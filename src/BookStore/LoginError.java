package BookStore;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LoginError extends JFrame{
	Container c = getContentPane();
	JLabel lbl = new JLabel("�������� �ʴ� ID�ų� �ùٸ��� ���� ��й�ȣ�Դϴ�.");
	JButton btn = new JButton("Ȯ��");
	
	public LoginError() {
		c.setLayout(new FlowLayout());
		setTitle("�α��� ����");
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
