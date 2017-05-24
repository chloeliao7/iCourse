package Server;

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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import Client.Course;

public class ServerSocketFrameWithDB extends JFrame {
	private JTextField tf_send;
	private JTextArea ta_info;
	private PrintWriter writer; // 声明PrintWriter类对象
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
						while (true) { // 如果套接字是连接状态
							String line = reader.readLine();// 读取客户端信息
							if (line != null) {

								if (line.equals("Signin")) {
									Signin();
								} else if (line.equals("Login")) {
									Login();
								} else if (line.equals("Edit")) {
									Edit();
								}
							}
						}
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// Connection conn = DAO.getConn();// 获得数据库连接
		// String sql = "select * from course where uID = ? ";
		// PreparedStatement ps = conn.prepareStatement(sql);
		// ps.setString(1, value[0]);// 为第1个参数赋值
		// ResultSet rs = ps.executeQuery();

		private void Edit() {
			// TODO Auto-generated method stub
			String[] value = new String[3];// get Course name, get room, get
											// teacher's name
			try {
				value[0] = reader.readLine();
				value[1] = reader.readLine();
				value[2] = reader.readLine();
			} catch (IOException e2) {
				e2.printStackTrace();
			}

			int[] values = new int[3];// get userID , courseOrder , day
			try {
				values[0] = reader.read();
				values[1] = reader.read();
				values[2] = reader.read();
			} catch (IOException e) {
				e.printStackTrace();
			}

			ta_info.append("Course name is " + value[0] + "\n");// 获得客户端信息
			ta_info.append("room is " + value[1] + "\n");
			ta_info.append("teacher is " + value[2] + "\n");
			ta_info.append("userID is " + values[0] + "\n");
			ta_info.append("course is " + values[1] + "\n");
			ta_info.append("day is " + values[2] + "\n");

			try {
				Connection conn = DAO.getConn();// 获得数据库连接
				System.out.println(value[0]);

				String sql3 = "select cName from course where uID = ? and courseOrder = ? and courseDay = ?";
				PreparedStatement ps = conn.prepareStatement(sql3);
				ps.setInt(1, values[0]);// 为第1个参数赋值
				ps.setInt(2, values[1]);// 为第2个参数赋值
				ps.setInt(3, values[2]);// 为第3个参数赋值
				ResultSet rs = ps.executeQuery();
				int flag = 0; // 执行SQL语句，获得更新记录数
				while (rs.next()) {
					if (rs.getString("cName") != null) {
						String sql = "update course set cName = ? where courseOrder = ? and courseDay = ?";// 定义SQL
						// PreparedStatement ps = conn.prepareStatement(sql);
						// 创建PreparedStatement对象，并传递SQL语句
						ps = conn.prepareStatement(sql);
						ps.setString(1, value[0]);// 为第1个参数赋值
						ps.setInt(2, values[1]);// 为第2个参数赋值
						ps.setInt(3, values[2]);// 为第3个参数赋值

						flag = ps.executeUpdate(); // 执行SQL语句，获得更新记录数

						String sql1 = "update course set room = ? where courseOrder = ? and courseDay = ?";
						ps = conn.prepareStatement(sql1);
						ps.setString(1, value[1]);// 为第1个参数赋值
						ps.setInt(2, values[1]);// 为第2个参数赋值
						ps.setInt(3, values[2]);// 为第3个参数赋值

						flag = ps.executeUpdate(); // 执行SQL语句，获得更新记录数

						String sql2 = "update course set teacher = ? where courseOrder = ? and courseDay = ?";
						ps = conn.prepareStatement(sql2);
						ps.setString(1, value[2]);// 为第1个参数赋值
						ps.setInt(2, values[1]);// 为第2个参数赋值
						ps.setInt(3, values[2]);// 为第3个参数赋值

						flag = ps.executeUpdate(); // 执行SQL语句，获得更新记录数
					} else {
						String sql = "INSERT INTO course VALUES （?,?,?) WHERE courseOrder = ? and courseDay = ? ";
						ps = conn.prepareStatement(sql);
						ps.setString(1, value[0]);// 为第1个参数赋值
						ps.setString(2, value[1]);// 为第2个参数赋值
						ps.setString(3, value[2]);// 为第3个参数赋值
						ps.setInt(4, values[1]);// 为第2个参数赋值
						ps.setInt(5, values[2]);// 为第3个参数赋值

						flag = ps.executeUpdate(); // 执行SQL语句，获得更新记录数
					}
				}

				ps.close();// 关闭PreparedStatement对象
				conn.close();// 关闭连接
				if (flag > 0) {
					ta_info.append("and save into database sucessfully。\n");
					writer.println("save sucessfully。");// 向客户端输出保存成功的信息
				} else {
					writer.println("fail to save。\n");// 向客户端输出保存成功的信息
				}
			} catch (SQLException ee) {
				writer.println("fail to save。\n" + ee.getMessage());// 向客户端输出保存成功的信息
			}
		}

		private void Login() {
			// TODO Auto-generated method stub
			String[] value = new String[2];// 创建数组以存储客户端接收的信息
			try {
				value[0] = reader.readLine();
				value[1] = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ta_info.append("user name is " + value[0] + "\n");// 获得客户端信息
			ta_info.append("password is " + value[1] + "\n");
			try {
				int uID = 0;
				Connection conn = DAO.getConn();// 获得数据库连接
				String sql = "select uID from user where uName = ? and pin = ?";// 定义SQL
				PreparedStatement ps = conn.prepareStatement(sql);// 创建PreparedStatement对象，并传递SQL语句
				ps.setString(1, value[0]);// 为第1个参数赋值
				ps.setString(2, value[1]);// 为第2个参数赋值
				ResultSet rs = ps.executeQuery();
				while (rs.next()) {
					if (rs.getInt(uID) != 0) {
						uID = rs.getInt(uID);
					}
				}
				if (uID != 0)
					writer.println("success");
				else
					writer.println("fail");
				rs.close();
				ps.close();// 关闭PreparedStatement对象
				conn.close();// 关闭连接
			} catch (SQLException ee) {
				writer.println("fail to save。\n" + ee.getMessage());// 向客户端输出保存成功的信息
			}
		}

		private void Signin() {
			String[] value = new String[3];// 创建数组以存储客户端接收的信息
			try {
				value[0] = reader.readLine();
				value[1] = reader.readLine();
				value[2] = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ta_info.append("user name is " + value[0] + "\n");// 获得客户端信息
			ta_info.append("password is " + value[1] + "\n");
			ta_info.append("email is " + value[2] + "\n");
			try {

				Connection conn = DAO.getConn();// 获得数据库连接
				System.out.println(value[0]);
				String sql = "insert into user (uName,pin,email) values(?,?,?)";// 定义SQL
																				// PreparedStatement
																				// ps
																				// =
																				// conn.prepareStatement(sql);//
																				// 创建PreparedStatement对象，并传递SQL语句
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, value[0]);// 为第1个参数赋值
				ps.setString(2, value[1]);// 为第2个参数赋值
				ps.setString(3, value[2]);// 为第2个参数赋值

				int flag = ps.executeUpdate(); // 执行SQL语句，获得更新记录数
				ps.close();// 关闭PreparedStatement对象
				conn.close();// 关闭连接
				if (flag > 0) {
					ta_info.append("and save into database sucessfully。\n");
					writer.println("save sucessfully。");// 向客户端输出保存成功的信息
				} else {
					writer.println("fail to save。\n");// 向客户端输出保存成功的信息
				}
			} catch (SQLException ee) {
				writer.println("fail to save。\n" + ee.getMessage());// 向客户端输出保存成功的信息
			}
		}

	}

	private void writeInfo(PrintWriter writer, String text) {
		writer.println(text);
	}

	public static void main(String[] args) {
		ServerSocketFrameWithDB frame = new ServerSocketFrameWithDB();
		frame.setVisible(true);
		frame.getServer();
	}

	public ServerSocketFrameWithDB() {
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
