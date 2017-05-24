package version2;

import java.awt.Point;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main extends JFrame {
	/*creat some JLabel*/
	JLabel Loging;
	//set a form for login
	JLabel SignUp;
	//set a form for signup
	JLabel Course;
	//set a form for course

	public static void main(String[] args) {
		new Main();
		//you can use the main method by this way
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
		/*add listener*/
		listenerForMain myaction = new listenerForMain(this);
		Loging.addMouseListener(myaction);
		SignUp.addMouseListener(myaction);
		this.add(Loging);
		this.add(SignUp);
		this.add(Course);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*Automatically hide and release the form 
		after calling any registered WindowListener object*/
		this.setVisible(true);
		//Sets the object of the this reference to be visible
	}

}
