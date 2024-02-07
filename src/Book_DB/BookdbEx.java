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
		String[] lbl_button= {"추가","조회","수정","삭제"};
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
		setTitle("서적 관리");
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
			System.out.println("드라이브 적재 성공");
			con=DriverManager.getConnection(url, id, password);
			stmt=con.createStatement();
			System.out.println("데이터베이스 연결 성공");
		}catch(ClassNotFoundException e){
			System.out.println("드라이버를 찾을 수 없습니다");
		}catch(SQLException e){
			System.out.println("연결에 실패하였습니다");
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
