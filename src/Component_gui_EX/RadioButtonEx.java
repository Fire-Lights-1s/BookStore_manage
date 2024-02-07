package Component_gui_EX;

import javax.swing.*;
import javax.swing.border.*;

import java.awt.*;
import java.awt.event.*;


public class RadioButtonEx extends JFrame {
	private JRadioButton [] magic= new JRadioButton [2];
	private String [] names = {"마법사", "대마법사"};
	private ImageIcon [] normall = {new ImageIcon("마법사.png"), new ImageIcon("대마법사1.png")};
	private JLabel sumMP = new JLabel();
	
	public RadioButtonEx() {
		setTitle("RadioButtonEx 프레임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		BorderLayout border = new BorderLayout(20,20);
		FlowLayout flow = new FlowLayout();
		c.setLayout(border);
		
		JPanel bt = new JPanel();
		JPanel bt2 = new JPanel();
		bt.setBackground(Color.gray);
		bt2.setBackground(Color.black);
		bt2.setLayout(flow);
		bt.setBorder(new TitledBorder(new LineBorder(Color.green),"직업"));
		
		ButtonGroup g = new ButtonGroup();// g에 더한 버튼들은 그룹이되서 그룹에 있는 버튼 중 하나만 선택됨
		for(int i=0; i< magic.length; i++ ) {
			if(i==0)
				magic[i]=new JRadioButton(names[i],true);
			else
				magic[i]=new JRadioButton(names[i]);
			g.add(magic[i]);
			bt.add(magic[i]);
			magic[i].addItemListener(new MyItemListener());
		}
		sumMP.setIcon(normall[0]);
		
		bt2.add(bt);
		c.add(bt2,BorderLayout.NORTH);
		c.add(sumMP,BorderLayout.CENTER);
		
		pack();
		setVisible(true);
	}
	
	class MyItemListener implements ItemListener {

		
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.DESELECTED)
				return;
			if(magic[0].isSelected())
				sumMP.setIcon(normall[0]);
			else
				sumMP.setIcon(normall[1]);
				
			
		}
		
	}
	public static void main(String[] args) {
		new RadioButtonEx();

	}

}
