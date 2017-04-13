package version2;

import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

public class Sign extends JFrame {

	JLabel User;
	JLabel PIN;
	JLabel Ensure;
	JLabel Email;
	JButton Enter;
	JButton Cancel;
	JTextField name;
	JTextField pin;
	JTextField epin;
	JTextField email;

	public static void main(String[] args) {
		new Sign();
	}

	public Sign() {
		this.setSize(400, 500);
		this.setLayout(null);
		this.setLocation(new Point(600, 200));

		User = new JLabel("User :");
		User.setBounds(65, 100, 100, 30);
		PIN = new JLabel("PIN :");
		PIN.setBounds(65, 150, 100, 30);
		Ensure = new JLabel("Ensure PIN :");
		Ensure.setBounds(65, 200, 100, 30);
		Email = new JLabel("Email :");
		Email.setBounds(65, 250, 100, 30);
		Enter = new JButton("Enter");
		Enter.setBounds(225, 350, 80, 30);
		Cancel = new JButton("Cancel");
		Cancel.setBounds(95, 350, 80, 30);

		name = new JTextField();
		name.setBounds(150, 100, 120, 30);
		pin = new JTextField();
		pin.setBounds(150, 150, 120, 30);
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
		this.setVisible(true);
	}
}
