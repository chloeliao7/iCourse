
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

	JLabel User;
	JLabel PIN;
	JButton Enter;
	JButton Cancel;
	JTextField name;
	JTextField pin;

	public static void main(String[] args) {
		new Loging();
	}

	public Loging() {
		this.setSize(400, 500);
		getContentPane().setLayout(null);
		this.setLocation(new Point(600, 200));

		User = new JLabel("User:");
		User.setBounds(65, 100, 100, 30);
		PIN = new JLabel("PIN:");
		PIN.setBounds(65, 150, 100, 30);
		Enter = new JButton("Enter");
		Enter.setBounds(225, 300, 80, 30);
		Cancel = new JButton("Cancel");
		Cancel.setBounds(95, 300, 80, 30);

		name = new JTextField();
		name.setBounds(140, 100, 120, 30);
		pin = new JTextField();
		pin.setBounds(140, 150, 120, 30);

		getContentPane().add(User);
		getContentPane().add(PIN);
		getContentPane().add(Enter);
		getContentPane().add(Cancel);
		getContentPane().add(name);
		getContentPane().add(pin);

		listenerForLoging myaction = new listenerForLoging(this);
		Enter.addActionListener(myaction);
		Cancel.addActionListener(myaction);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);

		try {
			Userinformation(name, pin);
		} catch (IIOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			e.printStackTrace();
		}
	}
}
