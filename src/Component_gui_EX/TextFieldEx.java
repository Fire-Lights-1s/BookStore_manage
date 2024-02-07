package Component_gui_EX;
import javax.swing.*;
import java.awt.*;

public class TextFieldEx extends JFrame {
	JLabel [] textLbl = new JLabel[3];
	JTextField [] textField = new JTextField[3];
	public TextFieldEx() {
		setTitle("텍스트 필드");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(300,300);
		
		String[] str = {"이름","학과","주소"};
		
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
		JLabel nameText = new JLabel("이름");
		JLabel comText = new JLabel("학과");
		JLabel adressText = new JLabel("주소");
		JTextField nameField = new JTextField(20);
		JTextField com = new JTextField("컴퓨터공학과 ",20);
		nameField.setText("병섭");
		nameField.setFont(new Font("고딕체", Font.ITALIC, 20));
		c.add(nameText);
		c.add(nameField);
		c.add(comText);
		c.add(com);
		c.add(adressText);
		c.add(new JTextField("서울시...",20));
		
		setVisible(true);
	}
	public static void main(String[] args) {
		new TextFieldEx();
	}

}
