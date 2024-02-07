package Component2_gui_EX;
import javax.swing.*;
import javax.swing.event.*;

import Component2_gui_EX.JComboBoxAdvEx.JComboBoxAdvListener;

import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class JSliderEx extends JFrame {
	JPanel p1, p2;

	JComboBox<String> fonts ;
	JComboBox<Integer> size ;
	JSlider size2 ;
	JTextField tf;
	JLabel label = new JLabel("자바 코딩의 고수가 되자. I want to be a good java Programmer");
	JCheckBox isItalic, isBold;
	String[] fontArray ;
	Integer[] in = {6,8,10,11,12,14,18,20,24,30,36,40,48,60,72,90,110};

	public JSliderEx() {
		setTitle("콤보박스 만들기");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		BorderLayout border = new BorderLayout();
		c.setLayout(border);
		
		GraphicsEnvironment g = GraphicsEnvironment.getLocalGraphicsEnvironment();//윈도우 안에 font를 다가져옴
		fonts =new JComboBox<String>(g.getAvailableFontFamilyNames());
		fonts.setSelectedItem("바탕체");
		size = new JComboBox<Integer>(in);
		size2 = new JSlider(JSlider.HORIZONTAL, 10, 90, 15);
		tf = new JTextField(5);
		tf.setText(size2.getValue()+"");
		isItalic =new JCheckBox("Italic");
		isBold = new JCheckBox("Bold");
		p1=new JPanel();
		p2=new JPanel();
		
		size2.setMinorTickSpacing(1);
		size2.setMajorTickSpacing(10);
		size2.setPaintLabels(true);
		size2.setPaintTicks(true);
		size2.setPaintTrack(true);
		
		size.setEditable(true);
		p1.add(fonts);
		//p1.add(size);
		p1.add(size2);
		p1.add(tf);		
		p1.add(isItalic);
		p1.add(isBold);
		p2.add(label);
		c.add(p1, BorderLayout.NORTH);
		c.add(p2, BorderLayout.CENTER);
		
		JComboBoxAdvListener listener = new JComboBoxAdvListener();
		fonts.addActionListener(listener);
		//size.addActionListener(listener);
		size2.addChangeListener(listener);
		isItalic.addActionListener(listener);
		isBold.addActionListener(listener);
		
		setSize(800, 250);
		setVisible(true);
	}
	public static void main(String[] args) {
		new JSliderEx();

	}
	class JComboBoxAdvListener implements ActionListener,ChangeListener {
		int stat, fontSize;
		public void actionPerformed(ActionEvent e) {
			if(isItalic.isSelected() && isBold.isSelected())
				stat=Font.BOLD|Font.ITALIC;
			else if(isBold.isSelected())
				stat=Font.BOLD;
			else if(isItalic.isSelected())
				stat=Font.ITALIC;
			else
				stat=Font.PLAIN;
			
			if(size.getSelectedIndex()==-1) 
				fontSize= Integer.parseInt(size.getSelectedItem().toString()) ;
			else
				fontSize= in[size.getSelectedIndex()].intValue();
				
			Font f= new Font(fonts.getSelectedItem().toString(),stat ,size2.getValue());
			label.setFont(f);
			
		}

	
		public void stateChanged(ChangeEvent e) {
			Font f= new Font(fonts.getSelectedItem().toString(),stat ,size2.getValue());
			tf.setText(size2.getValue()+"");
			label.setFont(f);
			
		}
		
	}
}
