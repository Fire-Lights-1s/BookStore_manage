package Component_gui_EX;
import javax.swing.*;
import java.awt.*;

public class TextFieldEx extends JFrame {
	JLabel [] textLbl = new JLabel[3];
	JTextField [] textField = new JTextField[3];
	public TextFieldEx() {
		setTitle("�ؽ�Ʈ �ʵ�");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300,300);
		
		String[] str = {"�̸�","�а�","�ּ�"};
		
		Container c = getContentPane();
		FlowLayout flow = new FlowLayout();
		c.setLayout(flow);
		
		for(int i=0; i<textField.length; i++) {
			textLbl[i]=new JLabel(str[i]);
			textField[i]=new JTextField(20);
		}
		textField[1].setEditable(false);
		for(int i=0; i<textField.length; i++) {
			c.add(textLbl[i]);
			c.add(textField[i]);
		}
		JLabel nameText = new JLabel("�̸�");
		JLabel comText = new JLabel("�а�");
		JLabel adressText = new JLabel("�ּ�");
		JTextField nameField = new JTextField(20);
		JTextField com = new JTextField("��ǻ�Ͱ��а� ",20);
		nameField.setText("����");
		nameField.setFont(new Font("���ü", Font.ITALIC, 20));
		c.add(nameText);
		c.add(nameField);
		c.add(comText);
		c.add(com);
		c.add(adressText);
		c.add(new JTextField("�����...",20));
		
		setVisible(true);
	}
	public static void main(String[] args) {
		new TextFieldEx();
	}

}
