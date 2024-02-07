package BookStore;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.math.*;

public class Customer extends JPanel{
	JLabel[] lbl = new JLabel[6];// 고객ID,이름, 전화번호, 주소, 성별, 나이
	JLabel lblG;
	JComboBox<String> gender;
	JTextField[] tf = new JTextField[6];
	JButton[] bt = new JButton[4]; //추가, 조회, 조회, 삭제
	Object[][] ob = new Object[0][7];
	DefaultTableModel tModel;
	JTable table;
	JPanel[] pl= new JPanel[3];
	CustomerAction action = new CustomerAction();
	
	BookStoreDB_connection connect = new BookStoreDB_connection();//mySQL 연결할때 쓸 클래스를 만듬

	Customer(){
		connect.makeConnection();
		setPanel();
		Show();
		connect.disConnection();
	}
	void setPanel() {
		String[] lbS = {"고객ID","고객PW","이름", "전화번호", "주소", "나이"};
		String gen = "성별";
		String[] tableS = {"고객ID","고객PW","이름", "전화번호", "주소", "성별", "나이"};
		String[] btS = {"Append", "Show", "Update", "Delete"};
		String[] comboS = {"All","남성","여성"};

		BorderLayout border = new BorderLayout();
		setLayout(border);

		tModel = new DefaultTableModel(ob, tableS);
		table = new JTable(tModel);
		table.addMouseListener(new MAction());
		gender = new JComboBox<String>(comboS);
		pl[0] = new JPanel();
		JPanel upPl = new JPanel();
		upPl.setLayout(new GridLayout(2,1));
		pl[1] = new JPanel();
		pl[2] = new JPanel();
		for(int i=0; i < lbl.length; i++) {
			if(i==4) {
				lbl[i] = new JLabel(lbS[i]);
				tf[i] = new JTextField(35);
				pl[1].add(lbl[i]);
				pl[1].add(tf[i]);
				lblG = new JLabel(gen);
				pl[1].add(lblG);
				pl[1].add(gender);
			}else{
				lbl[i] = new JLabel(lbS[i]);
				tf[i] = new JTextField(10);
				pl[0].add(lbl[i]);
				pl[0].add(tf[i]);
			}			
		}
		for(int i=0; i<bt.length; i++) {
			bt[i] = new JButton(btS[i]);
			bt[i].addActionListener(action);
			pl[2].add(bt[i]);
		}
		upPl.add(pl[0]);
		upPl.add(pl[1]);
		add(upPl, BorderLayout.NORTH);
		add(new JScrollPane(table), BorderLayout.CENTER);
		add(pl[2], BorderLayout.SOUTH);

	}
	class MAction extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			int row = table.getSelectedRow();
			
			String[] attribute = {"ID","password", "name", "phoneNumber", "address", "age"};//택스트 필드 체크용

			String ID = (String)table.getValueAt(row, 0); 
			String password = (String)table.getValueAt(row, 1); 
			String name = (String)table.getValueAt(row, 2); 
			String phoneNumber = (String)table.getValueAt(row, 3); 
			String address = (String)table.getValueAt(row, 4); 
			String gen = (String)table.getValueAt(row, 5); 
			String age = (String)table.getValueAt(row, 6); 
			tf[0].setText(ID);
			tf[1].setText(password);
			tf[2].setText(name);
			tf[3].setText(phoneNumber);
			tf[4].setText(age);
			tf[5].setText(address);
			gender.setSelectedItem(gen);
			
		}
	}
	class CustomerAction implements ActionListener{
		StringBuilder sb = new StringBuilder();
		public void actionPerformed(ActionEvent e) {
			JButton checkbt = (JButton)e.getSource();
			String[] attribute = {"ID","password", "name", "phoneNumber", "address", "age"};//택스트 필드 체크용
			String[] addAttri = new String[attribute.length];
			int[] index= new int[attribute.length];
			String sql;

			connect.makeConnection();

			int count=0;
			if(checkbt.equals(bt[0])) {//추가
				if(Check(1,attribute,index)) {//중복값이 있을때 확인 처음 인자가 1인 이유는 ID값만 확인하기 위해
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("ID 값이 중복입니다.");
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
					//System.out.println(count);
					if(tf[0].getText().equals("")||tf[1].getText().equals("")||tf[2].getText().equals("")||tf[4].getText().equals("")) {
						System.out.println("ID,PW,이름,주소 값이 있어야함");
						MessegeWindow mess = new MessegeWindow();
						mess.memo.setText("ID,PW,이름,주소 값이 있어야합니다.");
					}else {
						sql = sb.append("insert into customer(").toString();
						for(int i=0; i < count; i++) {
							if(i==count-1) {
								sql = sb.append(addAttri[i]).toString();
							}else 
								sql = sb.append(addAttri[i]+",").toString();
						}
						if(!gender.getSelectedItem().toString().equals("All")) 
							sql = sb.append(",gender) values(").toString();
						else
							sql = sb.append(") values(").toString();

						for(int i=0; i < count; i++) {
							if(i==count-1) {
								sql = sb.append("?").toString();
							}else 
								sql = sb.append("?,").toString();
						}
						if(!gender.getSelectedItem().toString().equals("All"))//성별은 콤보 박스여서 따로 확인
							sql = sb.append(",?);").toString();//gender
						else
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
						addAttri[count] = attribute[i];
						index[count]= i;
						count++;
					}
				}

				if(count==0 && gender.getSelectedItem().toString().equals("All")) {//텍스트 테이블갯수에 맞춰서 수정

					sql = sb.append("select * FROM customer  ").toString();
				}else {
					sql = sb.append("select * FROM customer where ").toString();
				}

				for(int i=0; i < count; i++) {
					if(i==count-1) {
						sql = sb.append(addAttri[i]+"=? ").toString();
					}else 
						sql = sb.append(addAttri[i]+"=? and ").toString();
				}
				if(!gender.getSelectedItem().toString().equals("All"))
					if(count==0)
						sql = sb.append("  gender=? ").toString();
					else
						sql = sb.append(" and gender=? ").toString();

				SelectShow(count, index, sql);
				setEmpty();

			}else if(checkbt.equals(bt[2])) {//수정
				System.out.println(bt[2].getText());
				System.out.println();
				for(int i=1; i < attribute.length; i++) {//값이 있는 텍스트 필드를 확인하고 저장
					if(!tf[i].getText().equals("")) {
						addAttri[count] = attribute[i];
						index[count]= i;
						count++;
					}
				}
				//System.out.println(count);
				if(tf[0].getText().equals("")) {
					System.out.println("ID 값이 있어야함");
					MessegeWindow mess = new MessegeWindow();
					mess.memo.setText("ID 값이 있어야 됩니다.");
				}else {
					sql = sb.append("update customer set ").toString();
					for(int i=0; i < count; i++) {// i=1인 이유는 ID는 마지막에 넣을거라서
						if(i==count-1) {
							sql = sb.append(addAttri[i]+"=? ").toString();
						}else 
							sql = sb.append(addAttri[i]+"=? , ").toString();
					}
					if(!gender.getSelectedItem().toString().equals("All")) //성별은 콤보 박스여서 따로 확인
						sql = sb.append(",gender=? where ID='"+tf[0].getText()+"'").toString();
					else
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
					System.out.println("ID값이 부족함");
				}else {
					sql = sb.append("delete FROM customer where ").toString();

					for(int i=0; i < count; i++) {
						if(i==count-1) {
							sql = sb.append(addAttri[i]+"=? ").toString();
						}else 
							sql = sb.append(addAttri[i]+"=? and ").toString();
					}
					if(!gender.getSelectedItem().toString().equals("All")) 
						sql = sb.append(" and gender=? ").toString();
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
	boolean Check(int count,String[] addAttri, int[] index) {
		String sql;
		StringBuilder sb= new StringBuilder();

		boolean result=false;
		if(count == 0) {
			return result=false;
		}
		sql = sb.append("select * from customer where ").toString();

		for(int i=0; i < count; i++) {
			if(i==count-1) {
				sql = sb.append(addAttri[i] + "=?").toString();
			}else 
				sql = sb.append(addAttri[i] + "=? and ").toString();
		}
		if(!gender.getSelectedItem().toString().equals("All"))//성별은 콤보 박스여서 따로 확인
			sql = sb.append(" and gender = ?;").toString();//gender
		else
			sql = sb.append(";").toString();//gender

		try {
			connect.pstmt = connect.con.prepareStatement(sql);
			for(int i=0; i < count; i++) {
				connect.pstmt.setString(i+1, tf[index[i]].getText());
			}
			if(!gender.getSelectedItem().toString().equals("All")) 
				connect.pstmt.setString(count+1, gender.getSelectedItem().toString());

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
		gender.setEnabled(false);
	}
	void TFAble(){ //  활성화
		for(int i=0;i<bt.length;i++) {
			bt[i].setEnabled(true);
		}for(int i=0; i < tf.length; i++) {
			tf[i].setEnabled(true);
		}
		gender.setEnabled(true);
	}
	void setEmpty() {//텍스트 필드 초기화
		for(int i=0; i < tf.length; i++) {
			tf[i].setText("");
		}
		gender.setSelectedItem("All");
	}
	void Show() {
		String sql;
		sql="SELECT * FROM customer";
		tModel.setNumRows(0);
		try {
			System.out.println(sql+"\n");
			connect.rs=connect.stmt.executeQuery(sql);
			while(connect.rs.next()) {
				Object[] data= {connect.rs.getString("ID")
						,connect.rs.getString("password")
						,connect.rs.getString("name")
						,connect.rs.getString("phoneNumber")
						,connect.rs.getString("address")
						,connect.rs.getString("gender")
						,connect.rs.getString("age")};
				tModel.addRow(data);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("show");
			System.out.println();
		}
	}
	void SelectShow(int count, int[] index, String sql) {
		tModel.setNumRows(0);
		try {
			connect.pstmt = connect.con.prepareStatement(sql);
			for(int i=0; i < count; i++) {
				connect.pstmt.setString(i+1, tf[index[i]].getText());
			}
			if(!gender.getSelectedItem().toString().equals("All")) 
				connect.pstmt.setString(count+1, gender.getSelectedItem().toString());

			connect.rs = connect.pstmt.executeQuery();
			System.out.println(sql);
			while(connect.rs.next()) {
				Object[] data= {connect.rs.getString("ID")
						,connect.rs.getString("password")
						,connect.rs.getString("name")
						,connect.rs.getString("phoneNumber")
						,connect.rs.getString("address")
						,connect.rs.getString("gender")
						,connect.rs.getString("age")};
				tModel.addRow(data);
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("show");
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
			if(!gender.getSelectedItem().toString().equals("All")) 
				connect.pstmt.setString(count+1, gender.getSelectedItem().toString());

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
