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
			if(((JButton)e.getSource()).getText().equals("�߰�")) {
				
			}else {}
			
		}
		
	}
	class btAction implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			JButton check = (JButton)e.getSource();
			if(check.equals("������")) {
				
			}
			
		}
		
	}
	public Connection makeConnection(){
		String url="jdbc:mysql://localhost:3306/mydb?serverTimezone=Asia/Seoul";
		String id="subsub"; // ���� ��ǻ���� sql �̸����� �ٲٸ� �˴ϴ�.
		String password="1234"; // ���� ��ǻ�� sql ��й�ȣ�� ���ּ���
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
		new BookM();
	}

}
