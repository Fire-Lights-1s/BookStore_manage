package BookStore;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class BookSale extends JPanel { 
	JLabel[] lbl = new JLabel[5];// ��ID,åID, �ּ�, �������(JList<E>)
	JButton[] bt = new JButton[4]; //�߰�, ��ȸ, ����
	JTextField[] tf = new JTextField[4];
	String[] comboS = {"������� ����","���ݰ���","ī�����","������ü"};
	JComboBox<String> saleM = new JComboBox<String>(comboS);
	Object[][] ob = new Object[0][6];
	DefaultTableModel tModel;
	JTable table;

	MAction maction = new MAction();
	BookSaleAction action = new BookSaleAction();
	BookStoreDB_connection connect = new BookStoreDB_connection();

	BookSale(){
		connect.makeConnection();
		SetPanel();
		Show();
		connect.disConnection();
	}
	void SetPanel() {
		String[] btS = {"Append", "Show", "Update","Delete"};
		String[] lbS = {" �ֹ���ȣ","��ID","åID","����","�������"};
		String[] tName= {"�ֹ���ȣ","��ID","åID","����","�������","�ֹ���¥"};
		tModel = new DefaultTableModel(ob,tName);
		table = new JTable(tModel);
		table.addMouseListener(maction);
		JPanel pl1= new JPanel();
		JPanel pl2= new JPanel();
		pl1.setLayout(new GridLayout(2,1));
		JPanel[] pl1_1 = new JPanel[2];
		pl1_1[0]= new JPanel();
		pl1_1[1]= new JPanel();
		BorderLayout border = new BorderLayout();
		setLayout(border);

		for(int i=0; i < lbl.length; i++) {
			if(i==4) {//�������
				lbl[i]=new JLabel(lbS[i]);
				pl1_1[1].add(lbl[i]);
				pl1_1[1].add(saleM);
				pl1.add(pl1_1[1]);

			}else {
				lbl[i]=new JLabel(lbS[i]);
				if(i==5) {//�ּ�
					tf[4]=new JTextField(35);
//					pl1_1[1].add(lbl[i]);
//					pl1_1[1].add(tf[4]);
//					pl1.add(pl1_1[1]);
				}else if(i==3){
					tf[i]=new JTextField(10);					
					pl1_1[1].add(lbl[i]);
					pl1_1[1].add(tf[i]);
					pl1.add(pl1_1[1]);
				}else{
					tf[i]=new JTextField(10);					
					pl1_1[0].add(lbl[i]);
					pl1_1[0].add(tf[i]);
					pl1.add(pl1_1[0]);
				}
			}
		}
		for(int i=0; i<bt.length; i++) {
			bt[i] = new JButton(btS[i]);
			bt[i].addActionListener(action);
			pl2.add(bt[i]);
		}
		add(pl1, BorderLayout.NORTH);
		add(new JScrollPane(table),BorderLayout.CENTER);
		add(pl2, BorderLayout.SOUTH);
	}
	class MAction extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();
			

			String no = (String)table.getValueAt(row, 0); 
			String customerID = (String)table.getValueAt(row, 1); 
			String bookID = (String)table.getValueAt(row, 2); 
			String saleQuantity = (String)table.getValueAt(row, 3); 
			String method = (String)table.getValueAt(row, 4); 
			
			tf[0].setText(no);
			tf[1].setText(customerID);
			tf[2].setText(bookID);
			tf[3].setText(saleQuantity);
			saleM.setSelectedItem(method);
			
		}
	}
	class BookSaleAction implements ActionListener{
		StringBuilder sb = new StringBuilder();
		public void actionPerformed(ActionEvent e) {

			JButton checkbt = (JButton)e.getSource();

			String[] attribute = {"no", "customerID", "bookID","saleQuantity"};//�ý�Ʈ �ʵ� üũ��
			//"oderMethod",
			String[] addAttri = new String[attribute.length];
			int[] index= new int[attribute.length];
			String sql;
			int count=0;
			connect.makeConnection();

			if(checkbt.equals(bt[0])) {//append
				if(Check(1,attribute,index,"book_sale")) {//�ߺ����� ������ Ȯ�� ó�� ���ڰ� 1�� ������ ID���� Ȯ���ϱ� ����
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("�ֹ���ȣ ���� �ߺ��Դϴ�.");

				}else if( !CheckForeign("ID", tf[1].getText().toString(),"customer")&& !tf[1].getText().equals("")) {
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("���� ��ID �Դϴ�.");

				}else if( !CheckForeign("ID", tf[2].getText().toString(),"bookstock")&& !tf[2].getText().equals("")) {
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("���� åID �Դϴ�.");

				}else if(saleM.getSelectedItem().toString().equals("������� ����")) {
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("��������� �����ϼ���.");

				}else{
					System.out.println(bt[0].getText());
					System.out.println();
					for(int i=0; i < attribute.length; i++) {//���� �ִ� �ؽ�Ʈ �ʵ带 Ȯ���ϰ� ����
						if(!tf[i].getText().equals("")) {
							addAttri[count] = attribute[i];
							index[count]= i;
							count++;
						}
					}
					if(tf[0].getText().equals("")||tf[3].getText().equals("")) {
						System.out.println("�ֹ���ȣ,���� ���� �־����");
						MessegeWindow mess = new MessegeWindow();
						mess.memo.setText("�ֹ���ȣ,���� ���� �־���մϴ�.");
					}else {
						sql = sb.append("insert into book_sale(").toString();
						for(int i=0; i < count; i++) {
							if(i==count-1) {
								sql = sb.append(addAttri[i]).toString();
							}else 
								sql = sb.append(addAttri[i]+",").toString();
						}
						sql = sb.append(",saleMethod) values(").toString();

						for(int i=0; i < count; i++) {
							if(i==count-1) {
								sql = sb.append("?").toString();
							}else 
								sql = sb.append("?,").toString();
						}
						sql = sb.append(",?)").toString();
						
						String sql2="update bookstock set stock = stock-"+ tf[3].getText().toString()+" where ID='"+tf[2].getText().toString()+"'";
						Gosql(0,null,sql2,false);
						
						Gosql(count, index, sql,true);
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
				if(count==0 && saleM.getSelectedItem().toString().equals("������� ����")) {

					sql = sb.append("select * FROM book_sale  ").toString();
				}else {
					sql = sb.append("select * FROM book_sale where ").toString();
				}
				for(int i=0; i < count; i++) {
					if(i==count-1) {
						sql = sb.append(addAttri[i]+"=? ").toString();
					}else 
						sql = sb.append(addAttri[i]+"=? and ").toString();
				}
				if(!saleM.getSelectedItem().equals("������� ����"))
					if(count==0)
						sql = sb.append(" saleMethod=?").toString();
					else
						sql = sb.append("and saleMethod=?").toString();

				SelectShow(count, index, sql);
				setEmpty();

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

				}else if(count==0&&saleM.getSelectedItem().toString().equals("������� ����")){
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("����� ���� �־�� �˴ϴ�.");

				}else{
					sql = sb.append("update book_sale set ").toString();
					for(int i=0; i < count; i++) {
						if(i==count-1) {
							sql = sb.append(addAttri[i]+"=? ").toString();
						}else 
							sql = sb.append(addAttri[i]+"=? , ").toString();
					}
					if(!saleM.getSelectedItem().equals("������� ����"))
						if(count==0)
							sql = sb.append(" saleMethod=?").toString();
						else
							sql = sb.append(", saleMethod=?").toString();

					sql = sb.append(" where no='"+tf[0].getText()+"'").toString();

					System.out.println("SQL ����");
					System.out.println(sql);
					if(!tf[3].getText().toString().equals("")) {
						String sql2= "update bookstock set stock = stock+"
								+ AttributeData("no",tf[0].getText(),"book_sale","saleQuantity")+"-"+ tf[3].getText().toString()
								+ " where ID='"+AttributeData("no",tf[0].getText(),"book_sale","bookID")+"'";
						Gosql(0, null, sql2,false);
					}
					Gosql(count, index, sql,true);
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
				if(count==0 && saleM.getSelectedItem().toString().equals("������� ����")) {
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("�Էµ� ���� �����ϴ�.");
				}else {
					if(Check(count, addAttri, index,"book_sale")) {//true �϶� ������ ���� ����
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
					System.out.println("ID���� ������");
				}else {
					sql = sb.append("delete FROM book_sale where ").toString();

					for(int i=0; i < count; i++) {
						if(i==count-1) {
							sql = sb.append(addAttri[i]+"=? ").toString();
						}else 
							sql = sb.append(addAttri[i]+"=? and ").toString();
					}
					if(!saleM.getSelectedItem().equals("������� ����"))
						if(count==0)
							sql = sb.append(" saleMethod=?").toString();
						else
							sql = sb.append("and saleMethod=?").toString();
					
					String sql2= "update bookstock set stock = stock+"
							+ AttributeData("no",tf[0].getText(),"book_sale","saleQuantity")+"+"+ tf[3].getText().toString()
							+ " where ID='"+AttributeData("no",tf[0].getText(),"book_sale","bookID")+"'";
					Gosql(0, null, sql2,false);
					
					Gosql(count, index, sql,true);
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
	boolean CheckForeign(String primaryKey, String key ,String DB_table) {
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
	Object AttributeData(String primaryKey, String key ,String DB_table, String getData) {
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
	boolean Check(int count,String[] addAttri, int[] index, String DB_table) {
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
		saleM.setSelectedItem(comboS[0]);
	}
	void Show() {
		String sql;
		sql="SELECT * FROM book_sale";
		tModel.setNumRows(0);
		try {
			System.out.println(sql+"\n");
			connect.rs=connect.stmt.executeQuery(sql);
			while(connect.rs.next()) {
				Object[] data= {connect.rs.getString("no")
						,connect.rs.getString("customerID")
						,connect.rs.getString("bookID")
						,connect.rs.getString("saleQuantity")
						,connect.rs.getString("saleMethod")
						,connect.rs.getString("saleDate")};
				tModel.addRow(data);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("show");
			System.out.println();
		}
	}
	void SelectShow(int count, int[] index, String sql) {
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
						,connect.rs.getString("customerID")
						,connect.rs.getString("bookID")
						,connect.rs.getString("saleQuantity")
						,connect.rs.getString("saleMethod")
						,connect.rs.getString("saleDate")};

				tModel.addRow(data);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("SelectShow error");
			System.out.println(sql);
			System.out.println();
		}
	}
	void Gosql(int count, int[] index, String sql, boolean method) {
		try {
			connect.pstmt = connect.con.prepareStatement(sql);
			for(int i=0; i < count; i++) {
				connect.pstmt.setString(i+1, tf[index[i]].getText());
			}
			if(!saleM.getSelectedItem().equals("������� ����")&&method) {
				connect.pstmt.setString(count+1, saleM.getSelectedItem().toString());
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
