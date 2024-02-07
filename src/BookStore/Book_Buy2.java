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
		welcome = Login.customerName + "�� ȯ���մϴ�.";
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
		String id="book_store_DB"; // ���� ��ǻ���� sql �̸����� �ٲٸ� �˴ϴ�.
		String password="bookstore"; // ���� ��ǻ�� sql ��й�ȣ�� ���ּ���
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			System.out.println("����̺� ���� ����");

			con=DriverManager.getConnection(url, id, password);
			stmt=con.createStatement();
			rs = stmt.executeQuery(str);

			System.out.println("�����ͺ��̽� ���� ����");

		}catch(ClassNotFoundException e){
			System.out.println("����̹��� ã�� �� �����ϴ�");
		}catch(SQLException e){
			System.out.println("���ῡ �����Ͽ����ϴ�");
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
	
	JPanel[] pnl = new JPanel[3]; // �� �г�
	JPanel[] npnl = new JPanel[2]; // NORTH �г� ������ �г�
	
	String[] select = {"å��ȣ","����","�۰�","���ǻ�"};
	JComboBox cb = new JComboBox(select);
	JTextField tf = new JTextField(40);
	JButton[] btn = new JButton[2];
	
	String[] head = {"å��ȣ","����","���ǻ�","����","����"};
	Object[][] list = new Object[0][5];
	DefaultTableModel model = new DefaultTableModel(list,head);
	JTable table = new JTable(model);
	
	String[] howpay = {"ī�����","���ݰ���","������ü"};
	JComboBox<String> howpaycb = new JComboBox<String>(howpay);
	JLabel stocklbl = new JLabel("���ż���");
	JTextField stocktf = new JTextField(10);
	
	String str;
	
	
	Book_Buy2_look(){
		c.setLayout(new BorderLayout());
		setTitle("å ���� ���α׷� (����)");
		
		for(int i = 0;i<3;i++) {
			pnl[i] = new JPanel();
		}
		for(int i = 0;i<2;i++) {
			npnl[i] = new JPanel();
		}
		
		table.getColumn("å��ȣ").setPreferredWidth(650);
		table.getColumn("����").setPreferredWidth(1500);
		table.getColumn("���ǻ�").setPreferredWidth(700);
		table.getColumn("����").setPreferredWidth(230);
		table.getColumn("����").setPreferredWidth(150);
		
		pnl[0].setLayout(new BorderLayout());
		
		
		npnl[0].add(welcomelbl);
		pnl[0].add(npnl[0],BorderLayout.NORTH);
		pnl[0].add(npnl[1],BorderLayout.CENTER);
		npnl[1].add(cb);
		npnl[1].add(tf);
		btn[0] = new JButton("�˻�");
		btn[1] = new JButton("����");
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
				if(cb.getSelectedItem() == "å��ȣ")
					if(tf.getText().equals(""))
						str = "select * from bookstock";
					else	
						str = "select * from bookstock where ID='" + tf.getText() + "'";
				else if(cb.getSelectedItem() == "����")
					if(tf.getText().equals(""))
						str = "select * from bookstock";
					else
						str = "select * from bookstock where title='" + tf.getText() + "'";
				else if(cb.getSelectedItem() == "���ǻ�")
					if(tf.getText().equals(""))
						str = "select * from bookstock";
					else
						str = "select * from bookstock where PublisherID='" + tf.getText() + "'";
				
				System.out.println(str);
				String url="jdbc:mysql://121.175.188.149:3306/mydb?serverTimezone=Asia/Seoul";
				String id="book_store_DB"; // ���� ��ǻ���� sql �̸����� �ٲٸ� �˴ϴ�.
				String password="bookstore"; // ���� ��ǻ�� sql ��й�ȣ�� ���ּ���
				try{
					Class.forName("com.mysql.cj.jdbc.Driver");
					System.out.println("����̺� ���� ����");

					Connection con=DriverManager.getConnection(url, id, password);
					Statement stmt=con.createStatement();
					ResultSet rs = stmt.executeQuery(str);

					System.out.println("�����ͺ��̽� ���� ����");
					
					model.setNumRows(0);
					while(rs.next()) {
						Object[] info = {rs.getString("ID"),rs.getString("title"),rs.getString("publisherID"),rs.getString("price"),rs.getString("stock")};
						model.addRow(info);
					}
					System.out.println("�˻�");
				}catch(ClassNotFoundException e1){
					System.out.println("����̹��� ã�� �� �����ϴ�");
				}catch(SQLException e1){
					System.out.println("���ῡ �����Ͽ����ϴ�");
				}
				
			}
			else if(e.getSource() == btn[1]) {
				System.out.print("����");
				String url="jdbc:mysql://121.175.188.149:3306/mydb?serverTimezone=Asia/Seoul";
				String id="book_store_DB"; // ���� ��ǻ���� sql �̸����� �ٲٸ� �˴ϴ�.
				String password="bookstore"; // ���� ��ǻ�� sql ��й�ȣ�� ���ּ���
				
				System.out.println(str);
				try{
					if(str == null)
						str = "select * from bookstock";
					
					Class.forName("com.mysql.cj.jdbc.Driver");
					System.out.println("����̺� ���� ����");

					Connection con=DriverManager.getConnection(url, id, password);
					Statement stmt=con.createStatement();
					ResultSet rs = stmt.executeQuery(str);

					System.out.println("�����ͺ��̽� ���� ����");
				
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
					System.out.println("����̹��� ã�� �� �����ϴ�");
				}catch(SQLException e1){
					System.out.println("���ῡ �����Ͽ����ϴ�");
				}
			}
			
			
		}
	}

}