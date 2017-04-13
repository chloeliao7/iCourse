package version2;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import setting.users;

public class CourseTable extends JFrame {
	final JScrollPane scrollPane = new JScrollPane();
	JTable table;
	JButton save;
	String[][] content = new String[5][7];
	int account_id;
	
	public CourseTable(int account_id) {
		this.account_id=account_id;
		String[] time = { "8:00~9:30", "9:55~11:30", "13:30~15:00", "15:25~17:00", "19:00~20:30" };
		for (int i = 0; i < 5; i++) {
			content[i][0] = time[i];//// time not shown????????????
			for (int j = 0; j < 7; j++) {
				content[i][j] = users.accounts.get(account_id).courses[i][j].getName();
			}
		}

		save = new JButton("save");
		save.setBounds(200, 450, 100, 80);
		this.add(save);

		// String[][] content = new String[][] { { "8:00~9:30", " ", " ", " ", "
		// ", " ", " ", " " },
		// { "9:55~11:30", " ", " ", " ", " ", " ", " ", "" },
		// { "13:30~15:00", " ", " ", " ", " ", " ", " ", " " },
		// { "15:25~17:00", " ", "", " ", " ", " ", " ", " " },
		// { "19:00~20:30", " ", " ", " ", " ", " ", " ", " " } };
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

		listenerForTable myaction = new listenerForTable(this);
		table.addMouseListener(myaction);
		save.addMouseListener(myaction);

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setVisible(true);

	}
}
