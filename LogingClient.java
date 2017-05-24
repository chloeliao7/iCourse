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

@SuppressWarnings("serial")
public class Loging extends JFrame implements Runnable {

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

	boolean isrun = true;

	@Override
	public void run() {
		try {
			while (isrun) { 
				if (reader != null) {
					String instruction = reader.readLine();
					
					if (instruction.matches("fail")) {
						JOptionPane.showMessageDialog(null, "No this user or wrong pin, please sign in again!");
						name.setText(" ");
						pin.setText(" ");
					}
					if (instruction.matches("success")) {
						JOptionPane.showMessageDialog(null, "Welcom back ");
						account new_account = new account(name.getText(), pin.getText());
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

	public static void main(String[] args) {
		new Loging();
	}

	public Loging() {
		this.setSize(400, 500);
		//set the size of login
		getContentPane().setLayout(null);
		//Setting layout manager
		this.setLocation(new Point(600, 200));

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
		
		getContentPane().add(User);
		getContentPane().add(PIN);
		getContentPane().add(Enter);
		getContentPane().add(Cancel);
		getContentPane().add(name);
		getContentPane().add(pin);
		/*Initializes some containers 
		 * that adds some controls to the containers*/

		Enter.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				writer.println("Login");
				writer.println(name.getText());
				writer.println(pin.getText());
			}
		});
		Cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					socket.close();
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

}


