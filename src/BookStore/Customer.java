package BookStore;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.math.*;

public class Customer extends JPanel{
	JLabel[] lbl = new JLabel[6];// ��ID,�̸�, ��ȭ��ȣ, �ּ�, ����, ����
	JLabel lblG;
	JComboBox<String> gender;
	JTextField[] tf = new JTextField[6];
	JButton[] bt = new JButton[4]; //�߰�, ��ȸ, ��ȸ, ����
	Object[][] ob = new Object[0][7];
	DefaultTableModel tModel;
	JTable table;
	JPanel[] pl= new JPanel[3];
	CustomerAction action = new CustomerAction();
	
	BookStoreDB_connection connect = new BookStoreDB_connection();//mySQL �����Ҷ� �� Ŭ������ ����

	Customer(){
		connect.makeConnection();
		setPanel();
		Show();
		connect.disConnection();
	}
	void setPanel() {
		String[] lbS = {"��ID","��PW","�̸�", "��ȭ��ȣ", "�ּ�", "����"};
		String gen = "����";
		String[] tableS = {"��ID","��PW","�̸�", "��ȭ��ȣ", "�ּ�", "����", "����"};
		String[] btS = {"Append", "Show", "Update", "Delete"};
		String[] comboS = {"All","����","����"};

		BorderLayout border = new BorderLayout();
		setLayout(border);

		tModel = new DefaultTableModel(ob, tableS);
		table = new JTable(tModel);
		table.addMouseListener(new MAction());
		gender = new JComboBox<String>(comboS);
		pl[0] = new JPanel();
		JPanel upPl = new JPanel();
		upPl.setLayout(new GridLayout(2,1));
		pl[1] = new JPanel();
		pl[2] = new JPanel();
		for(int i=0; i < lbl.length; i++) {
			if(i==4) {
				lbl[i] = new JLabel(lbS[i]);
				tf[i] = new JTextField(35);
				pl[1].add(lbl[i]);
				pl[1].add(tf[i]);
				lblG = new JLabel(gen);
				pl[1].add(lblG);
				pl[1].add(gender);
			}else{
				lbl[i] = new JLabel(lbS[i]);
				tf[i] = new JTextField(10);
				pl[0].add(lbl[i]);
				pl[0].add(tf[i]);
			}			
		}
		for(int i=0; i<bt.length; i++) {
			bt[i] = new JButton(btS[i]);
			bt[i].addActionListener(action);
			pl[2].add(bt[i]);
		}
		upPl.add(pl[0]);
		upPl.add(pl[1]);
		add(upPl, BorderLayout.NORTH);
		add(new JScrollPane(table), BorderLayout.CENTER);
		add(pl[2], BorderLayout.SOUTH);

	}
	class MAction extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();
			
			String[] attribute = {"ID","password", "name", "phoneNumber", "address", "age"};//�ý�Ʈ �ʵ� üũ��

			String ID = (String)table.getValueAt(row, 0); 
			String password = (String)table.getValueAt(row, 1); 
			String name = (String)table.getValueAt(row, 2); 
			String phoneNumber = (String)table.getValueAt(row, 3); 
			String address = (String)table.getValueAt(row, 4); 
			String gen = (String)table.getValueAt(row, 5); 
			String age = (String)table.getValueAt(row, 6); 
			tf[0].setText(ID);
			tf[1].setText(password);
			tf[2].setText(name);
			tf[3].setText(phoneNumber);
			tf[4].setText(age);
			tf[5].setText(address);
			gender.setSelectedItem(gen);
			
		}
	}
	class CustomerAction implements ActionListener{
		StringBuilder sb = new StringBuilder();
		public void actionPerformed(ActionEvent e) {
			JButton checkbt = (JButton)e.getSource();
			String[] attribute = {"ID","password", "name", "phoneNumber", "address", "age"};//�ý�Ʈ �ʵ� üũ��
			String[] addAttri = new String[attribute.length];
			int[] index= new int[attribute.length];
			String sql;

			connect.makeConnection();

			int count=0;
			if(checkbt.equals(bt[0])) {//�߰�
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
					//System.out.println(count);
					if(tf[0].getText().equals("")||tf[1].getText().equals("")||tf[2].getText().equals("")||tf[4].getText().equals("")) {
						System.out.println("ID,PW,�̸�,�ּ� ���� �־����");
						MessegeWindow mess = new MessegeWindow();
						mess.memo.setText("ID,PW,�̸�,�ּ� ���� �־���մϴ�.");
					}else {
						sql = sb.append("insert into customer(").toString();
						for(int i=0; i < count; i++) {
							if(i==count-1) {
								sql = sb.append(addAttri[i]).toString();
							}else 
								sql = sb.append(addAttri[i]+",").toString();
						}
						if(!gender.getSelectedItem().toString().equals("All")) 
							sql = sb.append(",gender) values(").toString();
						else
							sql = sb.append(") values(").toString();

						for(int i=0; i < count; i++) {
							if(i==count-1) {
								sql = sb.append("?").toString();
							}else 
								sql = sb.append("?,").toString();
						}
						if(!gender.getSelectedItem().toString().equals("All"))//������ �޺� �ڽ����� ���� Ȯ��
							sql = sb.append(",?);").toString();//gender
						else
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

				if(count==0 && gender.getSelectedItem().toString().equals("All")) {//�ؽ�Ʈ ���̺����� ���缭 ����

					sql = sb.append("select * FROM customer  ").toString();
				}else {
					sql = sb.append("select * FROM customer where ").toString();
				}

				for(int i=0; i < count; i++) {
					if(i==count-1) {
						sql = sb.append(addAttri[i]+"=? ").toString();
					}else 
						sql = sb.append(addAttri[i]+"=? and ").toString();
				}
				if(!gender.getSelectedItem().toString().equals("All"))
					if(count==0)
						sql = sb.append("  gender=? ").toString();
					else
						sql = sb.append(" and gender=? ").toString();

				SelectShow(count, index, sql);
				setEmpty();

			}else if(checkbt.equals(bt[2])) {//����
				System.out.println(bt[2].getText());
				System.out.println();
				for(int i=1; i < attribute.length; i++) {//���� �ִ� �ؽ�Ʈ �ʵ带 Ȯ���ϰ� ����
					if(!tf[i].getText().equals("")) {
						addAttri[count] = attribute[i];
						index[count]= i;
						count++;
					}
				}
				//System.out.println(count);
				if(tf[0].getText().equals("")) {
					System.out.println("ID ���� �־����");
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("ID ���� �־�� �˴ϴ�.");
				}else {
					sql = sb.append("update customer set ").toString();
					for(int i=0; i < count; i++) {// i=1�� ������ ID�� �������� �����Ŷ�
						if(i==count-1) {
							sql = sb.append(addAttri[i]+"=? ").toString();
						}else 
							sql = sb.append(addAttri[i]+"=? , ").toString();
					}
					if(!gender.getSelectedItem().toString().equals("All")) //������ �޺� �ڽ����� ���� Ȯ��
						sql = sb.append(",gender=? where ID='"+tf[0].getText()+"'").toString();
					else
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
					sql = sb.append("delete FROM customer where ").toString();

					for(int i=0; i < count; i++) {
						if(i==count-1) {
							sql = sb.append(addAttri[i]+"=? ").toString();
						}else 
							sql = sb.append(addAttri[i]+"=? and ").toString();
					}
					if(!gender.getSelectedItem().toString().equals("All")) 
						sql = sb.append(" and gender=? ").toString();
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
		sql = sb.append("select * from customer where ").toString();

		for(int i=0; i < count; i++) {
			if(i==count-1) {
				sql = sb.append(addAttri[i] + "=?").toString();
			}else 
				sql = sb.append(addAttri[i] + "=? and ").toString();
		}
		if(!gender.getSelectedItem().toString().equals("All"))//������ �޺� �ڽ����� ���� Ȯ��
			sql = sb.append(" and gender = ?;").toString();//gender
		else
			sql = sb.append(";").toString();//gender

		try {
			connect.pstmt = connect.con.prepareStatement(sql);
			for(int i=0; i < count; i++) {
				connect.pstmt.setString(i+1, tf[index[i]].getText());
			}
			if(!gender.getSelectedItem().toString().equals("All")) 
				connect.pstmt.setString(count+1, gender.getSelectedItem().toString());

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
		gender.setEnabled(false);
	}
	void TFAble(){ //  Ȱ��ȭ
		for(int i=0;i<bt.length;i++) {
			bt[i].setEnabled(true);
		}for(int i=0; i < tf.length; i++) {
			tf[i].setEnabled(true);
		}
		gender.setEnabled(true);
	}
	void setEmpty() {//�ؽ�Ʈ �ʵ� �ʱ�ȭ
		for(int i=0; i < tf.length; i++) {
			tf[i].setText("");
		}
		gender.setSelectedItem("All");
	}
	void Show() {
		String sql;
		sql="SELECT * FROM customer";
		tModel.setNumRows(0);
		try {
			System.out.println(sql+"\n");
			connect.rs=connect.stmt.executeQuery(sql);
			while(connect.rs.next()) {
				Object[] data= {connect.rs.getString("ID")
						,connect.rs.getString("password")
						,connect.rs.getString("name")
						,connect.rs.getString("phoneNumber")
						,connect.rs.getString("address")
						,connect.rs.getString("gender")
						,connect.rs.getString("age")};
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
			if(!gender.getSelectedItem().toString().equals("All")) 
				connect.pstmt.setString(count+1, gender.getSelectedItem().toString());

			connect.rs = connect.pstmt.executeQuery();
			System.out.println(sql);
			while(connect.rs.next()) {
				Object[] data= {connect.rs.getString("ID")
						,connect.rs.getString("password")
						,connect.rs.getString("name")
						,connect.rs.getString("phoneNumber")
						,connect.rs.getString("address")
						,connect.rs.getString("gender")
						,connect.rs.getString("age")};
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
			if(!gender.getSelectedItem().toString().equals("All")) 
				connect.pstmt.setString(count+1, gender.getSelectedItem().toString());

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
