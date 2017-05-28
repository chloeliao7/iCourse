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
import javax.swing.JTextField;

public class edit extends JFrame implements Runnable {
	JLabel course_name;
	JLabel classroom;
	JLabel lecturer;
	JButton confirm;
	JButton exit;
	JTextField name;
	JTextField location;
	JTextField teacher;
	int account_id;
	int row;
	int column;
	private PrintWriter writer; // 声明PrintWriter类对象
	private BufferedReader reader; // 声明BufferedReader对象
	private Socket socket; // 声明Socket对象
	private String instruction;

	private void connect() { // 连接套接字方法
		try { // 捕捉异常
			writer = new PrintWriter(socket.getOutputStream(), true);// 创建输出流对象
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 实例化BufferedReader对象
			new Thread(this).start();
			// getServerInfo();

		} catch (Exception e) {
			e.printStackTrace(); // 输出异常信息
		}
	}

	public edit(Socket socket, String ins, int row, int column, int account_id) {
		this.socket = socket;
		this.instruction = ins;
		this.account_id = account_id;
		this.row = row;
		this.column = column;

		this.setSize(400, 500);
		this.setLayout(null);
		this.setLocation(new Point(600, 200));
		setTitle("edit");
		course_name = new JLabel("Course :");
		course_name.setBounds(65, 100, 100, 30);
		classroom = new JLabel("classroom:");
		classroom.setBounds(65, 150, 100, 30);
		lecturer = new JLabel("lecturer:");
		lecturer.setBounds(65, 200, 100, 30);
		confirm = new JButton("confirm");
		confirm.setBounds(225, 300, 80, 30);
		exit = new JButton("Cancel");
		exit.setBounds(95, 300, 80, 30);

		name = new JTextField();
		name.setBounds(140, 100, 120, 30);
		location = new JTextField();
		location.setBounds(140, 150, 120, 30);
		teacher = new JTextField();
		teacher.setBounds(140, 200, 120, 30);

		this.add(course_name);
		this.add(classroom);
		this.add(lecturer);
		this.add(confirm);
		this.add(exit);
		this.add(name);
		this.add(location);
		this.add(teacher);

		confirm.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				writer.println("Edit");
				writer.println(instruction);
				writer.println(name.getText());
				writer.println(location.getText());// 将文本框中信息写入流
				writer.println(teacher.getText());
				writer.println(account_id);
				writer.println(row);
				writer.println(column);
				new CourseTable(socket);
				dispose();
			}
		});
		exit.addActionListener(new ActionListener() {
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

	boolean isrun = true;

	@Override
	public void run() {
		try {
			while (isrun) { // 如果套接字是连接状态
				if (reader != null) {
					String line = reader.readLine();// 读取服务器发送的信息
					// if change success
					if (line.equals("success"))
						this.dispose();
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
}
