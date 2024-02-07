package Component_gui_EX;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


public class CheckBoxItemEventEx extends  JFrame /* implements ItemListener*/{
	private JCheckBox [] magic= new JCheckBox [2];
	private String [] names = {"마법사", "대마법사"};
	private ImageIcon [] normall = {new ImageIcon("마법사.png"), new ImageIcon("대마법사1.png")};
	private JLabel sumMP;
	
	public CheckBoxItemEventEx() {
		setTitle("check box_EX ");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		FlowLayout flow = new FlowLayout();
		c.setLayout(flow);
		
		c.add(new JLabel("마법사 100MP, 대마법사 10000MP"));
		
		MyItemListener listener = new MyItemListener();
		for(int i=0 ; i < magic.length; i++) {
			magic[i] = new JCheckBox(names[i],normall[i]); 
			magic[i].setBorderPainted(true);
			c.add(magic[i]);
			magic[i].addItemListener(listener);
			//이 클래스에 implements ItemListener를 추가로 붙히고 작성해도 됨
			//magic[i].addItemListener(this);
		}
		sumMP = new JLabel("현재 MP는 0입니다.");
		c.add(sumMP);
		
		//setSize(500, 300);
		pack();
		setVisible(true);
	}
	
	class MyItemListener implements ItemListener { 
		//CheckBoxItemEventEx가 implements ItemListener를 사용할때 클래스를 만들 필요없음
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
			sumMP.setText("현재 MP는 "+ sum +"입니다.");
		}
	}
	public static void main(String[] args) {
		new CheckBoxItemEventEx();
	}

}
