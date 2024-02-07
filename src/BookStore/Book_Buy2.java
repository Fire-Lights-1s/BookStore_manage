package BookStore;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.table.*;


public class Book_Buy2{
	
	String str = "select * from bookstock";
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	static String welcome;
	BookStoreDB_connection connect = new BookStoreDB_connection();
	
	Book_Buy2(){
		makeConnection();
		welcome = Login.customerName + "님 환영합니다.";
		Book_Buy2_look look = new  Book_Buy2_look();
		
		try {
			while(rs.next()) {
				Object[] info = {rs.getString("ID"),rs.getString("title"),rs.getString("publisherID"),rs.getString("price"),rs.getString("stock")};
				look.model.addRow(info);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Connection makeConnection(){
		String url="jdbc:mysql://121.175.188.149:3306/mydb?serverTimezone=Asia/Seoul";
		String id="book_store_DB"; // 본인 컴퓨터의 sql 이름으로 바꾸면 됩니다.
		String password="bookstore"; // 본인 컴퓨터 sql 비밀번호로 해주세요
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("드라이브 적재 성공");

			con=DriverManager.getConnection(url, id, password);
			stmt=con.createStatement();
			rs = stmt.executeQuery(str);

			System.out.println("데이터베이스 연결 성공");

		}catch(ClassNotFoundException e){
			System.out.println("드라이버를 찾을 수 없습니다");
		}catch(SQLException e){
			System.out.println("연결에 실패하였습니다");
		}
		return con;
	}
	
	public static void main(String[] args) {
		
		new Login();
	}
}

class Book_Buy2_look extends JFrame{
	
	Container c = getContentPane();
	JLabel welcomelbl = new JLabel(Book_Buy2.welcome);
	
	JPanel[] pnl = new JPanel[3]; // 주 패널
	JPanel[] npnl = new JPanel[2]; // NORTH 패널 나누는 패널
	
	String[] select = {"책번호","제목","작가","출판사"};
	JComboBox cb = new JComboBox(select);
	JTextField tf = new JTextField(40);
	JButton[] btn = new JButton[2];
	
	String[] head = {"책번호","제목","출판사","가격","수량"};
	Object[][] list = new Object[0][5];
	DefaultTableModel model = new DefaultTableModel(list,head);
	JTable table = new JTable(model);
	
	String[] howpay = {"카드결제","현금결제","계좌이체"};
	JComboBox<String> howpaycb = new JComboBox<String>(howpay);
	JLabel stocklbl = new JLabel("구매수량");
	JTextField stocktf = new JTextField(10);
	
	String str;
	
	
	Book_Buy2_look(){
		c.setLayout(new BorderLayout());
		setTitle("책 구입 프로그램 (고객용)");
		
		for(int i = 0;i<3;i++) {
			pnl[i] = new JPanel();
		}
		for(int i = 0;i<2;i++) {
			npnl[i] = new JPanel();
		}
		
		table.getColumn("책번호").setPreferredWidth(650);
		table.getColumn("제목").setPreferredWidth(1500);
		table.getColumn("출판사").setPreferredWidth(700);
		table.getColumn("가격").setPreferredWidth(230);
		table.getColumn("수량").setPreferredWidth(150);
		
		pnl[0].setLayout(new BorderLayout());
		
		
		npnl[0].add(welcomelbl);
		pnl[0].add(npnl[0],BorderLayout.NORTH);
		pnl[0].add(npnl[1],BorderLayout.CENTER);
		npnl[1].add(cb);
		npnl[1].add(tf);
		btn[0] = new JButton("검색");
		btn[1] = new JButton("구매");
		for(int i = 0;i<2;i++) {
			btn[i].addActionListener(new MyActionListener());
			npnl[1].add(btn[i]);
		}
		c.add(pnl[0],BorderLayout.NORTH);
		c.add(new JScrollPane(table),BorderLayout.CENTER);
		
		pnl[2].add(howpaycb);
		pnl[2].add(stocklbl);
		pnl[2].add(stocktf);
		c.add(pnl[2],BorderLayout.SOUTH);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700,500);
		setVisible(true);
			
		}
	
	class MyActionListener implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {

			if(e.getSource() == btn[0]) {
				if(cb.getSelectedItem() == "책번호")
					if(tf.getText().equals(""))
						str = "select * from bookstock";
					else	
						str = "select * from bookstock where ID='" + tf.getText() + "'";
				else if(cb.getSelectedItem() == "제목")
					if(tf.getText().equals(""))
						str = "select * from bookstock";
					else
						str = "select * from bookstock where title='" + tf.getText() + "'";
				else if(cb.getSelectedItem() == "출판사")
					if(tf.getText().equals(""))
						str = "select * from bookstock";
					else
						str = "select * from bookstock where PublisherID='" + tf.getText() + "'";
				
				System.out.println(str);
				String url="jdbc:mysql://121.175.188.149:3306/mydb?serverTimezone=Asia/Seoul";
				String id="book_store_DB"; // 본인 컴퓨터의 sql 이름으로 바꾸면 됩니다.
				String password="bookstore"; // 본인 컴퓨터 sql 비밀번호로 해주세요
				try{
					Class.forName("com.mysql.cj.jdbc.Driver");
					System.out.println("드라이브 적재 성공");

					Connection con=DriverManager.getConnection(url, id, password);
					Statement stmt=con.createStatement();
					ResultSet rs = stmt.executeQuery(str);

					System.out.println("데이터베이스 연결 성공");
					
					model.setNumRows(0);
					while(rs.next()) {
						Object[] info = {rs.getString("ID"),rs.getString("title"),rs.getString("publisherID"),rs.getString("price"),rs.getString("stock")};
						model.addRow(info);
					}
					System.out.println("검색");
				}catch(ClassNotFoundException e1){
					System.out.println("드라이버를 찾을 수 없습니다");
				}catch(SQLException e1){
					System.out.println("연결에 실패하였습니다");
				}
				
			}
			else if(e.getSource() == btn[1]) {
				System.out.print("구매");
				String url="jdbc:mysql://121.175.188.149:3306/mydb?serverTimezone=Asia/Seoul";
				String id="book_store_DB"; // 본인 컴퓨터의 sql 이름으로 바꾸면 됩니다.
				String password="bookstore"; // 본인 컴퓨터 sql 비밀번호로 해주세요
				
				System.out.println(str);
				try{
					if(str == null)
						str = "select * from bookstock";
					
					Class.forName("com.mysql.cj.jdbc.Driver");
					System.out.println("드라이브 적재 성공");

					Connection con=DriverManager.getConnection(url, id, password);
					Statement stmt=con.createStatement();
					ResultSet rs = stmt.executeQuery(str);

					System.out.println("데이터베이스 연결 성공");
				
					for(int i=0;i<=table.getSelectedRow();i++)
						rs.next();
					String bookid = rs.getString("id");
					int stock = rs.getInt("stock");
					String title= rs.getString("title");
					int i = 0;
					
					Calendar today = Calendar.getInstance();
					long year;
					long month;
					long date;
					long hour;
					long minute;
					long second;
					
					long saleno;
					
					if (stock>=Integer.parseInt(stocktf.getText())) {
						
						System.out.print("update bookstock set stock=" + (stock - Integer.parseInt(stocktf.getText())) + " where id = " + bookid);
						stmt.executeUpdate("update bookstock set stock=" + (stock - Integer.parseInt(stocktf.getText())) + " where id = " + bookid);
						
						rs = stmt.executeQuery(str);
						model.setNumRows(0);
						while(rs.next()) {
							Object[] info = {rs.getString("ID"),rs.getString("title"),rs.getString("publisherID"),rs.getString("price"),rs.getString("stock")};
							model.addRow(info);
							}
						
						year = today.get(Calendar.YEAR) - 2000;
						month = today.get(Calendar.MONTH) + 1;
						date = today.get(Calendar.DATE);
						hour = today.get(Calendar.HOUR_OF_DAY);
						minute = today.get(Calendar.MINUTE);
						second = today.get(Calendar.SECOND);
						
						System.out.println();
						System.out.println();
						System.out.println(year);
						System.out.println(month);
						System.out.println(date);
						System.out.println(hour);
						System.out.println(minute);
						System.out.println(second); 
						
						saleno = month * 100000000 + date * 1000000 + hour * 10000 + minute * 100 + second;
						for(int i1 = 0; i1 < 10; i1++)
							saleno += year * 1000000000;
						System.out.println(saleno);
						
						
						System.out.println("insert into book_sale(no, cus	tomerID, bookID, saleMethod, saleQuantity) values ('" +saleno + "', '" + Login.customerID + "', '" + bookid + "', '" + howpaycb.getSelectedItem() + "', '" + stocktf.getText() + "')");
						stmt.executeUpdate("insert into book_sale(no, customerID, bookID, saleMethod, saleQuantity) values ('" +saleno + "', '" + Login.customerID + "', '" + bookid + "', '" + howpaycb.getSelectedItem() + "', '" + stocktf.getText() + "')");
						
		
					}
					
					else {
						new StockError();
					}
				}catch(ClassNotFoundException e1){
					System.out.println("드라이버를 찾을 수 없습니다");
				}catch(SQLException e1){
					System.out.println("연결에 실패하였습니다");
				}
			}
			
			
		}
	}

}