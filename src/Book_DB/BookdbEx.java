package Book_DB;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.*;
class BookdbView2 extends JFrame{
	JPanel[] pnl=new JPanel[2];
	JLabel[] label=new JLabel[4];
	JTextField[] tf=new JTextField[4];
	JButton[] button=new JButton[4];
	JTextArea ta=new JTextArea();
	BookdbView2() {
		String[] lbl_tf= {"ID","Ttile","Publisher","Price"};
		String[] lbl_button= {"�߰�","��ȸ","����","����"};
		Container c=getContentPane();
		pnl[0]=new JPanel();
		pnl[1]=new JPanel();
		for(int i=0;i<4;i++) {
			tf[i]=new JTextField(10);
			label[i]=new JLabel(lbl_tf[i]);
			button[i]=new JButton(lbl_button[i]);
		}
		for(int i=0;i<4;i++) {
			pnl[0].add(label[i]);
			pnl[0].add(tf[i]);
			pnl[1].add(button[i]);
		}
		c.add(pnl[0],BorderLayout.NORTH);
		c.add(new JScrollPane(ta),BorderLayout.CENTER);
		c.add(pnl[1],BorderLayout.SOUTH);
		setTitle("���� ����");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600,400);
		setResizable(false);
		setVisible(true);
	}
}

public class BookdbEx {
	BookdbView2 v=new BookdbView2();
	ActionHandler handler=new ActionHandler();
	Connection con=null;
	Statement stmt=null;
	ResultSet rs=null;
	public BookdbEx() {
		v.button[0].addActionListener(handler);
		v.button[1].addActionListener(handler);
	}

	class ActionHandler implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			makeConnection();
			if(e.getSource()==v.button[0]) {	
				
			}
			else if(e.getSource()==v.button[1]) {
				String sql="SELECT * FROM book";
				String row="";
				v.ta.setText("");
				try {
					System.out.println(sql+"\n");
					rs=stmt.executeQuery(sql);
					while(rs.next()) {
						row=rs.getString("id")+"\t";
						row+=rs.getString("title")+"\t";
						row+=rs.getString("publisher")+"\t";
						row+=rs.getString("price")+"\n";
						v.ta.append(row);
						row="";
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			disConnection();		
		}
	}

	public Connection makeConnection(){
		String url="jdbc:mysql://localhost:3306/guidb?serverTimezone=Asia/Seoul";
		String id="root";
		String password="147369";
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("����̺� ���� ����");
			con=DriverManager.getConnection(url, id, password);
			stmt=con.createStatement();
			System.out.println("�����ͺ��̽� ���� ����");
		}catch(ClassNotFoundException e){
			System.out.println("����̹��� ã�� �� �����ϴ�");
		}catch(SQLException e){
			System.out.println("���ῡ �����Ͽ����ϴ�");
		}
		return con;
	}

	public void disConnection() {
		try{
			rs.close();
			stmt.close();
			con.close();
		}catch(SQLException e){System.out.println(e.getMessage());}
	}

	public static void main(String[] args) {
		new BookdbEx();
	}

}
