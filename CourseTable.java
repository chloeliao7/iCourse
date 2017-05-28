package Client;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class CourseTable extends JFrame implements Runnable {
	final JScrollPane scrollPane = new JScrollPane();
	JTable table;
	JButton save;
	String[][] content = new String[5][7];
	private PrintWriter writer;
	private BufferedReader reader;
	private Socket socket;
	private int uID;

	private void connect() {
		try {
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
				while (reader != null) {
					try {
						String tag = reader.readLine();
						System.out.println(tag);
						if (tag.equals("course")) {
							receiveCourse();
						}
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				isrun = false;
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

	private void receiveCourse() {
		try {
			String courses = reader.readLine();
			// separate infromation from server side
			String[] courselist = courses.split("=");
			for (int i = 1; i < courselist.length; i++) {
				String[] course = courselist[i].split(":");
				TableModel tableModel = table.getModel();
				tableModel.setValueAt(course[3] + course[4] + course[5], Integer.parseInt(course[1]), Integer.parseInt(course[2]));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public CourseTable(Socket socket) {
		this.socket = socket;
		String[] time = { "8:00~9:30", "9:55~11:30", "13:30~15:00", "15:25~17:00", "19:00~20:30" };
		for (int i = 0; i < 5; i++) {
			content[i][0] = time[i];
		}
		String[] day = { "Times", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

		DefaultTableModel tmd = new DefaultTableModel(content, day);

		table = new JTable(tmd) {
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		scrollPane.setViewportView(table);
		setTitle("course");
		setSize(800, 600);
		setLocation(500, 200);
		scrollPane.setSize(320, 200);

		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, r);
		table.setRowHeight(80);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		add(scrollPane);

		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				String instruction;
				if (e.getSource().equals(null))
					instruction = "insert";
				else
					instruction = "update";
				System.out.println(instruction);
				int row = table.getSelectedRow();
				int column = table.getSelectedColumn();
				new edit(socket, instruction, row, column, uID);// 接收user ID
				dispose();
			}
		});

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
		connect();
	}
}

