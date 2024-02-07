package gui_programeEX;
import javax.swing.*;
import java.awt.*;

public class MyFrame extends JFrame {
	
	private static final long serialVersionUID = 1L;
	// 객체가 직렬화 되서 이동할떄 사용하는 번호
	public MyFrame() {
		setTitle("첫번째 프레임");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 300);
//		setVisible(true); // JFrame에 있는 것들
		
		Container contentPane = getContentPane();
		contentPane.setBackground(Color.ORANGE);
		
		//GridLayout grid = new GridLayout(0, 1, 0, 5);
		//contentPane.setLayout(grid);
//		FlowLayout flow = new FlowLayout(FlowLayout.LEFT,10,10);
//		contentPane.setLayout(flow);
		BorderLayout border =new BorderLayout(10, 10);
		contentPane.setLayout(border);
		
		JPanel pn1 = new JPanel();
		pn1.setBackground(new Color(100, 125, 125));
		
		JButton bt1 = new JButton("색 버튼");
		bt1.setBackground(Color.blue);
		bt1.setForeground(Color.white);
		
		JButton bt2 = new JButton("OK");
		JButton bt3 = new JButton("Cancle");
		JButton bt4 = new JButton("lgnore");
		
		JLabel rabel1 =new JLabel("18110078 우병섭");
		Font font = new Font("굴림체",Font.BOLD ,30);
		rabel1.setFont(font);
		rabel1.setBackground(Color.GRAY);
		rabel1.setForeground(Color.blue);
		rabel1.setOpaque(true);
		contentPane.add(rabel1);
		
		bt2.setSize(80, 20);
		bt2.setLocation(20, 50);
		bt3.setBounds(120, 40, 80, 40);
		pn1.add(bt1);
		contentPane.add(pn1, BorderLayout.NORTH );
		contentPane.add(bt2, BorderLayout.EAST);
		//contentPane.add(bt2, BorderLayout.CENTER);
		contentPane.add(bt3, BorderLayout.SOUTH);
		contentPane.add(bt4, BorderLayout.WEST);

//		contentPane.add(pn1);
//		contentPane.add(bt2);
//		contentPane.add(bt3);
//		contentPane.add(bt4);
//		
		setVisible(true); // JFrame에 있는 것들

		System.out.println("firt");
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		MyFrame Frame = new MyFrame();
	}

}
