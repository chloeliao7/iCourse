package version2;

import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Loging extends JFrame {

	JLabel User;
	//set a form for user name
	JLabel PIN;
	//set a form for password
	JButton Enter;
	//set a button to enter the app
	JButton Cancel;
	//set a button to cancel the edit
	JTextField name;
	//Allow edit single line user name text 
	JTextField pin;
	//Allow edit single line password text

	public static void main(String[] args) {
		new Loging();
		//you can use this method in main operation
	}

	public Loging() {
		this.setSize(400, 500);
		//set size for login window
		this.setLayout(null);
		//Default layout style
		this.setLocation(new Point(600, 200));
		//set location for sign

		User = new JLabel("User:");
		//display text user
		User.setBounds(65, 100, 100, 30);
		//set the bound of the user form
		PIN = new JLabel("PIN:");
		//display text password
		PIN.setBounds(65, 150, 100, 30);
		//set the bound of the password form
		Enter = new JButton("Enter");
		//set a button named enter
		Enter.setBounds(225, 300, 80, 30);
		//set the bound of the enter form
		Cancel = new JButton("Cancel");
		//set a button named cancel
		Cancel.setBounds(95, 300, 80, 30);
		//set the bound of the cancel form

		name = new JTextField();
		name.setBounds(140, 100, 120, 30);
		//Allow edit single line user name text

		pin = new JTextField();
		pin.setBounds(140, 150, 120, 30);
		//Allow edit single line password text

		this.add(User);
		this.add(PIN);
		this.add(Enter);
		this.add(Cancel);
		this.add(name);
		this.add(pin);

		//add listener
		listenerForLoging myaction = new listenerForLoging(this);
		Enter.addActionListener(myaction);
		Cancel.addActionListener(myaction);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*Automatically hide and release the form 
		after calling any registered WindowListener object*/
		this.setVisible(true);
		//Sets the object of the this reference to be visible
	}

}
