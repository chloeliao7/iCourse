package version2;

import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Sign extends JFrame {

	JLabel User;
	//set a form for user name
	JLabel PIN;
	//set a form for password
	JLabel Ensure;
	//set a button to ensure
	JLabel Email;
	//set a button to email
	JButton Enter;
	//set a button to enter the app
	JButton Cancel;
	//set a button to cancel the edit
	JTextField name;
	//Allow edit single line user name text 
	JTextField pin;
	//Allow edit single line password text
	JTextField epin;
	//Allow edit single line ensure pin text
	JTextField email;
	//Allow edit single line email text

	public static void main(String[] args) {
		new Sign();
	}

	public Sign() {
		this.setSize(400, 500);
		this.setLayout(null);
		//Set layout manager for this container
		this.setLocation(new Point(600, 200));

		/*creat some JLabel and set the size ,show the coordinate of the rectangle of each*/
		User = new JLabel("User :");
		//display text user
		User.setBounds(65, 100, 100, 30);
		//set the bound of the user form
		PIN = new JLabel("PIN :");
		//display text password
		PIN.setBounds(65, 150, 100, 30);
		//set the bound of the password form
		Ensure = new JLabel("Ensure PIN :");
		Ensure.setBounds(65, 200, 100, 30);
		Email = new JLabel("Email :");
		Email.setBounds(65, 250, 100, 30);
		Enter = new JButton("Enter");
		Enter.setBounds(225, 350, 80, 30);
		Cancel = new JButton("Cancel");
		Cancel.setBounds(95, 350, 80, 30);

		/*Create some string and set the size ,show the coordinate of the rectangle of each*/
		name = new JTextField();
		name.setBounds(150, 100, 120, 30);
		//Allow edit single line user name text
		pin = new JTextField();
		pin.setBounds(150, 150, 120, 30);
		 //Allow edit single line password text
		
		epin = new JTextField();
		epin.setBounds(150, 200, 120, 30);
		email = new JTextField();
		email.setBounds(140, 250, 150, 30);

		this.add(User);
		this.add(PIN);
		this.add(Ensure);
		this.add(Email);
		this.add(Enter);
		this.add(Cancel);
		this.add(name);
		this.add(pin);
		this.add(epin);
		this.add(email);

		listenerForSign myaction = new listenerForSign(this);
		Enter.addActionListener(myaction);
		Cancel.addActionListener(myaction);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*Automatically hide and release the form 
		after calling any registered WindowListener object*/
		this.setVisible(true);
		//Sets the object of the this reference to be visible
	}
}
