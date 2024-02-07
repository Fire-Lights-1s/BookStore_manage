package BookStore;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
public class Login extends JFrame{
	
	Container c = getContentPane();
	JLabel title = new JLabel("3������");
	JLabel id = new JLabel("ID             ");
	static JTextField idtf = new JTextField(12);
	JPasswordField passwordtf = new JPasswordField(12);
	JLabel password = new JLabel("��й�ȣ");
	JPanel[] pnl = new JPanel[3];
	JPanel idpnl = new JPanel();
	JPanel passwordpnl = new JPanel();
	
	static String customerName;
	static String customerID;
	
	Connection con = null;
	Statement stmt = null;
	ResultSet rs = null;
	
	JButton btn = new JButton("login");
	Login() {
		makeConnection();
		c.setLayout(new BorderLayout());
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		for(int i = 0; i < 3; i++) 
			pnl[i] = new JPanel();
		
		pnl[1].setLayout(new BorderLayout());
		pnl[0].add(title,BorderLayout.CENTER);
		c.add(pnl[0],BorderLayout.NORTH);
		passwordtf.setEchoChar('*');
		idpnl.add(id);
		idpnl.add(idtf);
		passwordpnl.add(password);
		passwordpnl.add(passwordtf);
		pnl[1].add(idpnl,BorderLayout.NORTH);
		pnl[1].add(passwordpnl,BorderLayout.CENTER);
		c.add(pnl[1],BorderLayout.CENTER);
		pnl[2].add(btn);
		c.add(pnl[2],BorderLayout.SOUTH);
		
		btn.addActionListener(new MyActionListener());
		setSize(300,200);
		setVisible(true);
		
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
			System.out.println("�����ͺ��̽� ���� ����");

		}catch(ClassNotFoundException e){
			System.out.println("����̹��� ã�� �� �����ϴ�");
		}catch(SQLException e){
			System.out.println("���ῡ �����Ͽ����ϴ�");
		}
		return con;
	}
	
	class MyActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == btn) {
				try {
					String loginstr = "select * from customer where id = '" + idtf.getText() + "' && password= '" + passwordtf.getText() + "'";
					System.out.println(loginstr);
					rs=stmt.executeQuery(loginstr);

					if(rs.next()) {
						customerName = rs.getString("name");
						customerID = rs.getString("ID");
						dispose();
						new Book_Buy2();
					}
					
					else {
						new LoginError();
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
			}
			
		}
		
	}

	public static void main(String[] args) {
		new Login();
	}
}
