package Client;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main extends JFrame {
	JButton Loging;
	//set a button to login
	JButton SignUp;
	//set a button to signup
	JLabel Course;
	//set a form for course

	public static void main(String[] args) {
		new Main();
	}

	public Main() {
		this.setSize(800, 500);
		this.setLocation(new Point(400, 200));
		getContentPane().setLayout(null);
		this.setTitle("iCourse");
		//set title to the table
		Loging = new JButton("Sign In");
		Loging.setBounds(650, 357, 100, 30);
		//set the bound for sign in window
		SignUp = new JButton("Sign Up");
		SignUp.setBounds(650, 399, 100, 30);
		//set the bound for sign up window
		Course = new JLabel("iCourse");
		Course.setForeground(new Color(105, 105, 105));
		Course.setFont(new Font("Futura", Font.BOLD, 80));
		Course.setBounds(260, 88, 295, 120);
		//set the bound for futura
		listenerForMain myaction = new listenerForMain(this);
		Loging.addMouseListener(myaction);
		SignUp.addMouseListener(myaction);
		getContentPane().add(Loging);
		getContentPane().add(SignUp);
		getContentPane().add(Course);
		//add listener for the window
		//add some scroll bars

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*Automatically hide and release the form 
		after calling any registered WindowListener object*/
		this.setVisible(true);
		//Sets the object of the this reference to be visible
	}

}

