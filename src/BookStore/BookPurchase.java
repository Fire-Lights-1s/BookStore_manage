package BookStore;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class BookPurchase extends JPanel{
	JLabel[] lbl = new JLabel[4];// 주문번호, 주문수량, 책ID, 출판사ID, 주문날짜
	JTextField[] tf = new JTextField[4];
	JButton[] bt = new JButton[4]; //추가, 조회, 삭제
	JTextField[] inOder = new JTextField[4];
	Object[][] ob = new Object[0][6];
	DefaultTableModel tModel;
	JTable table;
	JPanel[] pl = new JPanel[2];
	BookPurchaseAction action = new BookPurchaseAction();

	BookStoreDB_connection connect = new BookStoreDB_connection();

	BookPurchase(){
		connect.makeConnection();
		setPanel();
		Show();
		connect.disConnection();
	}
	void setPanel() {
		String[] lbS = {"주문번호","주문수량","책 ID","출판사 ID","주문날짜"};
		String[] tableS = {"주문번호","주문수량","책 ID","출판사 ID","주문날짜"};
		String[] btS = {"Append", "Show", "Update","Delete"};
		pl[0] = new JPanel();
		pl[1] = new JPanel();

		BookPurchaseMAction tableMA = new BookPurchaseMAction();
		tModel = new DefaultTableModel(ob, tableS);
		table = new JTable(tModel);
		table.addMouseListener(tableMA);
		
		
		BorderLayout border = new BorderLayout();
		setLayout(border);

		for(int i=0; i < lbl.length; i++) {
			if(i==4) {
				lbl[i] = new JLabel(lbS[i]);
				tf[i] = new JTextField(20);
				pl[0].add(lbl[i]);
				pl[0].add(tf[i]);
			}else {
				lbl[i] = new JLabel(lbS[i]);
				tf[i]= new JTextField(10);
				pl[0].add(lbl[i]);
				pl[0].add(tf[i]);
			}
		}
		
		tf[0].setText("0");
		
		for(int i=0; i<bt.length; i++) {
			bt[i] = new JButton(btS[i]);
			bt[i].addActionListener(action);
			pl[1].add(bt[i]);
		}
		add(pl[0], BorderLayout.NORTH);
		add(new JScrollPane(table), BorderLayout.CENTER);
		add(pl[1], BorderLayout.SOUTH);
	}
	class BookPurchaseMAction extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();
			
			String no = (String)table.getValueAt(row, 0); 
			String oderQuantity = (String)table.getValueAt(row, 1); 
			String bookID = (String)table.getValueAt(row, 2); 
			String publisherID = (String)table.getValueAt(row, 3); 
			System.out.println(no + oderQuantity+ bookID + publisherID);
			tf[0].setText(no);
			tf[1].setText(oderQuantity);
			tf[2].setText(bookID);
			tf[3].setText(publisherID);
			
		}

		
	}
	class BookPurchaseAction implements ActionListener{
		StringBuilder sb = new StringBuilder();
		public void actionPerformed(ActionEvent e) {
			JButton checkbt = (JButton)e.getSource();
			String[] attribute = {"no", "oderQuantity", "bookID", "publisherID"};//택스트 필드 체크용
			//"oderMethod",
			String[] addAttri = new String[attribute.length];
			int[] index= new int[attribute.length];
			String sql;
			int count=0;
			connect.makeConnection();

			if(checkbt.equals(bt[0])) {//append
				
				Calendar today = Calendar.getInstance();
				
				long year = today.get(Calendar.YEAR) - 2000;
				long month = today.get(Calendar.MONTH) + 1;
				long date = today.get(Calendar.DATE);
				long hour = today.get(Calendar.HOUR_OF_DAY);
				long minute = today.get(Calendar.MINUTE);
				long second = today.get(Calendar.SECOND);
				
				System.out.println();
				System.out.println();
				System.out.println(year);
				System.out.println(month);
				System.out.println(date);
				System.out.println(hour);
				System.out.println(minute);
				System.out.println(second); 
				
				long saleno = month * 100000000 + date * 1000000 + hour * 10000 + minute * 100 + second;
				for(int i1 = 0; i1 < 10; i1++)
					saleno += year * 1000000000;
				System.out.println(saleno);
				tf[0].setText("" + saleno);
				
				if(Check(1,attribute,index,"book_purchase")) {//중복값이 있을때 확인 처음 인자가 1인 이유는 ID값만 확인하기 위해
					tf[0].setText( 10* saleno+Math.random()*10+"");
				//	MessegeWindow mess = new MessegeWindow();
				//	mess.memo.setText("ID 값이 중복입니다.");

				}else if( !CheckForeign("ID", tf[2].getText().toString(),"bookstock") && !tf[2].getText().toString().equals("")) {
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("없는 책ID 입니다.");
				}else if( !CheckForeign("ID", tf[3].getText().toString(),"publisher")&& !tf[3].getText().toString().equals("")) {
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("없는 출판사ID 입니다.");
				}else {
					System.out.println(bt[0].getText());
					System.out.println();
					for(int i=0; i < attribute.length; i++) {//값이 있는 텍스트 필드를 확인하고 저장
						if(!tf[i].getText().equals("")) {
							addAttri[count] = attribute[i];
							index[count]= i;
							count++;
						}
					}
					if(tf[1].getText().equals("")||tf[2].getText().equals("")) {
						MessegeWindow mess = new MessegeWindow();
						mess.memo.setText("수량,책 값이 있어야합니다.");
					}else {
						sql = sb.append("insert into book_purchase(").toString();
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
						String sql2="update bookstock set stock = stock+"+ tf[1].getText().toString()+" where ID='"+tf[2].getText().toString()+"'";
						Gosql(0,null,sql2);
						
						Gosql(count, index, sql);
						tf[0].setText("0");
						Show();
						setEmpty();
					}
				}
			}else if(checkbt.equals(bt[1])) {
				System.out.println(bt[1].getText());
				System.out.println();
				for(int i=0; i < attribute.length; i++) {//값이 있는 텍스트 필드 확인
					if(!tf[i].getText().equals("")) {
						addAttri[count] = attribute[i];
						index[count]= i;
						count++;
					}
				}
				if(tf[0].getText().equals("")&&tf[1].getText().equals("")
						&&tf[2].getText().equals("")&&tf[3].getText().equals("")) {

					sql = sb.append("select * FROM book_purchase  ").toString();
				}else {
					sql = sb.append("select * FROM book_purchase where ").toString();
				}
				for(int i=0; i < count; i++) {
					if(i==count-1) {
						sql = sb.append(addAttri[i]+"=? ").toString();
					}else 
						sql = sb.append(addAttri[i]+"=? and ").toString();
				}

				SelectShow(count, index, sql);
			}else if(checkbt.equals(bt[2])) {
				System.out.println(bt[2].getText());
				System.out.println();
				//값이 있는 텍스트 필드를 확인하고 저장
				for(int i=1; i < attribute.length; i++) {// i=1인 이유는 ID는 마지막에 따로 넣을거라서
					if(!tf[i].getText().equals("")) {
						addAttri[count] = attribute[i];
						index[count]= i;
						count++;
					}
				}
				System.out.println(count);
				if(tf[0].getText().equals("")) {
					System.out.println("ID 값이 있어야함");
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("ID 값이 있어야 됩니다.");

				}else if(tf[1].getText().equals("")&&tf[2].getText().equals("")&&tf[3].getText().equals("")){
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("변경될 값이 있어야 됩니다.");

				}else{
					sql = sb.append("update book_purchase set ").toString();
					for(int i=0; i < count; i++) {
						if(i==count-1) {
							sql = sb.append(addAttri[i]+"=? ").toString();
						}else 
							sql = sb.append(addAttri[i]+"=? , ").toString();
					}
					sql = sb.append(" where no='"+tf[0].getText()+"'").toString();

					System.out.println("SQL 만듬");
					System.out.println(sql);
					if(!tf[1].getText().toString().equals("")) {
						String sql2= "update bookstock set stock = stock-"
								+ AttributeData("no",tf[0].getText(),"book_purchase","oderQuantity")+"+"+ tf[1].getText().toString()
								+ " where ID='"+AttributeData("no",tf[0].getText(),"book_purchase","bookID")+"'";
						Gosql(0, null, sql2);
					}
					Gosql(count, index, sql);
					Show();
					setEmpty();
				}
			}else if(checkbt.equals(bt[3])) {
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
					if(Check(count, addAttri, index,"book_purchase")) {//true 일때 삭제할 값이 있음
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
			}else if(checkbt.getText().equals("Yes")) {
				TFAble();
				for(int i=0; i < attribute.length; i++) {//값이 있는 텍스트 필드 확인
					if(!tf[i].getText().equals("")) {
						addAttri[count] = attribute[i];
						index[count]= i;
						count++;
					}
				}
				if(tf[0].getText().equals("")) {
					System.out.println("주문번호 값이 부족함");
				}else {
					sql = sb.append("delete FROM book_purchase where ").toString();

					for(int i=0; i < count; i++) {
						if(i==count-1) {
							sql = sb.append(addAttri[i]+"=? ").toString();
						}else 
							sql = sb.append(addAttri[i]+"=? and ").toString();
					}


					String sql2="update bookstock set stock = stock-"
							+ AttributeData("no",tf[0].getText(),"book_purchase","oderQuantity") 
							+" where ID='"
							+AttributeData("no",tf[0].getText(),"book_purchase","bookID") +"'";

					Gosql(0, null, sql2);

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
	Object AttributeData(String primaryKey, String key ,String DB_table, String getData) {//테이블에서 값을 가져옴
		String sql;
		StringBuilder sb= new StringBuilder();

		Object result = null;
		sql = sb.append("select * from "+DB_table+" where "+primaryKey+"=?").toString();
		try {
			connect.pstmt = connect.con.prepareStatement(sql);
			connect.pstmt.setString(1, key);
			connect.rs = connect.pstmt.executeQuery();
			
			if(connect.rs.next())
				result=connect.rs.getString(getData);
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("수량 확인");
		}
		return result;
	}
	boolean CheckForeign(String primaryKey, String key ,String DB_table) {//왜래키 검사
		String sql;
		StringBuilder sb= new StringBuilder();

		boolean result=false;
		if(key.equals("")) {//공백일때는 리턴
			return result = true;
		}
		sql = sb.append("select * from "+DB_table+" where "+primaryKey+"=?").toString();

		try {
			connect.pstmt = connect.con.prepareStatement(sql);
			connect.pstmt.setString(1, key);
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
	boolean Check(int count,String[] addAttri, int[] index, String DB_table) {//있는 데이터인지 확인
		String sql;
		StringBuilder sb= new StringBuilder();

		boolean result=false;
		if(count == 0) {
			return result=false;
		}
		sql = sb.append("select * from "+ DB_table +" where ").toString();

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
	void TFEnable(){ //  비활성화
		for(int i=0;i<bt.length;i++) {
			bt[i].setEnabled(false);
		}for(int i=0; i < tf.length; i++) {
			tf[i].setEnabled(false);
		}
	}
	void TFAble(){ //  활성화
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
	void Show() {
		String sql;
		sql="SELECT * FROM book_purchase";
		tModel.setNumRows(0);
		try {
			System.out.println(sql+"\n");
			connect.rs=connect.stmt.executeQuery(sql);
			while(connect.rs.next()) {
				Object[] data= {connect.rs.getString("no")
						,connect.rs.getString("oderQuantity")
						,connect.rs.getString("bookID")
						,connect.rs.getString("publisherID")
						,connect.rs.getString("oderDate")};
				tModel.addRow(data);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("show");
			System.out.println();
		}
	}
	void SelectShow(int count, int[] index, String sql) { //조건에 맞는 데이터 선택
		tModel.setNumRows(0);
		try {
			connect.pstmt = connect.con.prepareStatement(sql);
			for(int i=0; i < count; i++) {
				connect.pstmt.setString(i+1, tf[index[i]].getText());
			}

			connect.rs = connect.pstmt.executeQuery();
			System.out.println(sql+"\n");

			while(connect.rs.next()) {
				Object[] data= {connect.rs.getString("no")
						,connect.rs.getString("oderQuantity")
						,connect.rs.getString("bookID")
						,connect.rs.getString("publisherID")
						,connect.rs.getString("oderDate")};
				tModel.addRow(data);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("SelectShow error");
			System.out.println(sql);
			System.out.println();
		}
	}
	void Gosql(int count, int[] index, String sql) {
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

	}

}
