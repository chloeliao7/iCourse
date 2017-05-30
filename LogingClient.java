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

@SuppressWarnings("serial")
public class Loging extends JFrame implements Runnable {

	JLabel User;
	JLabel PIN;
	JButton Enter;
	JButton Cancel;
	JTextField name;
	JTextField pin;
	private PrintWriter writer; // 声明PrintWriter类对象
	private BufferedReader reader; // 声明BufferedReader对象
	private Socket socket; // 声明Socket对象

	private void connect() { // 连接套接字方法
		try {
			socket = new Socket("localhost", 1978);
			writer = new PrintWriter(socket.getOutputStream(), true);// 创建输出流对象
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 实例化BufferedReader对象
			new Thread(this).start();
		} catch (Exception e) {
			e.printStackTrace(); // 输出异常信息
		}
	}

	boolean isrun = true;

	@Override
	public void run() {
		try {
			while (isrun) { // 如果套接字是连接状态
				if (reader != null) {
					String instruction = reader.readLine();
					// 读取服务器发送的信息
					if (instruction.matches("fail")) {
						JOptionPane.showMessageDialog(null, "No this user or wrong pin, please sign in again!");
						name.setText(" ");
						pin.setText(" ");
					}
					if (instruction.matches("success")) {
						JOptionPane.showMessageDialog(null, "Welcom back ");
						new CourseTable(socket);
						isrun = false;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
	}

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
				}
				dispose();
			}
		});

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		connect();
	}

}
