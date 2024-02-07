package Component_gui_EX;

import java.awt.*;

import javax.swing.*;

public class ButtonEx extends JFrame{
	JLabel jlb;
	ImageIcon icon = new ImageIcon("images/보스창문.png");
	public ButtonEx() {
		setTitle("버튼 프레임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//setSize(500, 300);
		
		Container c = getContentPane();
		FlowLayout flow = new FlowLayout();
		c.setLayout(flow);
		
		jlb= new JLabel();
		jlb.setIcon(icon);
		ImageIcon normall = new ImageIcon("마법사.png");
		ImageIcon rollover = new ImageIcon("대마법사1.png");
		ImageIcon presse = new ImageIcon("마법사.png");
		
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
