package BookStore;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MessegeWindow extends JFrame{
	JLabel memo = new JLabel();
	JButton bt = new JButton("OK");
	MessegeWindow(){
		SetAppendWin();
	}
	void SetAppendWin() {
		JPanel[] pl = new JPanel[2];

		setTitle("Messege");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Container c = getContentPane();
		FlowLayout flow = new FlowLayout();
		GridLayout grid = new GridLayout(0,1);
		
		pl[0]=new JPanel();
		pl[1]=new JPanel();
		
		c.setLayout(grid);
		
		bt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		c.add(pl[0]);
		c.add(pl[1]);
		pl[0].add(memo);
		pl[1].add(bt);
		
		
		setBounds(600, 600, 250, 120);
		setResizable(false);
		setVisible(true);
	}
	public static void main(String[] args) {
		new MessegeWindow();

	}

}
