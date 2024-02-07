package BookStore;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BookStock extends JPanel {
	JLabel[] lbl = new JLabel[5];// ��ID,åID, �ּ�, �������(JList<E>)
	JButton[] bt = new JButton[4]; //�߰�, ��ȸ, ��ȸ, ����
	JTextField[] tf = new JTextField[5];

	Object[][] ob = new Object[0][5];

	DefaultTableModel tModel;
	JTable table;
	
	StockMAction mAction = new StockMAction();
	BookStockAction action = new BookStockAction();
	BookStoreDB_connection connect = new BookStoreDB_connection();//mySQL �����Ҷ� �� Ŭ������ ����
	
	BookStock(){
		connect.makeConnection();
		setPanel();
		Show();
		connect.disConnection();
	}
	void setPanel(){
		String[] btS = {"Append", "Show", "Update", "Delete"};
		String[] lbS = {"åID","����","���ǻ�ID","����","���"};
		String[] tName= {"ID","����","���ǻ�ID","����","���"};
		tModel = new DefaultTableModel(ob,tName);
		table = new JTable(tModel);
		table.addMouseListener(mAction);
		JPanel pl1= new JPanel();
		JPanel pl2= new JPanel();

		FlowLayout flow = new FlowLayout();
		BorderLayout border = new BorderLayout();
		setLayout(border);
		pl1.setLayout(flow);

		for(int i=0; i < lbl.length; i++) {

			lbl[i]=new JLabel(lbS[i]);

			tf[i]=new JTextField(10);					

			pl1.add(lbl[i]);
			pl1.add(tf[i]);

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
	class StockMAction extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();
			
			String ID = (String)table.getValueAt(row, 0); 
			String title = (String)table.getValueAt(row, 1); 
			String publisherID = (String)table.getValueAt(row, 2); 
			String price = (String)table.getValueAt(row, 3); 
			String stock = (String)table.getValueAt(row, 4); 

			tf[0].setText(ID);
			tf[1].setText(title);
			tf[2].setText(publisherID);
			tf[3].setText(price);
			tf[4].setText(stock);
			
		}

		
	}
	class BookStockAction implements ActionListener{
		StringBuilder sb = new StringBuilder();
		public void actionPerformed(ActionEvent e) {
			JButton checkbt = (JButton)e.getSource();//�׼��� �Ͼ ���� JButton���� �ٲ�

			String sql;
			
			String[] attribute = {"ID", "title", "publisherID", "price", "stock"};//�ý�Ʈ �ʵ� üũ��
			String[] addAttri = new String[attribute.length];//���� �ִ� ���� �Ӽ�
			int[] index= new int[attribute.length];// �ؽ�Ʈ �ʵ忡�� ���� ������ ��ġ
			int count=0;// ���� �ִ� �ؽ�Ʈ �ʵ��� ��
			
			connect.makeConnection();

			if(checkbt.equals(bt[0])) {//�߰�
				if(Check(1,attribute,index)) {//�ߺ����� ������ Ȯ�� ó�� ���ڰ� 1�� ������ ID���� Ȯ���ϱ� ����
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("ID ���� �ߺ��Դϴ�.");
					
				}else if(!CheckForeign(tf[2].getText().toString(),"publisher")){// �ܷ�Ű�� �ִ��� Ȯ����
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("���� ���ǻ�ID �Դϴ�.");
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
					if(tf[0].getText().equals("")||tf[1].getText().equals("")||tf[3].getText().equals("")||tf[4].getText().equals("")) {
						System.out.println("ID,����,����,���� ���� �־����");
						MessegeWindow mess = new MessegeWindow();
						mess.memo.setText("ID,�̸�,�ּ� ���� �־���մϴ�.");
					}else {
						sql = sb.append("insert into bookstock(").toString();
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

						Gosql(count, index, sql);
						Show();
						setEmpty();
					}
				}
			}else if(checkbt.equals(bt[1])) {//��ȸ
				System.out.println(bt[1].getText());
				System.out.println();
				for(int i=0; i < attribute.length; i++) {//���� �ִ� �ؽ�Ʈ �ʵ� Ȯ��
					if(!tf[i].getText().equals("")) {
						addAttri[count] = attribute[i];//���� �ִ� �Ӽ� �̸� ����
						index[count]= i;//���� �ִ� �ؽ�Ʈ �ʵ� �ε��� ����
						count++;
					}
				}
				//sql�� ����
				if(tf[0].getText().equals("")&&tf[1].getText().equals("")
						&&tf[2].getText().equals("")&&tf[3].getText().equals("")&&tf[4].getText().equals("")) {

					sql = sb.append("select * FROM bookstock  ").toString();
				}else {
					sql = sb.append("select * FROM bookstock where ").toString();
				}
				for(int i=0; i < count; i++) {
					if(i==count-1) {
						sql = sb.append(addAttri[i]+"=? ").toString();
					}else 
						sql = sb.append(addAttri[i]+"=? and ").toString();
				}
					SelectShow(count, index, sql);
					setEmpty();
					
			}else if(checkbt.equals(bt[2])) {//����
				System.out.println(bt[2].getText());
				System.out.println();
				for(int i=1; i < attribute.length; i++) {//���� �ִ� �ؽ�Ʈ �ʵ带 Ȯ���ϰ� ����
					if(!tf[i].getText().equals("")) {
						addAttri[count] = attribute[i];//���� �ִ� �Ӽ� �̸� ����
						index[count]= i;//���� �ִ� �ؽ�Ʈ �ʵ� �ε��� ����
						count++;
					}
				}
				if(tf[0].getText().equals("")) {
					System.out.println("ID ���� �־����");
					MessegeWindow mess = new MessegeWindow();//�޼��� �����츦 Ŭ������ ������
					mess.memo.setText("ID ���� �־�� �˴ϴ�.");
				
				}else {//sql�� ����
					sql = sb.append("update bookstock set ").toString();
					for(int i=0; i < count; i++) {// i=1�� ������ ID�� �������� �����Ŷ�
						if(i==count-1) {
							sql = sb.append(addAttri[i]+"=? ").toString();
						}else 
							sql = sb.append(addAttri[i]+"=? , ").toString();
					}
					sql = sb.append(" where ID='"+tf[0].getText()+"'").toString();

					Gosql(count, index, sql);
					Show();
					setEmpty();
				}
			}else if(checkbt.equals(bt[3])) {//����
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
					if(Check(count, addAttri, index)) {//true �϶� ������ ���� ����
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
			}else if(checkbt.getText().equals("Yes")) {//���� Ȯ�� ������ ����
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
					sql = sb.append("delete FROM bookstock where ").toString();

					for(int i=0; i < count; i++) {
						if(i==count-1) {
							sql = sb.append(addAttri[i]+"=? ").toString();
						}else 
							sql = sb.append(addAttri[i]+"=? and ").toString();
					}
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
	boolean CheckForeign(String key ,String DB_table) {//�ܷ�Ű�� �ִ��� Ȯ���Ҷ� ���
		String sql;
		StringBuilder sb= new StringBuilder();

		boolean result=false;
		sql = sb.append("select * from ? where ID=?").toString();

		try {
			connect.pstmt = connect.con.prepareStatement(sql);
			connect.pstmt.setString(1, DB_table);
			connect.pstmt.setString(2, key);
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
	boolean Check(int count,String[] addAttri, int[] index) {// �⺻Ű �ߺ�Ȯ��, �����Ұ��� �ִ��� Ȯ���Ҷ� ��� 
		String sql;
		StringBuilder sb= new StringBuilder();

		boolean result=false;
		if(count == 0) {
			return result=false;
		}
		sql = sb.append("select * from bookstock where ").toString();

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
	void TFEnable(){ // �ؽ�Ʈ �ʵ�, ��ư ��Ȱ��ȭ
		for(int i=0;i<bt.length;i++) {
			bt[i].setEnabled(false);
		}for(int i=0; i < tf.length; i++) {
			tf[i].setEnabled(false);
		}
	}
	void TFAble(){ // �ؽ�Ʈ �ʵ�, ��ư   Ȱ��ȭ
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
	void Show() {//DB���̺��� JTable��  ��� 
		String sql;
		sql="SELECT * FROM bookstock";
		tModel.setNumRows(0);
		try {
			System.out.println(sql+"\n");
			connect.rs=connect.stmt.executeQuery(sql);
			while(connect.rs.next()) {
				Object[] data= {connect.rs.getString("ID")
						,connect.rs.getString("title")
						,connect.rs.getString("publisherID")
						,connect.rs.getString("price")
						,connect.rs.getString("stock")};
				tModel.addRow(data);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("show");
			System.out.println();
		}
	}
	void SelectShow(int count, int[] index, String sql) { //���ǿ� �´� DB���̺��� JTable��  ��� 
		tModel.setNumRows(0);
		try {
			connect.pstmt = connect.con.prepareStatement(sql);
			for(int i=0; i < count; i++) {
				connect.pstmt.setString(i+1, tf[index[i]].getText());
			}

			connect.rs = connect.pstmt.executeQuery();
			System.out.println(sql);
			while(connect.rs.next()) {
				Object[] data= {connect.rs.getString("ID")
						,connect.rs.getString("title")
						,connect.rs.getString("publisherID")
						,connect.rs.getString("price")
						,connect.rs.getString("stock")};
				tModel.addRow(data);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("show");
			System.out.println(sql);
			System.out.println();
		}
	}
	void Gosql(int count, int[] index, String sql) { // SQL�� �޾Ƽ� ����
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
		new BookStock();
	}

}
