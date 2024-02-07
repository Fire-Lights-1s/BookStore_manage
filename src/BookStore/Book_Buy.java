package BookStore;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class Book_Buy{
	BookStoreDB_connection con = new BookStoreDB_connection();
	Book_Buy_look BB = new Book_Buy_look();
	String str = "select * from bookstock";
	Book_Buy(){
		con.makeConnection();
		try {
			BB.model.setNumRows(0);
			
			BB.btn[0].addActionListener(new MyActionListener());
			BB.btn[1].addActionListener(new MyActionListener());
			con.rs = con.stmt.executeQuery(str);
			while(con.rs.next()) {
				Object[] info = {con.rs.getString("id"),con.rs.getString("title"),con.rs.getString("PublisherID"),con.rs.getString("price"),con.rs.getString("stock")};
				BB.model.addRow(info);
			}
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		con.disConnection();
	}
	
	class MyActionListener implements ActionListener{

		
		public void actionPerformed(ActionEvent e) {
			con.makeConnection();
			JButton checkbtn = (JButton)e.getSource();
			if(checkbtn.equals(BB.btn[0])) {
				if(BB.tf.equals("")) {
					BB.model.setNumRows(0);
					try {
						con.rs = con.stmt.executeQuery(str);
						while(con.rs.next()) {
							Object[] info = {con.rs.getString("id"),con.rs.getString("title"),con.rs.getString("PublisherID"),con.rs.getString("price"),con.rs.getString("stock")};
							BB.model.addRow(info);
							System.out.print("d");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				else if(!BB.tf.equals("")) {
					BB.model.setNumRows(0);
					String str2 = " where id = '";
					try {
						con.rs = con.stmt.executeQuery(str + str2 + BB.tf.getText() + "'");
						while(con.rs.next()) {
							Object[] info = {con.rs.getString("id"),con.rs.getString("title"),con.rs.getString("PublisherID"),con.rs.getString("price"),con.rs.getString("stock")};
							BB.model.addRow(info);
							System.out.print("dd");
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			else if(e.getSource() == BB.btn[1])
				System.out.print("구매");
			con.disConnection();
		}
	
	}
	
	public static void main(String[] args) {
		new Book_Buy();
	}
}

class Book_Buy_look extends JFrame{

	Container c = getContentPane();
	
	
	JPanel[] pnl = new JPanel[3];
	JTextField tf = new JTextField(40);
	JButton[] btn = new JButton[2];
	
	String[] head = {"책번호","제목","가격","수량"};
	Object[][] list = new Object[0][4];
	DefaultTableModel model = new DefaultTableModel(list,head);
	JTable table = new JTable(model);
	
	Book_Buy_look(){
		c.setLayout(new BorderLayout());
		setTitle("책 구입 프로그램 (고객용)");
		
		for(int i = 0;i<3;i++) {
			pnl[i] = new JPanel();
		}
		pnl[0].add(tf);
		btn[0] = new JButton("검색");
		btn[1] = new JButton("구매");
		for(int i = 0;i<2;i++) {
			pnl[0].add(btn[i]);
		}
		c.add(pnl[0],BorderLayout.NORTH);
		c.add(new JScrollPane(table),BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(700,500);
		setVisible(true);
		
		
		}
	

}
