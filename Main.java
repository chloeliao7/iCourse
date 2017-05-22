package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main extends JFrame {
	JButton Loging;
	JButton SignUp;
	JLabel Course;

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		this.setSize(800, 500);
		this.setLocation(new Point(400, 200));
		getContentPane().setLayout(null);
		this.setTitle("iCourse");
		Loging = new JButton("Sign In");
		Loging.setBounds(650, 357, 100, 30);
		SignUp = new JButton("Sign Up");
		SignUp.setBounds(650, 399, 100, 30);
		Course = new JLabel("iCourse");
		Course.setForeground(new Color(105, 105, 105));
		Course.setFont(new Font("Futura", Font.BOLD, 80));
		Course.setBounds(260, 88, 295, 120);
		listenerForMain myaction = new listenerForMain(this);
		Loging.addMouseListener(myaction);
		SignUp.addMouseListener(myaction);
		getContentPane().add(Loging);
		getContentPane().add(SignUp);
		getContentPane().add(Course);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}

