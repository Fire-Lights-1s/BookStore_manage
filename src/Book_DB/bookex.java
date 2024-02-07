package Book_DB;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;


class BookdbView3 extends JFrame{
	JPanel[] pnl=new JPanel[2];
	JLabel[] label=new JLabel[4];
	JTextField[] tf=new JTextField[4];
	JButton[] button=new JButton[4];
	JTextArea ta=new JTextArea();
	BookdbView3() {
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
		setSize(800,400);
		setResizable(false);
		setVisible(true);
	}
}
public class bookex {
	BookdbView2 v=new BookdbView2();
	ActionHandler handler=new ActionHandler();
	Connection con=null;
	Statement stmt=null;
	ResultSet rs=null;

	public bookex (){
		v.button[0].addActionListener(handler);
		v.button[1].addActionListener(handler);
		v.button[2].addActionListener(handler);
		v.button[3].addActionListener(handler);
	}

	class ActionHandler implements ActionListener {
		StringBuilder sb = new StringBuilder();
		public void actionPerformed(ActionEvent e) {
			makeConnection();//������ ���̽� ����
			String sql;
			
			if(e.getSource()==v.button[0]) {	//�߰�
				if(!v.tf[0].getText().equals("") && !v.tf[1].getText().equals("")&&
						!v.tf[2].getText().equals("") && !v.tf[3].getText().equals("")) {

					sql = sb.append("insert into book values(")
							.append(v.tf[0].getText() + ",")
							.append( v.tf[1].getText() + ",")
							.append( v.tf[2].getText() + ",")
							.append(v.tf[3].getText())
							.append(");")
							.toString();
					GoSql(sql);
					Show();					
				}else {
					Show();
					v.ta.append("  ��� ���� �Է��ϼ���");
				}
			}
			else if(e.getSource()==v.button[1]) { //��ȸ
				sql="SELECT * FROM book";
				String row="";
				v.ta.setText("");
				try {
					System.out.println(sql+"\n");
					rs=stmt.executeQuery(sql);
					while(rs.next()) {
						row=rs.getString("id")+"\t";
						row+=rs.getString("title")+"\t\t";
						row+=rs.getString("publisher")+"\t\t";
						row+=rs.getString("price")+"\n";
						v.ta.append(row);
						row="";
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}else if(e.getSource()==v.button[2]) { //����
				sql = sb.append("update book set ").toString();
				if(!v.tf[1].getText().equals("")) {
					sql = sb.append("title='"+ v.tf[1].getText()+"'").toString();
				}
				if(!v.tf[2].getText().equals("")) {
					sql = sb.append(", publisher='"+ v.tf[2].getText()+"'").toString();
				}
				if(!v.tf[3].getText().equals("")) {
					sql = sb.append(", price="+ v.tf[3].getText()).toString();
				}
				if(!v.tf[0].getText().equals("")&&
						(!v.tf[1].getText().equals("")|| !v.tf[2].getText().equals("")|| !v.tf[3].getText().equals(""))) {
					sql = sb.append(" where id='"+ v.tf[0].getText()+"'").toString();
					GoSql(sql);
					v.tf[1].setText("");
					v.tf[2].setText("");
					v.tf[3].setText("");
					Show();
				}else {
					Show();
					v.ta.append("   id ���� �Է��ϰ� ������ �� �߿��� ������ ���� �Է��ϼ���.");
				}
				
				
			}else if(e.getSource()==v.button[3]) { //����
				sql = sb.append("delete from book where ").toString();
				if(!v.tf[0].getText().equals("")){
					sql = sb.append("id='"+v.tf[0].getText()+"'").toString();
					GoSql(sql);
					v.tf[1].setText("");
					v.tf[2].setText("");
					v.tf[3].setText("");
					Show();
				}else {
					Show();
					v.ta.append("id ���� �޾Ƽ� �����մϴ�.");
				}
			}
			sb.setLength(0);
			disConnection();//������ ���̽� ���� ����	
		}
	}
	private void GoSql(String sql) {
		try {
			stmt.executeUpdate(sql);
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println(sql);
		}
	}
	void Show() {
		String sql;
		sql="SELECT * FROM book";
		String row="";
		v.ta.setText("");
		try {
			System.out.println(sql+"\n");
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				row=rs.getString("id")+"\t";
				row+=rs.getString("title")+"\t\t";
				row+=rs.getString("publisher")+"\t\t";
				row+=rs.getString("price")+"\n";
				v.ta.append(row);
				row="";
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("show");
		}
	}
	public Connection makeConnection(){
		String url="jdbc:mysql://localhost:3306/book_db?serverTimezone=Asia/Seoul";
		String id="root";
		String password="tkaghk3301@@";
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

		new bookex();
	}

}
