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
		String id="book_store_DB"; // 본인 컴퓨터의 sql 이름으로 바꾸면 됩니다.
		String password="bookstore"; // 본인 컴퓨터 sql 비밀번호로 해주세요
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
