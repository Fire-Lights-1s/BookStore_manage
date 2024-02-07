package gui_programeEX;
import javax.swing.*;
import java.awt.*;


public class BoxlayoutEX extends JFrame {
	public BoxlayoutEX(int a) {
		if(a==1) //ó������ â�� 2�� �ٿ������
			BoxlayoutEX1();
		if(a==2)
			BoxlayoutEX2();
	}
	public void BoxlayoutEX1() {
		setTitle("�ڽ� ���̾ƿ� 1");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 300);
		setVisible(true); 
		
		JPanel pn1 = new JPanel();// �ϳ��� â�� �������� ������ �ٲ�
		JPanel pn2 = new JPanel();
		Container contentPane = getContentPane();
		contentPane.setLayout(new BorderLayout());
		
		pn1.setLayout(new BoxLayout(pn1,BoxLayout.Y_AXIS));
		pn2.setLayout(new BoxLayout(pn2,BoxLayout.X_AXIS));
		
		Button[] bt = new Button[7] ;
		for(int i=0; i<bt.length; i++) {
			bt[i] = new Button("BUTTON "+(i+1));
			pn1.add(bt[i]);
			// �ݺ��� �ϳ��� �Ϸ������� �ϳ��� ���� ������ pn1�� ��ư�� pn2�� �Ű����� ������
		}
		for(int i=0; i<bt.length; i++) {
			bt[i] = new Button("BUTTON "+(i+1));
			pn2.add(bt[i]);
		
		}
		contentPane.add(pn1, BorderLayout.WEST);
		contentPane.add(pn2, BorderLayout.CENTER);
	}
	public void BoxlayoutEX2() {
		setTitle("�ڽ� ���̾ƿ� 2");
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
