package BookStore;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BookM {
	
	Connection con=null;
	Statement stmt=null;
	ResultSet rs=null;
	
	BookStock BS = new BookStock();
	
	BookM(){
		
	}
	class CRUDAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(((JButton)e.getSource()).getText().equals("추가")) {
				
			}else {}
			
		}
		
	}
	class btAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton check = (JButton)e.getSource();
			if(check.equals("고객관리")) {
				
			}
			
		}
		
	}
	public Connection makeConnection(){
		String url="jdbc:mysql://localhost:3306/mydb?serverTimezone=Asia/Seoul";
		String id="subsub"; // 본인 컴퓨터의 sql 이름으로 바꾸면 됩니다.
		String password="1234"; // 본인 컴퓨터 sql 비밀번호로 해주세요
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
		new BookM();
	}

}
