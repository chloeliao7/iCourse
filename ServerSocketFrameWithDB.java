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
	//Allow edit single line text
	private JTextArea ta_info;
	//Show multiline lines of text only
	private PrintWriter writer; 
	//creat a print object
	private ServerSocket server; 
	//creat a server socket
	private Socket socket;
	//creat a socket
	private Vector<Socket> vector = new Vector<Socket>();
	private int counts = 0;
	private String instruction;

	public void getServer() {
		try {
			server = new ServerSocket(1978); 
			ta_info.append("Connction Success\n");
			while (true) {
				socket = server.accept(); 
				counts++;
				ta_info.append("Client" + counts + "connected successed" + "\n"); 
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println(String.valueOf(counts - 1));
				vector.add(socket);

				new ServerThread(socket).start();
			}
		} catch (Exception e) {
			e.printStackTrace(); 
			/*The command line prints the location and reason
			for the error message in the program*/
		}
	}

	class ServerThread extends Thread {
		Socket socket = null; 
		BufferedReader reader; 

		public ServerThread(Socket socket) { 
			this.socket = socket;

			try {
				writer = new PrintWriter(socket.getOutputStream(), true);
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 实例化BufferedReader对象
			} catch (IOException e) {
				e.printStackTrace();
				/*The command line prints the location and reason
				for the error message in the program*/
			}

		}

		public void run() {
			try {
				if (socket != null) {

					try {
						while (true) { 
							String line = reader.readLine();
							if (line != null) {

								if (line.equals("Signin")) {
									Signin();
								} else if (line.equals("Login")) {
									Loging();
								}

							}
						}
					} finally {
						try {
							if (reader != null) {
								reader.close();
								//close the reader
							}
							if (socket != null) {
								socket.close();
								//close the socket
							}
						} catch (IOException e) {
							e.printStackTrace();
							/*The command line prints the location and reason
							for the error message in the program*/
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				/*The command line prints the location and reason
				for the error message in the program*/
			}
		}

		private void Loging() {
			// TODO Auto-generated method stub
			String[] value = new String[2];
			try {
				value[0] = reader.readLine();
				value[1] = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				/*The command line prints the location and reason
				for the error message in the program*/
			}
			/*write queries for MySQL*/
			ta_info.append("user name is " + value[0] + "\n");
			ta_info.append("password is " + value[1] + "\n");
			try {
				int uID = 0;
				Connection conn = DAO.getConn();
				String sql = "select uID from user where uName = ? and pin = ?";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, value[0]);
				ps.setString(2, value[1]);
				ResultSet rs = ps.executeQuery();

				while (rs.next()) {
					uID = rs.getInt(1);
				}

				System.out.println(uID);
				if (uID != 0)
					writer.println("success");
				else
					writer.println("fail");
				rs.close();
				ps.close();
				conn.close();
			} catch (SQLException ee) {
				writer.println("fail to save" + ee.getMessage());
			}
		}

		private void Signin() {
			// TODO Auto-generated method stub
			String[] value = new String[3];
			try {
				value[0] = reader.readLine();
			} catch (IOException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}
			try {
				value[1] = reader.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				value[2] = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			ta_info.append("user name is " + value[0] + "\n");
			ta_info.append("password is " + value[1] + "\n");
			ta_info.append("email is " + value[2] + "\n");
			try {
				Connection conn = DAO.getConn();
	
				String sql = "insert into user (uName,pin,email) values(?,?,?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setString(1, value[0]);
				ps.setString(2, value[1]);
				ps.setString(3, value[2]);
				int flag = ps.executeUpdate();
				ps.close();
				conn.close();
				if (flag > 0) {
					ta_info.append("save into database sucessfully");
					writer.println("success");
				} else {
					ta_info.append("fail to save");
					instruction = "fail";
				}
			} catch (SQLException ee) {
				writer.println("fail to save" + ee.getMessage());
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

		/*Buttons, text boxes, etc. can be placed in this container JPanel*/
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

