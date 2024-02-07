package BookStore;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class BookPurchase extends JPanel{
	JLabel[] lbl = new JLabel[4];// �ֹ���ȣ, �ֹ�����, åID, ���ǻ�ID, �ֹ���¥
	JTextField[] tf = new JTextField[4];
	JButton[] bt = new JButton[4]; //�߰�, ��ȸ, ����
	JTextField[] inOder = new JTextField[4];
	Object[][] ob = new Object[0][6];
	DefaultTableModel tModel;
	JTable table;
	JPanel[] pl = new JPanel[2];
	BookPurchaseAction action = new BookPurchaseAction();

	BookStoreDB_connection connect = new BookStoreDB_connection();

	BookPurchase(){
		connect.makeConnection();
		setPanel();
		Show();
		connect.disConnection();
	}
	void setPanel() {
		String[] lbS = {"�ֹ���ȣ","�ֹ�����","å ID","���ǻ� ID","�ֹ���¥"};
		String[] tableS = {"�ֹ���ȣ","�ֹ�����","å ID","���ǻ� ID","�ֹ���¥"};
		String[] btS = {"Append", "Show", "Update","Delete"};
		pl[0] = new JPanel();
		pl[1] = new JPanel();

		BookPurchaseMAction tableMA = new BookPurchaseMAction();
		tModel = new DefaultTableModel(ob, tableS);
		table = new JTable(tModel);
		table.addMouseListener(tableMA);
		
		
		BorderLayout border = new BorderLayout();
		setLayout(border);

		for(int i=0; i < lbl.length; i++) {
			if(i==4) {
				lbl[i] = new JLabel(lbS[i]);
				tf[i] = new JTextField(20);
				pl[0].add(lbl[i]);
				pl[0].add(tf[i]);
			}else {
				lbl[i] = new JLabel(lbS[i]);
				tf[i]= new JTextField(10);
				pl[0].add(lbl[i]);
				pl[0].add(tf[i]);
			}
		}
		
		tf[0].setText("0");
		
		for(int i=0; i<bt.length; i++) {
			bt[i] = new JButton(btS[i]);
			bt[i].addActionListener(action);
			pl[1].add(bt[i]);
		}
		add(pl[0], BorderLayout.NORTH);
		add(new JScrollPane(table), BorderLayout.CENTER);
		add(pl[1], BorderLayout.SOUTH);
	}
	class BookPurchaseMAction extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();
			
			String no = (String)table.getValueAt(row, 0); 
			String oderQuantity = (String)table.getValueAt(row, 1); 
			String bookID = (String)table.getValueAt(row, 2); 
			String publisherID = (String)table.getValueAt(row, 3); 
			System.out.println(no + oderQuantity+ bookID + publisherID);
			tf[0].setText(no);
			tf[1].setText(oderQuantity);
			tf[2].setText(bookID);
			tf[3].setText(publisherID);
			
		}

		
	}
	class BookPurchaseAction implements ActionListener{
		StringBuilder sb = new StringBuilder();
		public void actionPerformed(ActionEvent e) {
			JButton checkbt = (JButton)e.getSource();
			String[] attribute = {"no", "oderQuantity", "bookID", "publisherID"};//�ý�Ʈ �ʵ� üũ��
			//"oderMethod",
			String[] addAttri = new String[attribute.length];
			int[] index= new int[attribute.length];
			String sql;
			int count=0;
			connect.makeConnection();

			if(checkbt.equals(bt[0])) {//append
				
				Calendar today = Calendar.getInstance();
				
				long year = today.get(Calendar.YEAR) - 2000;
				long month = today.get(Calendar.MONTH) + 1;
				long date = today.get(Calendar.DATE);
				long hour = today.get(Calendar.HOUR_OF_DAY);
				long minute = today.get(Calendar.MINUTE);
				long second = today.get(Calendar.SECOND);
				
				System.out.println();
				System.out.println();
				System.out.println(year);
				System.out.println(month);
				System.out.println(date);
				System.out.println(hour);
				System.out.println(minute);
				System.out.println(second); 
				
				long saleno = month * 100000000 + date * 1000000 + hour * 10000 + minute * 100 + second;
				for(int i1 = 0; i1 < 10; i1++)
					saleno += year * 1000000000;
				System.out.println(saleno);
				tf[0].setText("" + saleno);
				
				if(Check(1,attribute,index,"book_purchase")) {//�ߺ����� ������ Ȯ�� ó�� ���ڰ� 1�� ������ ID���� Ȯ���ϱ� ����
					tf[0].setText( 10* saleno+Math.random()*10+"");
				//	MessegeWindow mess = new MessegeWindow();
				//	mess.memo.setText("ID ���� �ߺ��Դϴ�.");

				}else if( !CheckForeign("ID", tf[2].getText().toString(),"bookstock") && !tf[2].getText().toString().equals("")) {
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("���� åID �Դϴ�.");
				}else if( !CheckForeign("ID", tf[3].getText().toString(),"publisher")&& !tf[3].getText().toString().equals("")) {
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("���� ���ǻ�ID �Դϴ�.");
				}else {
					System.out.println(bt[0].getText());
					System.out.println();
					for(int i=0; i < attribute.length; i++) {//���� �ִ� �ؽ�Ʈ �ʵ带 Ȯ���ϰ� ����
						if(!tf[i].getText().equals("")) {
							addAttri[count] = attribute[i];
							index[count]= i;
							count++;
						}
					}
					if(tf[1].getText().equals("")||tf[2].getText().equals("")) {
						MessegeWindow mess = new MessegeWindow();
						mess.memo.setText("����,å ���� �־���մϴ�.");
					}else {
						sql = sb.append("insert into book_purchase(").toString();
						for(int i=0; i < count; i++) {
							if(i==count-1) {
								sql = sb.append(addAttri[i]).toString();
							}else 
								sql = sb.append(addAttri[i]+",").toString();
						}
						sql = sb.append(") values(").toString();

						for(int i=0; i < count; i++) {
							if(i==count-1) {
								sql = sb.append("?").toString();
							}else 
								sql = sb.append("?,").toString();
						}
						
						sql = sb.append(");").toString();//gender
						String sql2="update bookstock set stock = stock+"+ tf[1].getText().toString()+" where ID='"+tf[2].getText().toString()+"'";
						Gosql(0,null,sql2);
						
						Gosql(count, index, sql);
						tf[0].setText("0");
						Show();
						setEmpty();
					}
				}
			}else if(checkbt.equals(bt[1])) {
				System.out.println(bt[1].getText());
				System.out.println();
				for(int i=0; i < attribute.length; i++) {//���� �ִ� �ؽ�Ʈ �ʵ� Ȯ��
					if(!tf[i].getText().equals("")) {
						addAttri[count] = attribute[i];
						index[count]= i;
						count++;
					}
				}
				if(tf[0].getText().equals("")&&tf[1].getText().equals("")
						&&tf[2].getText().equals("")&&tf[3].getText().equals("")) {

					sql = sb.append("select * FROM book_purchase  ").toString();
				}else {
					sql = sb.append("select * FROM book_purchase where ").toString();
				}
				for(int i=0; i < count; i++) {
					if(i==count-1) {
						sql = sb.append(addAttri[i]+"=? ").toString();
					}else 
						sql = sb.append(addAttri[i]+"=? and ").toString();
				}

				SelectShow(count, index, sql);
			}else if(checkbt.equals(bt[2])) {
				System.out.println(bt[2].getText());
				System.out.println();
				//���� �ִ� �ؽ�Ʈ �ʵ带 Ȯ���ϰ� ����
				for(int i=1; i < attribute.length; i++) {// i=1�� ������ ID�� �������� ���� �����Ŷ�
					if(!tf[i].getText().equals("")) {
						addAttri[count] = attribute[i];
						index[count]= i;
						count++;
					}
				}
				System.out.println(count);
				if(tf[0].getText().equals("")) {
					System.out.println("ID ���� �־����");
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("ID ���� �־�� �˴ϴ�.");

				}else if(tf[1].getText().equals("")&&tf[2].getText().equals("")&&tf[3].getText().equals("")){
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("����� ���� �־�� �˴ϴ�.");

				}else{
					sql = sb.append("update book_purchase set ").toString();
					for(int i=0; i < count; i++) {
						if(i==count-1) {
							sql = sb.append(addAttri[i]+"=? ").toString();
						}else 
							sql = sb.append(addAttri[i]+"=? , ").toString();
					}
					sql = sb.append(" where no='"+tf[0].getText()+"'").toString();

					System.out.println("SQL ����");
					System.out.println(sql);
					if(!tf[1].getText().toString().equals("")) {
						String sql2= "update bookstock set stock = stock-"
								+ AttributeData("no",tf[0].getText(),"book_purchase","oderQuantity")+"+"+ tf[1].getText().toString()
								+ " where ID='"+AttributeData("no",tf[0].getText(),"book_purchase","bookID")+"'";
						Gosql(0, null, sql2);
					}
					Gosql(count, index, sql);
					Show();
					setEmpty();
				}
			}else if(checkbt.equals(bt[3])) {
				System.out.println();
				System.out.println(bt[3].getText());
				
				for(int i=0; i < attribute.length; i++) {//���� �ִ� �ؽ�Ʈ �ʵ� Ȯ��
					if(!tf[i].getText().equals("")) {
						addAttri[count] = attribute[i];
						index[count]= i;
						count++;
					}
				}
				if(count==0) {
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("�Էµ� ���� �����ϴ�.");
				}else {
					if(Check(count, addAttri, index,"book_purchase")) {//true �϶� ������ ���� ����
						DeleteV DV = new DeleteV();
						DV.Dbutton[0].addActionListener(action);
						DV.Dbutton[1].addActionListener(action);
						TFEnable();

					}else {
						System.out.println("������ ���� ����");
						MessegeWindow mess = new MessegeWindow();
						mess.memo.setText("������ ���� �����ϴ�.");
					}//if check else 
				}
			}else if(checkbt.getText().equals("Yes")) {
				TFAble();
				for(int i=0; i < attribute.length; i++) {//���� �ִ� �ؽ�Ʈ �ʵ� Ȯ��
					if(!tf[i].getText().equals("")) {
						addAttri[count] = attribute[i];
						index[count]= i;
						count++;
					}
				}
				if(tf[0].getText().equals("")) {
					System.out.println("�ֹ���ȣ ���� ������");
				}else {
					sql = sb.append("delete FROM book_purchase where ").toString();

					for(int i=0; i < count; i++) {
						if(i==count-1) {
							sql = sb.append(addAttri[i]+"=? ").toString();
						}else 
							sql = sb.append(addAttri[i]+"=? and ").toString();
					}


					String sql2="update bookstock set stock = stock-"
							+ AttributeData("no",tf[0].getText(),"book_purchase","oderQuantity") 
							+" where ID='"
							+AttributeData("no",tf[0].getText(),"book_purchase","bookID") +"'";

					Gosql(0, null, sql2);

					Gosql(count, index, sql);
					Show();
					setEmpty();
				}
			}else if(checkbt.getText().equals("No")) {
				TFAble();
			}
			sb.setLength(0);
			connect.disConnection();
		}
	}
	Object AttributeData(String primaryKey, String key ,String DB_table, String getData) {//���̺��� ���� ������
		String sql;
		StringBuilder sb= new StringBuilder();

		Object result = null;
		sql = sb.append("select * from "+DB_table+" where "+primaryKey+"=?").toString();
		try {
			connect.pstmt = connect.con.prepareStatement(sql);
			connect.pstmt.setString(1, key);
			connect.rs = connect.pstmt.executeQuery();
			
			if(connect.rs.next())
				result=connect.rs.getString(getData);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("���� Ȯ��");
		}
		return result;
	}
	boolean CheckForeign(String primaryKey, String key ,String DB_table) {//�ַ�Ű �˻�
		String sql;
		StringBuilder sb= new StringBuilder();

		boolean result=false;
		if(key.equals("")) {//�����϶��� ����
			return result = true;
		}
		sql = sb.append("select * from "+DB_table+" where "+primaryKey+"=?").toString();

		try {
			connect.pstmt = connect.con.prepareStatement(sql);
			connect.pstmt.setString(1, key);
			connect.rs = connect.pstmt.executeQuery();

			if(connect.rs.next()) {
				result=true;
			}else {
				result=false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�ܷ�Ű �ߺ�Ȯ�� �߿� ���� �߻�");
			System.out.println(sql);
			System.out.println();
		}
		return result;

	}
	boolean Check(int count,String[] addAttri, int[] index, String DB_table) {//�ִ� ���������� Ȯ��
		String sql;
		StringBuilder sb= new StringBuilder();

		boolean result=false;
		if(count == 0) {
			return result=false;
		}
		sql = sb.append("select * from "+ DB_table +" where ").toString();

		for(int i=0; i < count; i++) {
			if(i==count-1) {
				sql = sb.append(addAttri[i] + "=?").toString();
			}else 
				sql = sb.append(addAttri[i] + "=? and ").toString();
		}

		sql = sb.append(";").toString();//gender

		try {
			connect.pstmt = connect.con.prepareStatement(sql);
			for(int i=0; i < count; i++) {
				connect.pstmt.setString(i+1, tf[index[i]].getText());
			}

			connect.rs = connect.pstmt.executeQuery();
			if(connect.rs.next()) {
				result=true;
			}else {
				result=false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�ߺ�Ȯ�� �߿� ���� �߻�");
			System.out.println(sql);
			System.out.println();
		}
		return result;
	}
	void TFEnable(){ //  ��Ȱ��ȭ
		for(int i=0;i<bt.length;i++) {
			bt[i].setEnabled(false);
		}for(int i=0; i < tf.length; i++) {
			tf[i].setEnabled(false);
		}
	}
	void TFAble(){ //  Ȱ��ȭ
		for(int i=0;i<bt.length;i++) {
			bt[i].setEnabled(true);
		}for(int i=0; i < tf.length; i++) {
			tf[i].setEnabled(true);
		}
	}
	void setEmpty() {//�ؽ�Ʈ �ʵ� �ʱ�ȭ
		for(int i=0; i < tf.length; i++) {
			tf[i].setText("");
		}
	}
	void Show() {
		String sql;
		sql="SELECT * FROM book_purchase";
		tModel.setNumRows(0);
		try {
			System.out.println(sql+"\n");
			connect.rs=connect.stmt.executeQuery(sql);
			while(connect.rs.next()) {
				Object[] data= {connect.rs.getString("no")
						,connect.rs.getString("oderQuantity")
						,connect.rs.getString("bookID")
						,connect.rs.getString("publisherID")
						,connect.rs.getString("oderDate")};
				tModel.addRow(data);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("show");
			System.out.println();
		}
	}
	void SelectShow(int count, int[] index, String sql) { //���ǿ� �´� ������ ����
		tModel.setNumRows(0);
		try {
			connect.pstmt = connect.con.prepareStatement(sql);
			for(int i=0; i < count; i++) {
				connect.pstmt.setString(i+1, tf[index[i]].getText());
			}

			connect.rs = connect.pstmt.executeQuery();
			System.out.println(sql+"\n");

			while(connect.rs.next()) {
				Object[] data= {connect.rs.getString("no")
						,connect.rs.getString("oderQuantity")
						,connect.rs.getString("bookID")
						,connect.rs.getString("publisherID")
						,connect.rs.getString("oderDate")};
				tModel.addRow(data);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("SelectShow error");
			System.out.println(sql);
			System.out.println();
		}
	}
	void Gosql(int count, int[] index, String sql) {
		try {
			connect.pstmt = connect.con.prepareStatement(sql);
			for(int i=0; i < count; i++) {
				connect.pstmt.setString(i+1, tf[index[i]].getText());
			}

			int result = connect.pstmt.executeUpdate();
			if(result==1) {
				System.out.println("Board������ ���� ����!");
				System.out.println(sql);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("sql ������ ����");
			System.out.println(sql);
		}
	}
	public static void main(String[] args) {

	}

}
