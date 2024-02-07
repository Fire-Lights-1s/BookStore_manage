package Component_gui_EX;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TextAreaEx extends JFrame {
	private JTextField tf = new JTextField(14);
	private JTextArea ta = new JTextArea(7,20);
	JButton bt2 = new JButton("�Է�");
	
	public TextAreaEx() {
		setTitle("�ؽ�Ʈ �ʵ�");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300,300);
		
		Container c = getContentPane();
		FlowLayout flow = new FlowLayout();
		c.setLayout(flow);
		
		ActionHandler handler = new ActionHandler();// Ŭ������ �����
		
		c.add(new JLabel("�Է� �� <Enter> Ű�� �Է��ϼ���."));
		c.add(tf);
		c.add(bt2);
		c.add(new JScrollPane(ta));
		
		bt2.addActionListener(new ActionListener(){ //Ŭ������ ������ �ʰ� ���Ҷ� �����
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
