package Server;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ServerSocketFrame extends JFrame {
	private JTextField tf_send;
	private JTextArea ta_info;
	private PrintWriter writer; // 声明PrintWriter类对象
	private ServerSocket server; // 声明ServerSocket对象
	private Socket socket; // 声明Socket对象socket
	private Vector<Socket> vector = new Vector<Socket>();// 用于存储连接到服务器的客户端套接字对象
	private int counts = 0;// 用于记录连接的客户人数
	private String instruction;

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

			try {
				writer = new PrintWriter(socket.getOutputStream(), true);
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 实例化BufferedReader对象
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

		public void run() {
			try {
				if (socket != null) {

					try {
						while (true) { // 如果套接字是连接状态
							String line = reader.readLine();// 读取客户端信息
							if (line != null) {
								if (line.equals("Signin")) {
									Signin();
								} else if (line.equals("Login")) {
									Loging();
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

		private void Loging() {
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
					uID = rs.getInt(1);
				}
				if (uID != 0) {
					// System.out.println("发出去的的id 是" + uID);
					writer.println("success");
					CourseTable(uID);
				} else
					writer.println("fail");
				rs.close();
				ps.close();// 关闭PreparedStatement对象
				conn.close();// 关闭连接
			} catch (

			SQLException ee) {
				writer.println("fail to save。\n" + ee.getMessage());// 向客户端输出保存成功的信息
			}
		}

		private void CourseTable(int uID) throws SQLException {

			Connection conn = DAO.getConn();
			String sql1 = "SELECT * FROM  course WHERE uID =" + uID;
			java.sql.Statement st = conn.createStatement();
			ResultSet rs1 = st.executeQuery(sql1);

			StringBuilder stringBuilder = new StringBuilder();

			while (rs1.next()) {
				int uid = rs1.getInt("uID");
				int row = rs1.getInt("row");
				int column = rs1.getInt("col");
				String name = rs1.getString("cName");
				String room = rs1.getString("room");
				String teacher = rs1.getString("teacher");
				stringBuilder.append("=" + uid + ":" + row + ":" + column + ":" + name + ":" + room + ":" + teacher);
			}
			writer.println("course");
			writer.println(stringBuilder.toString());

		}

		private void Signin() {
			// TODO Auto-generated method stub
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

				String sql = "insert into user (uName,pin,email) values(?,?,?)";// 定义SQL
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, value[0]);// 为第1个参数赋值
				ps.setString(2, value[1]);// 为第2个参数赋值
				ps.setString(3, value[2]);// 为第2个参数赋值
				int flag = ps.executeUpdate(); // 执行SQL语句，获得更新记录数
				ps.close();// 关闭PreparedStatement对象
				conn.close();// 关闭连接
				if (flag > 0) {
					ta_info.append("save into database sucessfully。\n");// 在服务器输出结果
					writer.println("success");
				} else {
					ta_info.append("fail to save。\n");// 在服务器输出结果
					instruction = "fail";
				}
			} catch (SQLException ee) {
				writer.println("fail to save。\n" + ee.getMessage());// 向客户端输出保存成功的信息
			}
		}

		private void Edit() throws IOException {
			String instruction = reader.readLine();
			// TODO Auto-generated method stub
			String[] value = new String[6];// get Course name, get room, get teacher
			try {
				value[0] = reader.readLine();
				value[1] = reader.readLine();
				value[2] = reader.readLine();
				value[3] = reader.readLine();
				value[4] = reader.readLine();
				value[5] = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			ta_info.append("course :" + value[0] + "\n");// 显示客户端信息
			ta_info.append("room : " + value[1] + "\n");
			ta_info.append("teacher : " + value[2] + "\n");
			ta_info.append("userID : " + Integer.valueOf(value[3]) + "\n");
			ta_info.append("row : " + Integer.valueOf(value[4]) + "\n");
			ta_info.append("col : " + Integer.valueOf(value[5]) + "\n");

			try {
				Connection conn = DAO.getConn();// 获得数据库连接
				// 当用户更新课表时
				if (instruction.equals("update")) {
					String sql = "update course set cName = ? where uID =? and row = ? and col = ? ";
					String sql1 = "update course set room = ? where uID =? and row = ? and col = ?";
					String sql2 = "update course set teacher = ? where  uID =? and row = ? and col = ?";
					// 创建PreparedStatement对象，并传递SQL语句
					PreparedStatement ps = conn.prepareStatement(sql);
					PreparedStatement ps1 = conn.prepareStatement(sql1);
					PreparedStatement ps2 = conn.prepareStatement(sql2);

					ps.setString(1, value[0]);
					ps.setInt(2, Integer.valueOf(value[3]));
					ps.setInt(3, Integer.valueOf(value[4]));
					ps.setInt(4, Integer.valueOf(value[5]));
					int flag = ps.executeUpdate(); // 执行SQL语句，获得更新记录数

					ps1.setString(1, value[1]);
					ps1.setInt(2, Integer.valueOf(value[3]));
					ps1.setInt(3, Integer.valueOf(value[4]));
					ps1.setInt(4, Integer.valueOf(value[5]));
					int flag1 = ps1.executeUpdate(); // 执行SQL语句，获得更新记录数

					ps2.setString(1, value[2]);
					ps2.setInt(2, Integer.valueOf(value[3]));
					ps2.setInt(3, Integer.valueOf(value[4]));
					ps2.setInt(4, Integer.valueOf(value[5]));
					int flag2 = ps2.executeUpdate();

					ps.close();
					ps1.close();
					ps2.close();

					if (flag == 1 && flag1 == 1 && flag2 == 1) {
						ta_info.append("update success");
						writer.println("success");
					}
				} else if (instruction.equals("insert")) {
					String sql = "insert into course values(?,?,?,?,?,?)";
					PreparedStatement ps = conn.prepareStatement(sql);

					ps.setString(1, value[0]);
					ps.setString(2, value[1]);
					ps.setString(3, value[2]);
					ps.setInt(4, Integer.valueOf(value[3]));
					ps.setInt(5, Integer.valueOf(value[4]));
					ps.setInt(6, Integer.valueOf(value[5]));
					int flag = ps.executeUpdate();

					if (flag == 1) {
						ta_info.append("insert success");
						writer.println("success");
					}

					ps.close();// 关闭PreparedStatement对象
				}
				conn.close();// 关闭连接

			} catch (SQLException ee) {
				writer.println("fail to save。\n" + ee.getMessage());// 向客户端输出保存成功的信息
			}
		}

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

		final JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);
		final JLabel label_1 = new JLabel();
		label_1.setForeground(Color.BLACK);
		label_1.setFont(new Font("Chalkboard", Font.BOLD, 22));
		label_1.setText("Server Side");
		panel_1.add(label_1);
	}
}
