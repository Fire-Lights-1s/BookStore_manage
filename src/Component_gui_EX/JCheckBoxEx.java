package Component_gui_EX;
import java.awt.*;

import javax.swing.*;

public class JCheckBoxEx extends JFrame{
	ImageIcon normall = new ImageIcon("������.png");
	ImageIcon rollover = new ImageIcon("�븶����1.png");
	
	public JCheckBoxEx() {
		setTitle("check box_EX ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		FlowLayout flow = new FlowLayout();
		c.setLayout(flow);
		
		JCheckBox magic = new JCheckBox("����",normall );
		magic.setBorderPainted(true); //�µθ� ��
		magic.setSelectedIcon(rollover);
		
		c.add(magic);
		
		
		//setSize(500, 300);
		pack();
		setVisible(true);
	}
	public static void main(String[] args) {
		new JCheckBoxEx();

	}

}
