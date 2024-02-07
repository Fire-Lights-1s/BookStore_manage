package gui_programeEX;
import javax.swing.*;
import java.awt.*;

public class GridLayoutEx extends JFrame{
	public GridLayoutEx() {
		setTitle("grid Sample");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 200);
		setVisible(true);
		
		GridLayout grid = new GridLayout(4, 2, 0 , 5);
		Container c = getContentPane();
		c.setLayout(grid);
		c.add(new JLabel(" 이름"));
		c.add(new JTextField(""));
		c.add(new JLabel(" 학번"));
		c.add(new JTextField(""));
		c.add(new JLabel(" 학과"));
		c.add(new JTextField(""));
		c.add(new JLabel(" 과목"));
		c.add(new JTextField(""));
		
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		GridLayoutEx exam =new GridLayoutEx();
	}

}
