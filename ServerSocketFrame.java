package table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerSocketFrame extends JFrame {
	private JTextField tf_send;
	private JTextArea ta_info;
	private ServerSocket server; // 声明ServerSocket对象
	private Socket socket; // 声明Socket对象socket
	private Vector<Socket> vector = new Vector<Socket>();// 用于存储连接到服务器的客户端套接字对象
	private int counts = 0;// 用于记录连接的客户人数

	public void getServer() {
		try {
			server = new ServerSocket(1978); // 实例化Socket对象
			ta_info.append("Connction Success\n"); // 输出信息
			while (true) {
				socket = server.accept(); // 实例化Socket对象
				counts++;
				ta_info.append("Client" + counts + "connected successed" + "\n"); // 输出信息
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println(String.valueOf(counts - 1));// 向客户端发送套接字索引
				vector.add(socket);// 存储客户端套接字对象
				new ServerThread(socket).start();// 创建并启动线程序
			}
		} catch (Exception e) {
			e.printStackTrace(); // 输出异常信息
		}
	}

	class ServerThread extends Thread {
		Socket socket = null; // 创建Socket对象
		BufferedReader reader; // 声明BufferedReader对象

		public ServerThread(Socket socket) { // 构造方法
			this.socket = socket;
		}

		public void run() {
			try {
				if (socket != null) {
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 实例化BufferedReader对象

					try {
						while (true) {// connect successes
							String line = reader.readLine();// 读取客户端信息

							if (line != null) {
								if (line.startsWith("N"))
									ta_info.append("user name is " + line.substring(1) + "\n");
								if (line.startsWith("P"))
									ta_info.append("password is " + line.substring(1) + "\n");
								if (line.startsWith("E"))
									ta_info.append("email is " + line.substring(1) + "\n");
								if (line.startsWith("n"))
									ta_info.append("class name is " + line.substring(1) + "\n");
								if (line.startsWith("l"))
									ta_info.append("location is " + line.substring(1) + "\n");
								if (line.startsWith("t"))
									ta_info.append("teacher is " + line.substring(1) + "\n");
							}
						}
					} finally {
						try {
							if (reader != null) {
								reader.close();// 关闭流
							}
							if (socket != null) {
								socket.close();
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	private void writeInfo(PrintWriter writer, String text) {
		writer.println(text);
	}

	public static void main(String[] args) {
		ServerSocketFrame frame = new ServerSocketFrame();
		frame.setVisible(true);
		frame.getServer();
	}

	public ServerSocketFrame() {
		super();
		setTitle("Server");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 379, 260);

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);

		ta_info = new JTextArea();
		scrollPane.setViewportView(ta_info);

		final JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.SOUTH);

		final JLabel label = new JLabel();
		label.setText("Server：");
		panel.add(label);

		tf_send = new JTextField();
		tf_send.setPreferredSize(new Dimension(150, 25));
		panel.add(tf_send);

		final JButton button = new JButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				for (int i = 0; i < vector.size(); i++) {
					Socket socket = vector.get(i);// 获得连接成功的套接字对象
					PrintWriter writer;
					try {
						if (socket != null && !socket.isClosed()) {
							writer = new PrintWriter(socket.getOutputStream(), true);// 创建输出流对象
							writeInfo(writer, tf_send.getText()); // 将文本框中信息写入流
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				ta_info.append("Server：" + tf_send.getText() + "\n"); // 将文本框中信息显示在文本域中
				tf_send.setText(""); // 将文本框清空
			}
		});
		button.setText("Send");
		panel.add(button);

		final JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);

		final JLabel label_1 = new JLabel();
		label_1.setForeground(Color.BLACK);
		label_1.setFont(new Font("Chalkboard", Font.BOLD, 22));
		label_1.setText("Server Side");
		panel_1.add(label_1);
	}
}
