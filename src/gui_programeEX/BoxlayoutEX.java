package gui_programeEX;
import javax.swing.*;
import java.awt.*;


public class BoxlayoutEX extends JFrame {
	public BoxlayoutEX(int a) {
		if(a==1) //처음에는 창을 2개 뛰우려했음
			BoxlayoutEX1();
		if(a==2)
			BoxlayoutEX2();
	}
	public void BoxlayoutEX1() {
		setTitle("박스 레이아웃 1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 300);
		setVisible(true); 
		
		JPanel pn1 = new JPanel();// 하나의 창에 넣으려고 생각이 바뀜
		JPanel pn2 = new JPanel();
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		pn1.setLayout(new BoxLayout(pn1,BoxLayout.Y_AXIS));
		pn2.setLayout(new BoxLayout(pn2,BoxLayout.X_AXIS));
		
		Button[] bt = new Button[7] ;
		for(int i=0; i<bt.length; i++) {
			bt[i] = new Button("BUTTON "+(i+1));
			pn1.add(bt[i]);
			// 반복문 하나로 하려했으나 하나에 같이 넣으니 pn1의 버튼이 pn2로 옮겨져서 따로함
		}
		for(int i=0; i<bt.length; i++) {
			bt[i] = new Button("BUTTON "+(i+1));
			pn2.add(bt[i]);
		
		}
		contentPane.add(pn1, BorderLayout.WEST);
		contentPane.add(pn2, BorderLayout.CENTER);
	}
	public void BoxlayoutEX2() {
		setTitle("박스 레이아웃 2");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 300);
		setVisible(true); 
		
		JPanel pn1 = new JPanel();
		pn1.setLayout(new BoxLayout(pn1,BoxLayout.X_AXIS));
		
		Button[] bt = new Button[7] ;
		for(int i=0; i<bt.length; i++) {
			bt[i] = new Button("BUTTON "+(i+1));
			pn1.add(bt[i]);
		}
		add(pn1);
	}
	public static void main(String[] args) {
		BoxlayoutEX box1 = new BoxlayoutEX(1);
		BoxlayoutEX box2 = new BoxlayoutEX(2);
	}

}
