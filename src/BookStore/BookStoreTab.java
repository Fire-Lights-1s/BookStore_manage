package BookStore;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class BookStoreTab extends JFrame{
	JPanel[] bookPl = new JPanel[5];
	JButton[] bt = new JButton[5];
	JTabbedPane bookTab;
	
	Customer customer = new Customer();
	BookSale bookSale = new BookSale();
	BookStock stock = new BookStock();
	BookPurchase bookPurchase = new BookPurchase();
	Publisher puublisher = new Publisher();
	
	BookStoreTab(){
		setTitle("서점 관리");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	
		JPanel pl = new JPanel();
		
		bookTab = new JTabbedPane();
		setTabbedPane();
		addAction();
		 
		setBounds(300, 300, 1000, 500);
		setResizable(false);
		setVisible(true);
	}
	void setTabbedPane() {
		for(int i=0; i < bookPl.length; i++) {
			bookPl[i] = new JPanel();
			bt[i] = new JButton();
			bookPl[i].add(bt[i]);
			//bookPl[i].add(pl);
		}
		stock.table.addMouseListener(new MAction());
		bookTab.add("고객관리",customer);
		bookTab.add("책 판매",bookSale);
		bookTab.add("책 관리",stock);
		bookTab.add("책 구매",bookPurchase);
		bookTab.add("출판사 관리",puublisher);
		add(bookTab);
		
	}
	void addAction() {
		for(int i=0; i < bookPl.length; i++) {
			bookPl[i] = new JPanel();
			bt[i] = new JButton();
			bookPl[i].add(bt[i]);
			//bookPl[i].add(pl);
		}
	}
	class MAction extends MouseAdapter{
		public void mouseClicked(MouseEvent e) {
			int row = stock.table.getSelectedRow();
			
			String[] attribute = {"ID","password", "name", "phoneNumber", "address", "age"};//택스트 필드 체크용

			String ID = (String)stock.table.getValueAt(row, 0); 
			
			bookPurchase.tf[2].setText(ID);
			bookSale.tf[2].setText(ID);
			
		}
	}
	public static void main(String[] args) {
		new BookStoreTab();
	}

}
