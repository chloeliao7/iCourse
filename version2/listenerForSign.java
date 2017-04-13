package version2;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import setting.account;
import setting.users;

public class listenerForSign implements ActionListener {

	Sign a;

	public listenerForSign(Sign window) {
		a = window;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == a.Enter) {
			String user_name = a.name.getText();
			String pin = a.pin.getText();
			String repin = a.epin.getText();
			String email = a.email.getText();

			if (pin.contentEquals(repin)) {											
				account new_account = new account(user_name, pin);
				new_account.setEmail(email);
				users.accounts.add(new_account);
				////////// database£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡£¡
				JOptionPane.showMessageDialog(null, "You created a new account!");
				a.dispose();
				new CourseTable(users.accounts.indexOf(new_account));
			} else if (pin != repin) {
				System.out.println("the pin doesn't fit.Please resign-up.");
				JOptionPane.showMessageDialog(null, "The pin doesn't fit.Please sign up again.");
				new Sign();
			}
		}
	}
}
