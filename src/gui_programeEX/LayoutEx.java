package gui_programeEX;

import javax.swing.*;
import java.awt.*;

public class LayoutEx extends JFrame {
	public LayoutEx(int a) {
		if(a==1)
			GridLayout﻿EX();
		if(a==2)
			BorderLayoutEX();
		if(a==3)
			FlowLayout﻿EX();
		
	}
	public void GridLayout﻿EX() {
		setTitle("GridLayout﻿ Sample");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 200);
		setVisible(true);
		GridLayout grid = new GridLayout(0, 3, 10, 15);
		Container c = getContentPane();
		c.setLayout(grid);
		c.add(new JButton("button 1"));
		c.add(new JButton("button 2"));
		c.add(new JButton("button 3"));
		c.add(new JButton("button 4"));
		c.add(new JButton("button 5"));

	}
	public void BorderLayoutEX() {
		setTitle("BorderLayout Sample");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 200);
		setVisible(true);
		Container c = getContentPane();
		BorderLayout border = new BorderLayout(20,20);
		c.setLayout(border);
		c.add(new JButton("button 1"), BorderLayout.WEST);
		c.add(new JButton("button 2"), BorderLayout.EAST);
		c.add(new JButton("button 3"), BorderLayout.SOUTH);
		c.add(new JButton("button 4"), BorderLayout.NORTH);
		c.add(new JButton("button 5"), BorderLayout.CENTER);
	}
	public void FlowLayout﻿EX() {
		setTitle("FlowLayout﻿ Sample");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 200);
		setVisible(true);
		
		Container flow = getContentPane();
		flow.setLayout(new FlowLayout(FlowLayout.CENTER,2,2));
		flow.add(new JButton("button 1"));
		flow.add(new JButton("button 2"));
		flow.add(new JButton("button 3"));
		flow.add(new JButton("button 4"));
		flow.add(new JButton("button 5"));
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		LayoutEx grid = new LayoutEx(1);
		LayoutEx border = new LayoutEx(2);
		LayoutEx flow = new LayoutEx(3);
		
	}

}
