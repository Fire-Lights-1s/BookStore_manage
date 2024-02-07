package BookStore;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class BookStock extends JPanel {
	JLabel[] lbl = new JLabel[5];// 고객ID,책ID, 주소, 결제방식(JList<E>)
	JButton[] bt = new JButton[4]; //추가, 조회, 조회, 삭제
	JTextField[] tf = new JTextField[5];

	Object[][] ob = new Object[0][5];

	DefaultTableModel tModel;
	JTable table;
	
	StockMAction mAction = new StockMAction();
	BookStockAction action = new BookStockAction();
	BookStoreDB_connection connect = new BookStoreDB_connection();//mySQL 연결할때 쓸 클래스를 만듬
	
	BookStock(){
		connect.makeConnection();
		setPanel();
		Show();
		connect.disConnection();
	}
	void setPanel(){
		String[] btS = {"Append", "Show", "Update", "Delete"};
		String[] lbS = {"책ID","제목","출판사ID","가격","재고"};
		String[] tName= {"ID","제목","출판사ID","가격","재고"};
		tModel = new DefaultTableModel(ob,tName);
		table = new JTable(tModel);
		table.addMouseListener(mAction);
		JPanel pl1= new JPanel();
		JPanel pl2= new JPanel();

		FlowLayout flow = new FlowLayout();
		BorderLayout border = new BorderLayout();
		setLayout(border);
		pl1.setLayout(flow);

		for(int i=0; i < lbl.length; i++) {

			lbl[i]=new JLabel(lbS[i]);

			tf[i]=new JTextField(10);					

			pl1.add(lbl[i]);
			pl1.add(tf[i]);

		}
		for(int i=0; i<bt.length; i++) {
			bt[i] = new JButton(btS[i]);
			bt[i].addActionListener(action);
			pl2.add(bt[i]);
		}
		add(pl1, BorderLayout.NORTH);
		add(new JScrollPane(table),BorderLayout.CENTER);
		add(pl2, BorderLayout.SOUTH);

	}
	class StockMAction extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();
			
			String ID = (String)table.getValueAt(row, 0); 
			String title = (String)table.getValueAt(row, 1); 
			String publisherID = (String)table.getValueAt(row, 2); 
			String price = (String)table.getValueAt(row, 3); 
			String stock = (String)table.getValueAt(row, 4); 

			tf[0].setText(ID);
			tf[1].setText(title);
			tf[2].setText(publisherID);
			tf[3].setText(price);
			tf[4].setText(stock);
			
		}

		
	}
	class BookStockAction implements ActionListener{
		StringBuilder sb = new StringBuilder();
		public void actionPerformed(ActionEvent e) {
			JButton checkbt = (JButton)e.getSource();//액션이 일어난 값을 JButton으로 바꿈

			String sql;
			
			String[] attribute = {"ID", "title", "publisherID", "price", "stock"};//택스트 필드 체크용
			String[] addAttri = new String[attribute.length];//값이 있는 값의 속성
			int[] index= new int[attribute.length];// 텍스트 필드에서 값을 가져올 위치
			int count=0;// 값이 있는 텍스트 필드의 수
			
			connect.makeConnection();

			if(checkbt.equals(bt[0])) {//추가
				if(Check(1,attribute,index)) {//중복값이 있을때 확인 처음 인자가 1인 이유는 ID값만 확인하기 위해
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("ID 값이 중복입니다.");
					
				}else if(!CheckForeign(tf[2].getText().toString(),"publisher")){// 외래키가 있는지 확인함
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("없는 출판사ID 입니다.");
				}else{
					System.out.println(bt[0].getText());
					System.out.println();
					for(int i=0; i < attribute.length; i++) {//값이 있는 텍스트 필드를 확인하고 저장
						if(!tf[i].getText().equals("")) {
							addAttri[count] = attribute[i];
							index[count]= i;
							count++;
						}
					}
					if(tf[0].getText().equals("")||tf[1].getText().equals("")||tf[3].getText().equals("")||tf[4].getText().equals("")) {
						System.out.println("ID,제목,가격,수량 값이 있어야함");
						MessegeWindow mess = new MessegeWindow();
						mess.memo.setText("ID,이름,주소 값이 있어야합니다.");
					}else {
						sql = sb.append("insert into bookstock(").toString();
						for(int i=0; i < count; i++) {
							if(i==count-1) {
								sql = sb.append(addAttri[i]).toString();
							}else 
								sql = sb.append(addAttri[i]+",").toString();
						}
						sql = sb.append(") values(").toString();

						for(int i=0; i < count; i++) {
							if(i==count-1) {
								sql = sb.append("?").toString();
							}else 
								sql = sb.append("?,").toString();
						}
						sql = sb.append(");").toString();//gender

						Gosql(count, index, sql);
						Show();
						setEmpty();
					}
				}
			}else if(checkbt.equals(bt[1])) {//조회
				System.out.println(bt[1].getText());
				System.out.println();
				for(int i=0; i < attribute.length; i++) {//값이 있는 텍스트 필드 확인
					if(!tf[i].getText().equals("")) {
						addAttri[count] = attribute[i];//값이 있는 속성 이름 저장
						index[count]= i;//값이 있는 텍스트 필드 인덱스 저장
						count++;
					}
				}
				//sql문 생성
				if(tf[0].getText().equals("")&&tf[1].getText().equals("")
						&&tf[2].getText().equals("")&&tf[3].getText().equals("")&&tf[4].getText().equals("")) {

					sql = sb.append("select * FROM bookstock  ").toString();
				}else {
					sql = sb.append("select * FROM bookstock where ").toString();
				}
				for(int i=0; i < count; i++) {
					if(i==count-1) {
						sql = sb.append(addAttri[i]+"=? ").toString();
					}else 
						sql = sb.append(addAttri[i]+"=? and ").toString();
				}
					SelectShow(count, index, sql);
					setEmpty();
					
			}else if(checkbt.equals(bt[2])) {//수정
				System.out.println(bt[2].getText());
				System.out.println();
				for(int i=1; i < attribute.length; i++) {//값이 있는 텍스트 필드를 확인하고 저장
					if(!tf[i].getText().equals("")) {
						addAttri[count] = attribute[i];//값이 있는 속성 이름 저장
						index[count]= i;//값이 있는 텍스트 필드 인덱스 저장
						count++;
					}
				}
				if(tf[0].getText().equals("")) {
					System.out.println("ID 값이 있어야함");
					MessegeWindow mess = new MessegeWindow();//메세지 윈도우를 클래스로 만들어둠
					mess.memo.setText("ID 값이 있어야 됩니다.");
				
				}else {//sql문 생성
					sql = sb.append("update bookstock set ").toString();
					for(int i=0; i < count; i++) {// i=1인 이유는 ID는 마지막에 넣을거라서
						if(i==count-1) {
							sql = sb.append(addAttri[i]+"=? ").toString();
						}else 
							sql = sb.append(addAttri[i]+"=? , ").toString();
					}
					sql = sb.append(" where ID='"+tf[0].getText()+"'").toString();

					Gosql(count, index, sql);
					Show();
					setEmpty();
				}
			}else if(checkbt.equals(bt[3])) {//삭제
				System.out.println();
				System.out.println(bt[3].getText());
				for(int i=0; i < attribute.length; i++) {//값이 있는 텍스트 필드 확인
					if(!tf[i].getText().equals("")) {
						addAttri[count] = attribute[i];
						index[count]= i;
						count++;
					}
				}
				if(count==0) {
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("입력된 값이 없습니다.");
				}else {
					if(Check(count, addAttri, index)) {//true 일때 삭제할 값이 있음
						DeleteV DV = new DeleteV();
						DV.Dbutton[0].addActionListener(action);
						DV.Dbutton[1].addActionListener(action);
						TFEnable();

					}else {
						System.out.println("삭제할 값이 없음");
						MessegeWindow mess = new MessegeWindow();
						mess.memo.setText("삭제할 값이 없습니다.");
					}//if check else 
				}
			}else if(checkbt.getText().equals("Yes")) {//삭제 확인 했을때 삭제
				TFAble();
				for(int i=0; i < attribute.length; i++) {//값이 있는 텍스트 필드 확인
					if(!tf[i].getText().equals("")) {
						addAttri[count] = attribute[i];
						index[count]= i;
						count++;
					}
				}
				if(tf[0].getText().equals("")) {
					System.out.println("ID값이 부족함");
				}else {
					sql = sb.append("delete FROM bookstock where ").toString();

					for(int i=0; i < count; i++) {
						if(i==count-1) {
							sql = sb.append(addAttri[i]+"=? ").toString();
						}else 
							sql = sb.append(addAttri[i]+"=? and ").toString();
					}
					Gosql(count, index, sql);
					Show();
					setEmpty();
				}
			}else if(checkbt.getText().equals("No")) {
				TFAble();
			}
			sb.setLength(0);
			connect.disConnection();
		}	
	}
	boolean CheckForeign(String key ,String DB_table) {//외래키가 있는지 확인할때 사용
		String sql;
		StringBuilder sb= new StringBuilder();

		boolean result=false;
		sql = sb.append("select * from ? where ID=?").toString();

		try {
			connect.pstmt = connect.con.prepareStatement(sql);
			connect.pstmt.setString(1, DB_table);
			connect.pstmt.setString(2, key);
			connect.rs = connect.pstmt.executeQuery();

			if(connect.rs.next()) {
				result=true;
			}else {
				result=false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("외래키 중복확인 중에 오류 발생");
			System.out.println(sql);
			System.out.println();
		}
		return result;

	}
	boolean Check(int count,String[] addAttri, int[] index) {// 기본키 중복확인, 삭제할값이 있는지 확인할때 사용 
		String sql;
		StringBuilder sb= new StringBuilder();

		boolean result=false;
		if(count == 0) {
			return result=false;
		}
		sql = sb.append("select * from bookstock where ").toString();

		for(int i=0; i < count; i++) {
			if(i==count-1) {
				sql = sb.append(addAttri[i] + "=?").toString();
			}else 
				sql = sb.append(addAttri[i] + "=? and ").toString();
		}

		sql = sb.append(";").toString();//gender

		try {
			connect.pstmt = connect.con.prepareStatement(sql);
			for(int i=0; i < count; i++) {
				connect.pstmt.setString(i+1, tf[index[i]].getText());
			}

			connect.rs = connect.pstmt.executeQuery();
			if(connect.rs.next()) {
				result=true;
			}else {
				result=false;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("중복확인 중에 오류 발생");
			System.out.println(sql);
			System.out.println();
		}
		return result;
	}
	void TFEnable(){ // 텍스트 필드, 버튼 비활성화
		for(int i=0;i<bt.length;i++) {
			bt[i].setEnabled(false);
		}for(int i=0; i < tf.length; i++) {
			tf[i].setEnabled(false);
		}
	}
	void TFAble(){ // 텍스트 필드, 버튼   활성화
		for(int i=0;i<bt.length;i++) {
			bt[i].setEnabled(true);
		}for(int i=0; i < tf.length; i++) {
			tf[i].setEnabled(true);
		}
	}
	void setEmpty() {//텍스트 필드 초기화
		for(int i=0; i < tf.length; i++) {
			tf[i].setText("");
		}
	}
	void Show() {//DB테이블을 JTable에  출력 
		String sql;
		sql="SELECT * FROM bookstock";
		tModel.setNumRows(0);
		try {
			System.out.println(sql+"\n");
			connect.rs=connect.stmt.executeQuery(sql);
			while(connect.rs.next()) {
				Object[] data= {connect.rs.getString("ID")
						,connect.rs.getString("title")
						,connect.rs.getString("publisherID")
						,connect.rs.getString("price")
						,connect.rs.getString("stock")};
				tModel.addRow(data);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("show");
			System.out.println();
		}
	}
	void SelectShow(int count, int[] index, String sql) { //조건에 맞는 DB테이블을 JTable에  출력 
		tModel.setNumRows(0);
		try {
			connect.pstmt = connect.con.prepareStatement(sql);
			for(int i=0; i < count; i++) {
				connect.pstmt.setString(i+1, tf[index[i]].getText());
			}

			connect.rs = connect.pstmt.executeQuery();
			System.out.println(sql);
			while(connect.rs.next()) {
				Object[] data= {connect.rs.getString("ID")
						,connect.rs.getString("title")
						,connect.rs.getString("publisherID")
						,connect.rs.getString("price")
						,connect.rs.getString("stock")};
				tModel.addRow(data);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("show");
			System.out.println(sql);
			System.out.println();
		}
	}
	void Gosql(int count, int[] index, String sql) { // SQL문 받아서 실행
		try {
			connect.pstmt = connect.con.prepareStatement(sql);
			for(int i=0; i < count; i++) {
				connect.pstmt.setString(i+1, tf[index[i]].getText());
			}

			int result = connect.pstmt.executeUpdate();
			if(result==1) {
				System.out.println("Board데이터 삽입 성공!");
				System.out.println(sql);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("sql 실행중 오류");
			System.out.println(sql);
		}
	}
	public static void main(String[] args) {
		new BookStock();
	}

}
