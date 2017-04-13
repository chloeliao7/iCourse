package version2;

import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main extends JFrame {
	JLabel Loging;
	JLabel SignUp;
	JLabel Course;

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		this.setSize(800, 500);
		this.setLocation(new Point(400, 200));
		this.setLayout(null);
		this.setTitle("iCourse");
		Loging = new JLabel("Loging");
		Loging.setBounds(650, 330, 100, 30);
		SignUp = new JLabel("Sign Up");
		SignUp.setBounds(650, 380, 100, 30);
		Course = new JLabel("iCourse");
		Course.setFont(new java.awt.Font("Andale Mono", 1, 80));
		Course.setBounds(52, 80, 400, 80);
		listenerForMain myaction = new listenerForMain(this);
		Loging.addMouseListener(myaction);
		SignUp.addMouseListener(myaction);
		this.add(Loging);
		this.add(SignUp);
		this.add(Course);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}
