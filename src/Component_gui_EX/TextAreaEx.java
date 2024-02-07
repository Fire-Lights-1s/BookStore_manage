package Component_gui_EX;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextAreaEx extends JFrame {
	private JTextField tf = new JTextField(14);
	private JTextArea ta = new JTextArea(7,20);
	JButton bt2 = new JButton("입력");
	
	public TextAreaEx() {
		setTitle("텍스트 필드");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300,300);
		
		Container c = getContentPane();
		FlowLayout flow = new FlowLayout();
		c.setLayout(flow);
		
		ActionHandler handler = new ActionHandler();// 클래스로 만든것
		
		c.add(new JLabel("입력 후 <Enter> 키를 입력하세요."));
		c.add(tf);
		c.add(bt2);
		c.add(new JScrollPane(ta));
		
		bt2.addActionListener(new ActionListener(){ //클래스로 만들지 않고 더할때 만든것
			public void actionPerformed(ActionEvent e) {
				//JTextField t = (JTextField)e.getSource();
				ta.append(tf.getText()+"\n");
				tf.setText("");
			}
		});
		tf.addActionListener(handler);
		ta.setEditable(false);
		
		setVisible(true);
	}
	class ActionHandler implements ActionListener{

	
		public void actionPerformed(ActionEvent e) {
			ta.append(tf.getText()+"\n");
			tf.setText("");
			
		}
		
	}
	public static void main(String[] args) {
		new TextAreaEx();
		
	}

}
