
import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.IIOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Loging extends JFrame {

	  /*creat some JLabel*/
	JLabel User;
	JLabel PIN;
	  /*creat some JButton*/
	JButton Enter;
	JButton Cancel;
	  /*creat some String*/
	JTextField name;
	JTextField pin;

	public static void main(String[] args) {
		new Loging();
		  //you can use this method in main operation
	}

	public Loging() {
		this.setSize(400, 500);
		getContentPane().setLayout(null);
		   //Set layout manager
		this.setLocation(new Point(600, 200));

		  /*set some JLabel and their rectangle size and location*/
		User = new JLabel("User:");
		User.setBounds(65, 100, 100, 30);
		PIN = new JLabel("PIN:");
		PIN.setBounds(65, 150, 100, 30);
		  /*set some JButton and their rectangle size and location*/
		Enter = new JButton("Enter");
		Enter.setBounds(225, 300, 80, 30);
		Cancel = new JButton("Cancel");
		Cancel.setBounds(95, 300, 80, 30);

		/*Create some string and set the size ,show the coordinate of the rectangle of each*/
		name = new JTextField();
		name.setBounds(140, 100, 120, 30);
		pin = new JTextField();
		pin.setBounds(140, 150, 120, 30);
		  /*add these things so that it can be shown in the window*/
		getContentPane().add(User);
		getContentPane().add(PIN);
		getContentPane().add(Enter);
		getContentPane().add(Cancel);
		getContentPane().add(name);
		getContentPane().add(pin);

		  /*add listener*/
		listenerForLoging myaction = new listenerForLoging(this);
		Enter.addActionListener(myaction);
		Cancel.addActionListener(myaction);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   //When the user clicks a button ,close the window
		this.setVisible(true);
		   //Enable visualization of the table
		try {
			Userinformation(name, pin);
		} catch (IIOException e) {
			  // TODO Auto-generated catch block
			e.printStackTrace();
			   //when some errors happened,it will show the error on the screen 
		}
	}

	public void Userinformation(JTextField name, JTextField pin) throws IIOException {
		try {
			File file = new File("data/test.txt");
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fw = new FileWriter(file.getAbsoluteFile());
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(name.getText());
			bw.write(pin.getText());
			bw.close();
			System.out.println("File Done");
		} catch (IOException e) {
			   // TODO Auto-generated catch block
			e.printStackTrace();
			 //when some errors happened,it will show the error on the screen 
		}
	}
}
