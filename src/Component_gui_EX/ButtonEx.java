package Component_gui_EX;

import java.awt.*;

import javax.swing.*;

public class ButtonEx extends JFrame{
	JLabel jlb;
	ImageIcon icon = new ImageIcon("images/����â��.png");
	public ButtonEx() {
		setTitle("��ư ������");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setSize(500, 300);
		
		Container c = getContentPane();
		FlowLayout flow = new FlowLayout();
		c.setLayout(flow);
		
		jlb= new JLabel();
		jlb.setIcon(icon);
		ImageIcon normall = new ImageIcon("������.png");
		ImageIcon rollover = new ImageIcon("�븶����1.png");
		ImageIcon presse = new ImageIcon("������.png");
		
		JButton bt2 = new JButton("OK",normall );
		bt2.setPressedIcon(presse);
		bt2.setRolloverIcon(rollover);
		bt2.setVerticalAlignment(SwingConstants.BOTTOM);
		
		
		c.add(jlb);
		c.add(bt2);
		pack();
		setVisible(true);
		
		
	}
	public static void main(String[] args) {
		new ButtonEx();

	}

}
