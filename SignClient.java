package Client;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import setting.account;
import setting.users;

public class Sign extends JFrame implements Runnable {

	JLabel User;
	//set a form for user name
	JLabel PIN;
	//set a form for password
	JLabel Ensure;
	//set a form for ensure
	JLabel Email;
	//set a form for email
	JButton Enter;
	//set a button to enter the app
	JButton Cancel;
	//set a button to cancel the edit
	JTextField name;
	//Allow edit single line user name text 
	JTextField pin;
	//Allow edit single line password text
	JTextField repin;
	//Allow edit single line repin text
	JTextField email;
	//Allow edit single line email text
	private PrintWriter writer;
	//creat a print object
	private BufferedReader reader;
	// Wrapping character streams to put character streams into the cache
	private Socket socket;
	// creat a socket

	private void connect() {

		try {
			socket = new Socket("localhost", 1978);
			// while (true) {
		    // writer = new PrintWriter(socket.getOutputStream(), true);
			//  get the socket output stream to write data
			writer = new PrintWriter(socket.getOutputStream(), true);
			//The output stream is obtained by the Socket object
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
			//Constructing BufferedReader objects from system standard input devices
			new Thread(this).start();
			// }
		} catch (Exception e) {
			e.printStackTrace();
			/*The command line prints the location and reason
			for the error message in the program*/
		}
	}

	public static void main(String[] args) {
		new Sign();
	}

	public Sign() {
		this.setSize(400, 500);
		//set size for sign window
		this.setLayout(null);
		//Default layout style
		this.setLocation(new Point(600, 200));
		//set location for sign

		User = new JLabel("User :");
		//display text user
		User.setBounds(65, 100, 100, 30);
		//set the bound of the user form
		PIN = new JLabel("PIN :");
		//display text password
		PIN.setBounds(65, 150, 100, 30);
		//set the bound of the password form
		Ensure = new JLabel("Ensure PIN :");
		//display text ensure password
		Ensure.setBounds(65, 200, 100, 30);
		//set the bound of the insurepin form
		Email = new JLabel("Email :");
		//display text email
		Email.setBounds(65, 250, 100, 30);
		//set the bound of the email form
		Enter = new JButton("Enter");
		//set a button named enter
		Enter.setBounds(225, 350, 80, 30);
		//set the bound of the enter form
		Cancel = new JButton("Cancel");
		//set a button named cancel
		Cancel.setBounds(95, 350, 80, 30);
		//set the bound of the cancel form

		name = new JTextField();
		name.setBounds(150, 100, 120, 30);
		//Allow edit single line user name text
		pin = new JTextField();
		pin.setBounds(150, 150, 120, 30);
		//Allow edit single line password text
		repin = new JTextField();
		repin.setBounds(150, 200, 120, 30);
		 //Allow edit single line repin text
		email = new JTextField();
		email.setBounds(140, 250, 150, 30);
		 //Allow edit single line email text

		this.add(User);
		this.add(PIN);
		this.add(Ensure);
		this.add(Email);
		this.add(Enter);
		this.add(Cancel);
		this.add(name);
		this.add(pin);
		this.add(repin);
		this.add(email);

		//add listener
		Enter.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				if (pin.getText().contentEquals(repin.getText())) {
					writer.println("Signin");
					writer.println(name.getText());
					writer.println(pin.getText());
					writer.println(email.getText());
				} else {
					JOptionPane.showMessageDialog(null, "The pin doesn't fit.Please sign up again.");
					name.setText(" ");
					pin.setText(" ");
					repin.setText(" ");
					email.setText(" ");
				}
			}
		});
		Cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					socket.close();
					//close the socket
				} catch (IOException e1) {
					e1.printStackTrace();
					/*The command line prints the location and reason
					for the error message in the program*/
				}
				dispose();
				//realse the window
			}
		});

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*Automatically hide and release the form 
		after calling any registered WindowListener object*/
		this.setVisible(true);
		//Sets the object of the this reference to be visible
		connect();
	}

	boolean isrun = true;

	@Override
	public void run() {
		try {
			while (isrun) { 
				if (reader != null) {
					String instruction = reader.readLine();
					
					if (instruction.matches("rename")) {
						JOptionPane.showMessageDialog(null, "This name has exist,Please pick another name !");
						name.setText(" ");
						pin.setText(" ");
						repin.setText(" ");
						email.setText(" ");
					}
					if (instruction.matches("reemail")) {
						JOptionPane.showMessageDialog(null, "This emailbox has been used !");
						name.setText(" ");
						pin.setText(" ");
						repin.setText(" ");
						email.setText(" ");
					}
					if (instruction.matches("success")) {
						JOptionPane.showMessageDialog(null, "Congratuation! ");
						account new_account = new account(name.getText(), pin.getText());
						new_account.setEmail(email.getText());
						users.accounts.add(new_account);
						new CourseTable();
						isrun = false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			/*The command line prints the location and reason
			for the error message in the program*/
		} finally {
			try {
				if (reader != null) {
					reader.close();
					//close the reader
				}
				if (socket != null) {
					socket.close();
					//close the socket
				}
			} catch (IOException e) {
				e.printStackTrace();
				/*The command line prints the location and reason
				for the error message in the program*/
			}
		}
	}
}
