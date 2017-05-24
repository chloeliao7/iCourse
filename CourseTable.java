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

public class CourseTable extends JFrame implements Runnable {
	final JScrollPane scrollPane = new JScrollPane();
	JTable table;
	//creat a form
	JButton save;
	//creat a button
	String[][] content = new String[5][7];
	private PrintWriter writer;
	private BufferedReader reader;
	private Socket socket;
	//set a socket

	private void connect() {
		try {
			socket = new Socket("localhost", 1978);
			// writer = new PrintWriter(socket.getOutputStream(), true);
			//  get the socket output stream to write data
			reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // 实例化BufferedReader对象
			new Thread().start();
		} catch (Exception e) {
			e.printStackTrace();
			/*The command line prints the location and reason
			for the error message in the program*/
		}
	}

	boolean isrun = true;

	@Override
	public void run() {
		System.out.println("  1");
		try {
			while (isrun) { 
				// 
				while (reader != null) {
					try {
						int row = reader.read();
						System.out.println(row + " ");
						int col = reader.read();
						String name = reader.readLine();
						String room = reader.readLine();
						String teacher = reader.readLine();
						content[row][col] = name + " " + room + " " + teacher;
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						/*The command line prints the location and reason
						for the error message in the program*/
					}
				}
				isrun = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			/*The command line prints the location and reason
			for the error message in the program*/
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

	public static void main(String[] args) {
		new CourseTable();
	}

	/*set the time and the date to class in the course table*/
	public CourseTable() {
		String[] time = { "8:00~9:30", "9:55~11:30", "13:30~15:00", "15:25~17:00", "19:00~20:30" };
		for (int i = 0; i < 5; i++) {
			content[i][0] = time[i];
		}
		String[] day = { "Times", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

		DefaultTableModel tmd = new DefaultTableModel(content, day);

		table = new JTable(tmd) {
			public boolean isCellEditable(int row, int column) {
				return false;
				//Change each cell in the table into modifiable
			}
		};

		scrollPane.setViewportView(table);
		//Add scroll bar
		setTitle("course");
		//set the title to the table
		setSize(800, 600);
		//set the size 
		setLocation(500, 200);
		//set the location
		scrollPane.setSize(320, 200);
		//set the size of the scroll bar

		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, r);
		table.setRowHeight(80);
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		add(scrollPane);

		/*add mouselistener to the table*/
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
				int row = table.getSelectedRow();
				int column = table.getSelectedColumn();
				new edit(row, column, 1);
				dispose();
				//realse the window
			}
		});

		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		/*Automatically hide and release the form 
		after calling any registered WindowListener object*/
		this.setVisible(true);
		//Sets the object of the this reference to be visible
		connect();
	}
}

