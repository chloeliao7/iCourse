package version2;

import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import setting.account;


public class edit extends JFrame {
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
	public edit(int row,int column,int account_id) {
		this.account_id=account_id;
		this.row=row;
		this.column=column;
		
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

		listenerForEdit myaction = new listenerForEdit(this);
		confirm.addMouseListener(myaction);
		exit.addMouseListener(myaction);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}

}
