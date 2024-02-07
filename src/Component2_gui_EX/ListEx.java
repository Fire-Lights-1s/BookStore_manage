package Component2_gui_EX;
import javax.swing.*;
import javax.swing.event.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ListEx extends JFrame{
	private JTextField tf = new JTextField(20);
	private String[] name= {"����","����","�ν���������","����","�ĸ�","�λ�","�����"} ;
	private JList<String> nameList = new JList<String>();
	private JLabel lbl = new JLabel("����");
	JScrollPane scrol=new JScrollPane(nameList);
	JPanel cityIN = new JPanel();
	JPanel city = new JPanel();
	
	ListHandler listener =new ListHandler();
	
	public ListEx() {
		setTitle("����Ʈ ex");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container c = getContentPane();
		FlowLayout flow = new FlowLayout();
		//c.setLayout(flow);
		Arrays.sort(name);
		nameList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		nameList.setListData(name);
		nameList.setVisibleRowCount(5);
		nameList.addListSelectionListener(listener);
		
		city.add(scrol);
		c.add(city);
		cityIN.add(lbl);
		cityIN.add(tf);
		c.add(cityIN, BorderLayout.NORTH);

		setSize(300, 300);
		setVisible(true);
	}
	class ListHandler implements ListSelectionListener{
		public void valueChanged(ListSelectionEvent e) {
			String str="";
			int[] idx = nameList.getSelectedIndices();//���õ� �͵��� �ε����� ������
			for(int i=0; i <idx.length; i++ ) {
				str += name[idx[i]]+":";
			}
			//tf.setText(nameList.getSelectedValue());
			tf.setText(str);
			
		}
		
	}
	public static void main(String[] args) {
		ListEx list = new ListEx();

	}

}
