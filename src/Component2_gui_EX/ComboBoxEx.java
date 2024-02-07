package Component2_gui_EX;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class ComboBoxEx extends JFrame{
	String[] cities = {"����","����","�ν���������","����","�ĸ�","�λ�","�����","����"} ;
	JComboBox<String> combo ;
	JTextField tf = new JTextField(20);
	private JLabel lbl = new JLabel("����");
	
	JPanel pnl = new JPanel();
	JPanel disp = new JPanel();
	
	
	public ComboBoxEx() {
		setTitle("�޺��ڽ� �����");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		BorderLayout border = new BorderLayout();
		c.setLayout(border);
		
		Arrays.sort(cities);
		combo =new JComboBox<String>(cities);
		combo.setMaximumRowCount(5);
		combo.addActionListener(new comboAction());
		combo.setEditable(true);
		combo.setSelectedItem("�λ�");
		
		pnl.add(combo);
		c.add(pnl, BorderLayout.CENTER);
		disp.add(lbl);
		disp.add(tf);
		c.add(disp, BorderLayout.NORTH);
		
		setSize(300, 300);
		setVisible(true);
	}
	class comboAction implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			
			tf.setText(combo.getSelectedItem().toString()); 
			
			
		}
		
	}
	public static void main(String[] args) {
		new ComboBoxEx();
	}

}
