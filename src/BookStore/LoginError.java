package BookStore;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginError extends JFrame{
	Container c = getContentPane();
	JLabel lbl = new JLabel("존재하지 않는 ID거나 올바르지 않은 비밀번호입니다.");
	JButton btn = new JButton("확인");
	
	public LoginError() {
		c.setLayout(new FlowLayout());
		setTitle("로그인 오류");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		c.add(lbl);
		btn.addActionListener(new MyActionListener());
		c.add(btn);
		setSize(350,120);
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
		new LoginError();
	}
}
