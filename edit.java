package version2;

import java.awt.Point;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import setting.account;


public class edit extends JFrame {
	JLabel course_name;
	//set a form for course name
	JLabel classroom;
	//set a form for classroom
	JLabel lecturer;
	//set a form for lecturer
	JButton confirm;
	//set a button to confirm
	JButton exit;
	//set a button to exit
	JTextField name;
	//Allow edit single line user name text 
	JTextField location;
	//Allow edit single line location text 
	JTextField teacher;
	//Allow edit single line teacher text 
	
	/*int some values*/
	int account_id;
	int row;
	int column;
	public edit(int row,int column,int account_id) {
		this.account_id=account_id;
		this.row=row;
		this.column=column;
		
		this.setSize(400, 500);
		//set size for edit window
		this.setLayout(null);
		//Default layout style
		this.setLocation(new Point(600, 200));
		//set location for sign
		setTitle("edit");
		//set the title edit
		
		 /*creat some JLabel and set the size ,show the coordinate of the rectangle of each*/
		course_name = new JLabel("Course :");
		//display text course name
		course_name.setBounds(65, 100, 100, 30);
		//set the bound of the course name
		classroom = new JLabel("classroom:");
		//display text classroom
		classroom.setBounds(65, 150, 100, 30);
		//set the bound of the classroom
		lecturer = new JLabel("lecturer:");
		//display text lecturer
		lecturer.setBounds(65, 200, 100, 30);
		//set the bound of the lecturer
		confirm = new JButton("confirm");
		//display text confirm
		confirm.setBounds(225, 300, 80, 30);
		//set the bound of the confirm
		exit = new JButton("Cancel");
		//display text cancel
		exit.setBounds(95, 300, 80, 30);
		//set the bound of the cancel

		name = new JTextField();
		name.setBounds(140, 100, 120, 30);
		//Allow edit single line user name text
		location = new JTextField();
		location.setBounds(140, 150, 120, 30);
		//Allow edit single line location text
		teacher = new JTextField();
		teacher.setBounds(140, 200, 120, 30);
		//Allow edit single line teacher text

		this.add(course_name);
		this.add(classroom);
		this.add(lecturer);
		this.add(confirm);
		this.add(exit);
		this.add(name);
		this.add(location);
		this.add(teacher);

		//add listener
		listenerForEdit myaction = new listenerForEdit(this);
		confirm.addMouseListener(myaction);
		exit.addMouseListener(myaction);

		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//When the user clicks a button ,close the window
		this.setVisible(true);
		//Enable visualization of the table
	}

}
