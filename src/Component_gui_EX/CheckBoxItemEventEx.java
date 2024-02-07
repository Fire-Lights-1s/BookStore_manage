package Component_gui_EX;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class CheckBoxItemEventEx extends  JFrame /* implements ItemListener*/{
	private JCheckBox [] magic= new JCheckBox [2];
	private String [] names = {"������", "�븶����"};
	private ImageIcon [] normall = {new ImageIcon("������.png"), new ImageIcon("�븶����1.png")};
	private JLabel sumMP;
	
	public CheckBoxItemEventEx() {
		setTitle("check box_EX ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		FlowLayout flow = new FlowLayout();
		c.setLayout(flow);
		
		c.add(new JLabel("������ 100MP, �븶���� 10000MP"));
		
		MyItemListener listener = new MyItemListener();
		for(int i=0 ; i < magic.length; i++) {
			magic[i] = new JCheckBox(names[i],normall[i]); 
			magic[i].setBorderPainted(true);
			c.add(magic[i]);
			magic[i].addItemListener(listener);
			//�� Ŭ������ implements ItemListener�� �߰��� ������ �ۼ��ص� ��
			//magic[i].addItemListener(this);
		}
		sumMP = new JLabel("���� MP�� 0�Դϴ�.");
		c.add(sumMP);
		
		//setSize(500, 300);
		pack();
		setVisible(true);
	}
	
	class MyItemListener implements ItemListener { 
		//CheckBoxItemEventEx�� implements ItemListener�� ����Ҷ� Ŭ������ ���� �ʿ����
		private int sum=0;
		
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange()==ItemEvent.SELECTED) {
				if(e.getItem()==magic[0])
					sum+=100;
				else if(e.getItem()==magic[1])
					sum+=10000;
				
			}
			if(e.getStateChange()==ItemEvent.DESELECTED) {
				if(e.getItem()==magic[0])
					sum-=100;
				else if(e.getItem()==magic[1])
					sum-=10000;
				
			}
			sumMP.setText("���� MP�� "+ sum +"�Դϴ�.");
		}
	}
	public static void main(String[] args) {
		new CheckBoxItemEventEx();
	}

}
