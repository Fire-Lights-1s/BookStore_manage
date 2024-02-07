package Component2_gui_EX;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class ComboBoxEx extends JFrame{
	String[] cities = {"서울","런던","로스앤젤레스","뉴욕","파리","부산","벤쿠버","가나"} ;
	JComboBox<String> combo ;
	JTextField tf = new JTextField(20);
	private JLabel lbl = new JLabel("도시");
	
	JPanel pnl = new JPanel();
	JPanel disp = new JPanel();
	
	
	public ComboBoxEx() {
		setTitle("콤보박스 만들기");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		Container c = getContentPane();
		BorderLayout border = new BorderLayout();
		c.setLayout(border);
		
		Arrays.sort(cities);
		combo =new JComboBox<String>(cities);
		combo.setMaximumRowCount(5);
		combo.addActionListener(new comboAction());
		combo.setEditable(true);
		combo.setSelectedItem("부산");
		
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
