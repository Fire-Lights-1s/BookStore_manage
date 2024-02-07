package BookStore;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import BookStore.Book;

public class Publisher extends JPanel{
	JLabel[] lbl = new JLabel[4];// ���ǻ�ID, �̸�, ��ȭ��ȣ, �ּ� 
	JTextField[] tf = new JTextField[4];

	JButton[] bt = new JButton[4]; //�߰�, ��ȸ, ����
	JTextField[] inOder = new JTextField[4];
	Object[][] ob = new Object[0][6];
	DefaultTableModel tModel;
	JTable table;
	JPanel[] pl = new JPanel[2];
	
	PublisherMAction mAction = new PublisherMAction();
	PublisherAction action = new PublisherAction();
	BookStoreDB_connection connect = new BookStoreDB_connection();

	Publisher(){
		connect.makeConnection();
		setPanel();
		Show();
		connect.disConnection();
	}
	void setPanel() {
		String[] lbS = {"ID","���ǻ�","��ȭ��ȣ","�ּ�"};
		String[] tableS = {"ID","���ǻ�","��ȭ��ȣ","�ּ�"};
		String[] btS = {"Append", "Show", "Update", "Delete"};
		tModel = new DefaultTableModel(ob, tableS);
		table = new JTable(tModel);
		table.addMouseListener(mAction);
		pl[0] = new JPanel();
		pl[1] = new JPanel();

		BorderLayout border = new BorderLayout();
		setLayout(border);

		for(int i=0; i < lbl.length; i++) {
			if(i==3) {
				lbl[i] = new JLabel(lbS[i]);
				tf[i] = new JTextField(35);
				pl[0].add(lbl[i]);
				pl[0].add(tf[i]);
			}else {
				lbl[i] = new JLabel(lbS[i]);
				tf[i] = new JTextField(10);
				pl[0].add(lbl[i]);
				pl[0].add(tf[i]);
			}
		}
		for(int i=0; i < bt.length; i++) {
			bt[i] = new JButton(btS[i]);
			bt[i].addActionListener(action);
			pl[1].add(bt[i]);
		}
		add(pl[0], BorderLayout.NORTH);
		add(new JScrollPane(table), BorderLayout.CENTER);
		add(pl[1], BorderLayout.SOUTH);
	}
	class PublisherMAction extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();
			
			String ID = (String)table.getValueAt(row, 0); 
			String name = (String)table.getValueAt(row, 1); 
			String phoneNumber = (String)table.getValueAt(row, 2); 
			String publisherAddress = (String)table.getValueAt(row, 3); 

			tf[0].setText(ID);
			tf[1].setText(name);
			tf[2].setText(phoneNumber);
			tf[3].setText(publisherAddress);
		}
	}
	class PublisherAction implements ActionListener{
		StringBuilder sb = new StringBuilder();
		public void actionPerformed(ActionEvent e) {
			JButton checkbt = (JButton)e.getSource();//�׼��� �Ͼ ���� JButton���� �ٲ�

			String[] attribute = {"ID", "name", "phoneNumber", "publisherAddress"};//�ý�Ʈ �ʵ� üũ��
			String[] addAttri = new String[attribute.length];//���� �ִ� ���� �Ӽ�
			int[] index= new int[attribute.length];// �ؽ�Ʈ �ʵ忡�� ���� ������ ��ġ
			String sql;
			int count=0;
			connect.makeConnection();

			if(checkbt.equals(bt[0])) {
				if(Check(1,attribute,index)) {//�ߺ����� ������ Ȯ�� ó�� ���ڰ� 1�� ������ ID���� Ȯ���ϱ� ����
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("ID ���� �ߺ��Դϴ�.");
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
					if(tf[0].getText().equals("")||tf[1].getText().equals("")||tf[2].getText().equals("")||tf[3].getText().equals("")) {
						System.out.println("ID,���ǻ�,��ȭ��ȣ,�ּ� ���� �־����");
						MessegeWindow mess = new MessegeWindow();
						mess.memo.setText("ID,���ǻ�,��ȭ��ȣ,�ּ� ���� �־���մϴ�.");
					}else {
						sql = sb.append("insert into publisher(").toString();
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
						addAttri[count] = attribute[i];
						index[count]= i;
						count++;
					}
				}


				if(tf[0].getText().equals("")&&tf[1].getText().equals("")
						&&tf[2].getText().equals("")&&tf[3].getText().equals("")) {

					sql = sb.append("select * FROM publisher  ").toString();
				}else {
					sql = sb.append("select * FROM publisher where ").toString();
				}
				for(int i=0; i < count; i++) {
					if(i==count-1) {
						sql = sb.append(addAttri[i]+"=? ").toString();
					}else 
						sql = sb.append(addAttri[i]+"=? and ").toString();


				}
				SelectShow(count, index, sql);
				setEmpty();
				
			}else if(checkbt.equals(bt[2])) {
				System.out.println(bt[2].getText());
				System.out.println();
				for(int i=1; i < attribute.length; i++) {//���� �ִ� �ؽ�Ʈ �ʵ带 Ȯ���ϰ� ����
					if(!tf[i].getText().equals("")) {
						addAttri[count] = attribute[i];
						index[count]= i;
						count++;
					}
				}
				if(tf[0].getText().equals("")) {
					System.out.println("ID ���� �־����");
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("ID ���� �־�� �˴ϴ�.");
				}else {
					sql = sb.append("update publisher set ").toString();
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
					sql = sb.append("delete FROM publisher where ").toString();

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
	boolean Check(int count,String[] addAttri, int[] index) {
		String sql;
		StringBuilder sb= new StringBuilder();

		boolean result=false;
		if(count == 0) {
			return result=false;
		}
		sql = sb.append("select * from publisher where ").toString();

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
		sql="SELECT * FROM publisher";
		tModel.setNumRows(0);
		try {
			System.out.println(sql+"\n");
			connect.rs=connect.stmt.executeQuery(sql);
			while(connect.rs.next()) {
				Object[] data= {connect.rs.getString("ID")
						,connect.rs.getString("name")
						,connect.rs.getString("phoneNumber")
						,connect.rs.getString("publisherAddress")};
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
			System.out.println(sql);
			while(connect.rs.next()) {
				Object[] data= {connect.rs.getString("ID")
						,connect.rs.getString("name")
						,connect.rs.getString("phoneNumber")
						,connect.rs.getString("publisherAddress")};
				tModel.addRow(data);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("show");
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
		// TODO Auto-generated method stub

	}

}
