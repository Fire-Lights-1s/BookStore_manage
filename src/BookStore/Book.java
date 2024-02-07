package BookStore;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;


class BookdbView extends JFrame{
	JPanel[] pnl=new JPanel[2];
	JLabel[] label=new JLabel[4];
	JTextField[] tf=new JTextField[4];
	JButton[] button=new JButton[4];
	
	Object[][] ob = new Object[0][5];
	DefaultTableModel tModel;
	JTable table ;
	
	BookdbView() {
		String[] lbl_tf= {"ID","Ttile","Publisher","Price"};
		String[] lbl_button= {"�߰�","��ȸ","����","����"};
		String[] tName= {"ID","����","���ǻ�","����","��ϳ�¥"};
		Container c=getContentPane();
		pnl[0]=new JPanel();
		pnl[1]=new JPanel();
		
		tModel = new DefaultTableModel(ob,tName);
		table = new JTable(tModel);

		for(int i=0;i<4;i++) {
			tf[i]=new JTextField(10);
			label[i]=new JLabel(lbl_tf[i]);
			button[i]=new JButton(lbl_button[i]);
			tf[i].setEnabled(false);
			button[i].setEnabled(false);
		}
		tf[0].setEnabled(true);
		tf[0].setBackground(Color.LIGHT_GRAY);
		button[1].setEnabled(true);
		for(int i=0;i<4;i++) {
			pnl[0].add(label[i]);
			pnl[0].add(tf[i]);
			pnl[1].add(button[i]);
		}
		c.add(pnl[0],BorderLayout.NORTH);
		c.add(new JScrollPane(table),BorderLayout.CENTER);
		c.add(pnl[1],BorderLayout.SOUTH);

		setTitle("���� ����");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(300, 300, 800, 400);
		setResizable(false);
		setVisible(true);
	}
}
public class Book {
	BookdbView v = new BookdbView();
	ActionHandler handler=new ActionHandler();
	EnterHandler enterHandler=new EnterHandler();
	Connection con=null;
	Statement stmt=null;
	ResultSet rs=null;

	public Book (){
		makeConnection();
		ShowT();
		disConnection();
		v.button[0].addActionListener(handler);
		v.button[1].addActionListener(handler);
		v.button[2].addActionListener(handler);
		v.button[3].addActionListener(handler);
		v.tf[0].addActionListener(enterHandler);
	}
	class EnterHandler implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			System.out.println("enter");
			boolean yn = false;
			if(e.getSource()== v.tf[0]) {
				makeConnection();
				yn = IDcheck(v.tf[0].getText());
				for(int i=0; i < v.tf.length; i++) {
					v.tf[i].setEnabled(true);
				}
				if(yn) {
					//������ ���� ���� Ȱ��ȭ �߰� ��Ȱ��ȭ
					v.button[0].setEnabled(false);
					v.button[2].setEnabled(true);
					v.button[3].setEnabled(true);
				}else {// �ݴ�
					v.button[0].setEnabled(true);
					v.button[2].setEnabled(false);
					v.button[3].setEnabled(false);
				}
			}
		}
	}
	class ActionHandler implements ActionListener {
		StringBuilder sb = new StringBuilder();
		public void actionPerformed(ActionEvent e) {
			makeConnection();//������ ���̽� ����
			String sql; // sql ��ɾ� ������ �κ�
			boolean comma=false; // �տ� �ٸ� �Ӽ��� �ִ��� Ȯ��
			String id = v.tf[0].getText();
			String title = v.tf[1].getText();
			String publisher = v.tf[2].getText();
			String price = v.tf[3].getText();
			JButton bt=(JButton) e.getSource();
			if(e.getSource()==v.button[0]) {	//�߰�
				if(IDcheck(id)) { //�ߺ� Ȯ��
					ShowT();
				}else
					if(!id.equals("") && !title.equals("")&&
							!publisher.equals("") && !price.equals("")) {

						sql = sb.append("insert IGNORE into book(id,title,publisher,price) values(")
								.append("'"+id + "',")
								.append("'"+title + "',")
								.append("'"+publisher + "','")
								.append(price+"'")
								.append(");")
								.toString();
						GoSql(sql);
						ShowT();	
						setTextFiled();
						enable();
					}else {
						ShowT();
						Object[] erT = {"��� ���� �Է��ϼ���","","",""};
						v.tModel.addRow(erT);
					}
			}
			else if(e.getSource()==v.button[1]) { //��ȸ
				if(id.equals("")&&title.equals("")&&publisher.equals("")&&price.equals("")) {
					ShowT();					
				}else {
					sql = sb.append("select * FROM book where ").toString();
					if(!id.equals("")) {
						sql = sb.append("id='"+id+"'").toString();
						comma = true;
					}
					if(!title.equals("")) {
						if(comma) { // �տ� ���� ������ and 
							sb.append(" and ");
						}
						sql = sb.append("title='"+title+"'").toString();
						comma = true;
					}
					if(!publisher.equals("")) {
						if(comma) {
							sb.append(" and ");
						}
						sql = sb.append("publisher='"+publisher+"'").toString();
						comma = true;
					}
					if(!price.equals("")) {
						if(comma) {
							sb.append(" and ");
						}
						sql = sb.append("price='"+price+"'").toString();
					}
					Show2(sql);
				}
			}else if(e.getSource()==v.button[2]) { //����
				sql = sb.append("update book set ").toString();
				if(!title.equals("")) {
					sql = sb.append("title='"+ title+"'").toString();
					comma = true;
				}
				if(!publisher.equals("")) {
					if(comma) { // �տ� ���� �ָ��� �޸�
						sb.append(", ");
					}
					sql = sb.append(" publisher='"+ publisher+"'").toString();
					comma = true;
				}
				if(!price.equals("")) {
					if(comma) {
						sb.append(", ");
					}
					sql = sb.append(" price="+ price).toString();
				}
				if(!id.equals("")&&
						(!title.equals("")|| !publisher.equals("")|| !price.equals(""))) {
					sql = sb.append(" where id='"+ id+"'").toString();
					GoSql(sql);
					v.tf[1].setText("");
					v.tf[2].setText("");
					v.tf[3].setText("");
					ShowT();
					enable();
				}else {
					ShowT();
					Object[] erT = {"ID�� �Է��ϰ� ","������ ���� ","�Է����ּ���",""};
					v.tModel.addRow(erT);
				}
			}else if(e.getSource()==v.button[3]) { //����
				if(!id.equals("")){
					DeleteView DV = new DeleteView();
					DV.Dbutton[0].addActionListener(handler);
					v.tf[0].setText("");
					v.tf[1].setText("");
					v.tf[2].setText("");
					v.tf[3].setText("");
					ShowT();
					enable();
				}else {
					ShowT();
					Object[] erT = {"ID������ �����մϴ�.","","",""};
					v.tModel.addRow(erT);
				}
			}else if(bt.getText().equals("Yes")) {
				sql = sb.append("delete from book where ")
						.append("id='"+id+"'").toString();
				GoSql(sql);
				ShowT();
				setTextFiled();
				enable();
			}
			sb.setLength(0);
			disConnection();//������ ���̽� ���� ����	
		}
	}
	boolean IDcheck(String inid) { //id�ߺ� üũ�ϴ� �޼ҵ�
		boolean result=false;
		if(inid.equals("")) {
			return result;
		}
		StringBuilder sb = new StringBuilder();
		String sql = "select id from book where id=";
		sql = sb.append(sql+"'"+inid+"'").toString();
		try {
			rs = stmt.executeQuery(sql);
			if(rs.next()) {
				result=true;
			}else {
				result=false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("�ߺ�Ȯ�� �߿� ���� �߻�");
		}
		return result;
	}
	void enable(){ // id �ؽ�Ʈ �ʵ�� ��ȸ ��ư�� ���� ��Ȱ��ȭ
		for(int i=0;i<4;i++) {
			v.tf[i].setEnabled(false);
			v.button[i].setEnabled(false);
		}
		v.tf[0].setEnabled(true);
		v.button[1].setEnabled(true);
	}
	void setTextFiled() {//�ؽ�Ʈ �ʵ� �ʱ�ȭ
		for(int i=0; i< v.tf.length; i++) {
			v.tf[i].setText("");
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
	void ShowT() {
		String sql;
		sql="SELECT * FROM book";
		v.tModel.setNumRows(0);
		try {
			System.out.println(sql+"\n");
			rs = stmt.executeQuery(sql);
			while(rs.next()) {
				Object[] data= {rs.getString("id")
						,rs.getString("title")
						,rs.getString("publisher")
						,rs.getString("price")
						,rs.getString("indate")};
				v.tModel.addRow(data);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("show");
		}
	}
	void Show2(String sql) {
		v.tModel.setNumRows(0);
		try {
			System.out.println(sql+"\n");
			rs=stmt.executeQuery(sql);
			while(rs.next()) {
				Object[] data= {rs.getString("id")
						,rs.getString("title")
						,rs.getString("publisher")
						,rs.getString("price")
						,rs.getString("indate")};
				v.tModel.addRow(data);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("show");
		}
	}
	public Connection makeConnection(){
		String url="jdbc:mysql://localhost:3306/guidb?serverTimezone=Asia/Seoul";
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
	class DeleteView extends JFrame{ // ���� ��ư�� ���������� ������
		JButton[] Dbutton= new JButton[3];
		String[] DString= {"Yes","No","cancel"};
		JLabel memo = new JLabel();
		DeleteView(){
			setTitle("Delete");
			setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

			Container c = getContentPane();
			FlowLayout flow = new FlowLayout();
			c.setLayout(flow);

			Font font = new Font("SanSerif", Font.BOLD, 18);
			memo.setText("���� �����Ͻðڽ��ϱ�?");
			memo.setFont(font);
			c.add(memo);
			for(int i=0; i < Dbutton.length; i++) {
				Dbutton[i] = new JButton(DString[i]);
				Dbutton[i].addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				c.add(Dbutton[i]);
			}
			setBounds(500, 500, 250, 120);
			setResizable(false);
			setVisible(true);
		}

	}
	public static void main(String[] args) {

		new Book();
	}


}
