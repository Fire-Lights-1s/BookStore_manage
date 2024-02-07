package BookStore;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class StockError extends JFrame{
	Container c = getContentPane();
	JLabel lbl = new JLabel("재고가 부족합니다. 서점에 문의해주세요.");
	JButton btn = new JButton("확인");
	
	public StockError() {
		c.setLayout(new FlowLayout());
		setTitle("재고 오류");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.add(lbl);
		btn.addActionListener(new MyActionListener());
		c.add(btn);
		setSize(270,120);
		setVisible(true);
	}

	class MyActionListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == btn)
				dispose();
		}
		
	}
	
	public static void main(String[] args) {
		new StockError();
	}
}
