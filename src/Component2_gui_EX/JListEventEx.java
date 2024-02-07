package Component2_gui_EX;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class JListEventEx extends JFrame{
	private JTextField tf = new JTextField();
	private Vector<String> name= new Vector<String>();
	private JList<String> nameList = new JList<String>();
	
	public JListEventEx() {
		setTitle("¸®½ºÆ® ex");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		FlowLayout flow = new FlowLayout();
		c.setLayout(flow);
		
		setSize(300, 300);
		setVisible(true);
	}
	public static void main(String[] args) {
		JListEventEx list = new JListEventEx();

	}

}
