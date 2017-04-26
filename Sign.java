package table;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

public class Sign extends JFrame {

	  /*creat some JLabel*/
	JLabel User; 
	JLabel PIN;
	JLabel Ensure;
	JLabel Email;
	  /*creat some button*/
	JButton Enter;
	JButton Cancel;
	  /*Create some string*/
	JTextField name;
	JTextField pin;
	JTextField repin;
	JTextField email;
	private PrintWriter writer; 
	   // declare PrintWriter class object
	private BufferedReader reader; 
	   // declare BufferedReader object
	private Socket socket; 
	   // declare Socket object

	private void connect() { 
		// Connection socket method

		try { 
			// Catch exception
			socket = new Socket("localhost", 1978); 
			   // Instantiate Socket objects
			while (true) {
				writer = new PrintWriter(socket.getOutputStream(), true);
				   // Create output stream object
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); 
				   // Instantiate BufferedReader objects

				getServerInfo();
			}
		} catch (Exception e) {
			e.printStackTrace(); 
			   // Output exception information
		}
	}

	private void getServerInfo() {
		try {
			while (true) { 
				// If the socket is connected
				if (reader != null) {
					String line = reader.readLine();
					   // Read the message sent by the server
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			   //Output exception information
		} finally {
			try {
				if (reader != null) {
					reader.close();
					   // Close flow
				}
				if (socket != null) {
					socket.close(); 
					   // Close socket
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

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
		User.setBounds(65, 100, 100, 30);
		PIN = new JLabel("PIN :");
		PIN.setBounds(65, 150, 100, 30);
		Ensure = new JLabel("Ensure PIN :");
		Ensure.setBounds(65, 200, 100, 30);
		Email = new JLabel("Email :");
		Email.setBounds(65, 250, 100, 30);
		  /*creat some JButton and set the size ,show the coordinate of the rectangle of each*/
		Enter = new JButton("Enter");
		Enter.setBounds(225, 350, 80, 30);
		Cancel = new JButton("Cancel");
		Cancel.setBounds(95, 350, 80, 30);
		
		  /*Create some string and set the size ,show the coordinate of the rectangle of each*/
		name = new JTextField();
		name.setBounds(150, 100, 120, 30);
		pin = new JTextField();
		pin.setBounds(150, 150, 120, 30);
		repin = new JTextField();
		repin.setBounds(150, 200, 120, 30);
		email = new JTextField();
		email.setBounds(140, 250, 150, 30);

		  /*add these things to the window*/
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

		Enter.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				System.out.println(pin.getText());
				System.out.println(repin.getText());
				if (pin.getText().contentEquals(repin.getText())) {
					writer.println("N" + name.getText());
					writer.println("P" + pin.getText());
					   // Writes a message in a text box to a stream
					writer.println("E" + email.getText());
					   //JOptionPane.showMessageDialog(null, "You created a new account!");
					
					account new_account = new account(name.getText(), pin.getText());
					new_account.setEmail(email.getText());
					users.accounts.add(new_account);
					new CourseTable(users.accounts.indexOf(new_account));
				} else {
					JOptionPane.showMessageDialog(null, "The pin doesn't fit.Please sign up again.");
					// name.setText(" ");
					// pin.setText(" ");
					// repin.setText(" ");
					// email.setText(" ");
				}
			}
		});
		Cancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					socket.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				dispose();
			}	
		});

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 //When the user clicks a button ,close the window
		this.setVisible(true);
		//Enable visualization of the table
		connect();
	}
}
