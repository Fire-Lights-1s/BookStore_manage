package Component_gui_EX;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

public class JComponentEx extends JFrame {
	public JComponentEx() {
		setTitle("공토메소드 예제");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(260, 200);
		setVisible(true);
		
		Container c = getContentPane();
		FlowLayout flow = new FlowLayout(FlowLayout.CENTER,10,10);
		c.setLayout(flow);
		
		JButton bt1 = new JButton("Magenta/Yellow Button");
		JButton bt2 = new JButton("Disabled Button");
		JButton bt3 = new JButton("getX(), getY()");
		
		bt1.setBackground(Color.yellow);
		bt1.setForeground(Color.magenta);
		bt1.setFont(new Font("Arial", Font.ITALIC, 20));
		bt2.setEnabled(false);
		bt3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton)e.getSource();
				JComponentEx frame = (JComponentEx)b.getTopLevelAncestor();
				frame.setTitle(b.getX()+", "+b.getY());
				
			}
		});
		c.add(bt1);
		c.add(bt2);
		c.add(bt3);
	}
	public static void main(String[] args) {
		JComponentEx compo = new JComponentEx();
	}

}
