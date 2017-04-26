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
	private PrintWriter writer; // 声明PrintWriter类对象
	private BufferedReader reader; // 声明BufferedReader对象
	private Socket socket; // 声明Socket对象

	private void connect() { // 连接套接字方法

		try { // 捕捉异常
			socket = new Socket("localhost", 1978); // 实例化Socket对象
			while (true) {
				writer = new PrintWriter(socket.getOutputStream(), true);// 创建输出流对象
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 实例化BufferedReader对象

				getServerInfo();
			}
		} catch (Exception e) {
			e.printStackTrace(); // 输出异常信息
		}
	}

	private void getServerInfo() {
		try {
			while (true) { // 如果套接字是连接状态
				if (reader != null) {
					String line = reader.readLine();// 读取服务器发送的信息
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();// 关闭流
				}
				if (socket != null) {
					socket.close(); // 关闭套接字
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
				System.out.println(pin.getText());
				System.out.println(repin.getText());
				if (pin.getText().contentEquals(repin.getText())) {
					writer.println("N" + name.getText());
					writer.println("P" + pin.getText());
					// 将文本框中信息写入流
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
		this.setVisible(true);
		connect();
	}
}
