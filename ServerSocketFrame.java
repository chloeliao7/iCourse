package table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
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
	private ServerSocket server; 
	   // Declare ServerSocket object
	private Socket socket; 
	   // declare the object of Socket socket
	private Vector<Socket> vector = new Vector<Socket>();
	   // Used to store client socket objects attached to the server
	private int counts = 0;
	   //Number of clients used to record connections

	public void getServer() {
		try {
			server = new ServerSocket(1978);
			   // Instantiate Socket objects
			ta_info.append("服务器套接字已经创建成功\n"); 
			   // Output information
			while (true) { 
				   // If the socket is connected
				socket = server.accept(); 
				   // Instantiate Socket objects
				counts++;
				ta_info.append("第" + counts + "个客户连接成功\n");
				   // Output information
				PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
				out.println(String.valueOf(counts - 1));
				   // Send socket index to client
				vector.add(socket);
				   // Storage client socket object
				new ServerThread(socket).start();
				   // Create and start the Thread program
			}
		} catch (Exception e) {
			e.printStackTrace(); 
			   //Output exception information
		}
	}

	class ServerThread extends Thread {
		Socket socket = null;
		   // Create Socket object
		BufferedReader reader; 
		   // declare BufferedReader object

		public ServerThread(Socket socket) { 
			   // Construction method
			this.socket = socket;
		}

		public void run() {
			try {
				if (socket != null) {
					reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					   // Instantiate BufferedReader objects
					int index = -1;
					   // Store index value of exit clients
					try {
						while (true) { 
							   // If the socket is connected
							String line = reader.readLine();
							   // Read client information
							try {
								index = Integer.parseInt(line);
								   // Gets the index value of the client
							} catch (Exception ex) {
								index = -1;
							}
							if (line != null) {
								  /*Get client information*/
								if (line.startsWith("N"))
									ta_info.append("user name is " + line.substring(1) + "\n"); 
								if (line.startsWith("P"))
									ta_info.append("password is " + line.substring(1) + "\n");
								if (line.startsWith("E"))
									ta_info.append("email is " + line.substring(1) + "\n");
							}
						}
					} catch (Exception e) {
						if (index != -1) {
							vector.set(index, null);
							   // Set the exit client socket to null
							ta_info.append("第" + (index + 1) + "个客户端已经退出。\n"); 
							   // Output exception information
						}
					} finally {
						try {
							if (reader != null) {
								reader.close();
								   // Close flow
							}
							if (socket != null) {
								socket.close(); 
								   // close socket
							}
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			//show the exception
		}

	}

	private void writeInfo(PrintWriter writer, String text) {
		writer.println(text);
	}

	public static void main(String[] args) { 
		   //Main method
		ServerSocketFrame frame = new ServerSocketFrame(); 
		   // Create class object
		frame.setVisible(true);
		   // Display Form
		frame.getServer(); 
		   // Calling method
	}

	public ServerSocketFrame() {
		super();
		setTitle("服务器端程序");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 379, 260);

		final JScrollPane scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		   //add scroll bar
  
		ta_info = new JTextArea();
		scrollPane.setViewportView(ta_info);

		final JPanel panel = new JPanel();
		   // global variable
		getContentPane().add(panel, BorderLayout.SOUTH);

		final JLabel label = new JLabel();
		label.setText("服务器发送的信息：");
		panel.add(label);

		tf_send = new JTextField();
		tf_send.setPreferredSize(new Dimension(150, 25));
		panel.add(tf_send);

		final JButton button = new JButton();
		button.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				for (int i = 0; i < vector.size(); i++) {
					Socket socket = vector.get(i);
					   //Socket socket for successful connection
					PrintWriter writer;
					try {
						if (socket != null && !socket.isClosed()) {
							writer = new PrintWriter(socket.getOutputStream(), true);
							   // Create output stream object
							writeInfo(writer, tf_send.getText()); 
							   // Writes a message in a text box to a stream
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					   //show the exception
				}
				ta_info.append("服务器发送的信息是：" + tf_send.getText() + "\n"); // 将文本框中信息显示在文本域中
				tf_send.setText("");
				   // Empty text box
			}
		});
		button.setText("发  送");
		panel.add(button);

		final JPanel panel_1 = new JPanel();
		getContentPane().add(panel_1, BorderLayout.NORTH);

		final JLabel label_1 = new JLabel();
		label_1.setForeground(new Color(0, 0, 255));
		label_1.setFont(new Font("", Font.BOLD, 22));
		label_1.setText("一对多通信——服务器端程序");
		panel_1.add(label_1);
	}
}
