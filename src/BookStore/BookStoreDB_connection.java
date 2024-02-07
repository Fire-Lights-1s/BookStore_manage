package BookStore;
import java.sql.*;

public class BookStoreDB_connection {
	Connection con=null;
	Statement stmt=null;
	ResultSet rs=null;
	PreparedStatement pstmt = null;
	
	BookStoreDB_connection(){

	}
	public Connection makeConnection(){
		String url="jdbc:mysql://localhost:3306/mydb?serverTimezone=Asia/Seoul";
		String id="book_store_DB"; // ���� ��ǻ���� sql �̸����� �ٲٸ� �˴ϴ�.
		String password="bookstore"; // ���� ��ǻ�� sql ��й�ȣ�� ���ּ���
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
			if(pstmt!=null && !pstmt.isClosed()) {
				pstmt.close();
			}
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
	public static void main(String[] args) {


	}

}
