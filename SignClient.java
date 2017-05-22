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
	JLabel PIN;
	JLabel Ensure;
	JLabel Email;
	JButton Enter;
	JButton Cancel;
	JTextField name;
	JTextField pin;
	JTextField repin;
	JTextField email;
	private PrintWriter writer;
	private BufferedReader reader;
	private Socket socket;

	private void connect() {

		try {
			socket = new Socket("localhost", 1978);
			// while (true) {
			writer = new PrintWriter(socket.getOutputStream(), true);// 创建输出流对象
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 实例化BufferedReader对象
			new Thread(this).start();
			// }
		} catch (Exception e) {
			e.printStackTrace(); // 输出异常信息
		}
	}

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
		repin = new JTextField();
		repin.setBounds(150, 200, 120, 30);
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
		this.add(repin);
		this.add(email);

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
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				dispose();
			}
		});

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		connect();
	}

	boolean isrun = true;

	@Override
	public void run() {
		try {
			while (isrun) { // 如果套接字是连接状态
				if (reader != null) {
					String instruction = reader.readLine();
					// 读取服务器发送的信息
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
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
				if (socket != null) {
					socket.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
