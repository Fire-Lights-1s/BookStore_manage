package BookStore;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class DeleteV extends JFrame{
	JButton[] Dbutton= new JButton[2];
	String[] DString= {"Yes","No"};
	JLabel memo = new JLabel();
	DeleteV(){
		setTitle("Delete");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		Container c = getContentPane();
		FlowLayout flow = new FlowLayout();
		c.setLayout(flow);

		Font font = new Font("SanSerif", Font.BOLD, 18);
		memo.setText("정말 삭제하시겠습니까?");
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
		setBounds(600, 600, 250, 120);
		setResizable(false);
		setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
