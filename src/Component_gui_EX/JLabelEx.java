package Component_gui_EX;
import java.awt.*;
import javax.swing.*;

public class JLabelEx extends JFrame{
	JLabel jlb ;
	public JLabelEx() {
		setTitle("���̺� ������");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		Container contentPane = getContentPane();
		FlowLayout flow = new FlowLayout(FlowLayout.LEFT,10,10);
		contentPane.setLayout(flow);
		
		JLabel textLabel = new JLabel("�̿���~");
		
		ImageIcon im = new ImageIcon("�븶����1.png");
		JLabel image = new JLabel(im);
		
		ImageIcon imText = new ImageIcon("������.png");
		JLabel imageText = new JLabel("����?", imText, SwingConstants.CENTER );

		contentPane.add(textLabel);
		contentPane.add(image);
		contentPane.add(imageText);

		//setSize(500, 300);
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		JLabelEx label =new JLabelEx();

	}

}
