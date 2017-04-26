package version1;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import version1.Course;

public class CourseTable extends JFrame {
	final JScrollPane scrollPane = new JScrollPane();
	JTable table; 
	   //To display and edit a regular two-dimensional unit table
	JButton save; 
	   //To define a buttton for save 
	Course courses[][]; 
	
	public CourseTable() {
		
		courses=new Course[6][7];
		for(int i=0;i<6;i++){
			for(int j=0;j<7;j++){
				courses[i][j]=new Course(j, j, " ");
			}
		}
		
		save=new JButton("save"); 
		   //name the button save
		save.setBounds(200, 450, 100, 80); 
		   //to set the coordinate and the height and width for the rectangle
		this.add(save); 
		   //add save and the rectangle
		
		/*to add the time of class and the week for the coursetable*/
		String[][] content = new String[][]{{"8:00~9:30"," "," "," "," "," "," "," "},{"9:55~11:30"," "," "," "," "," "," "," "},{"13:30~15:00"," "," "," "," "," "," "," "},{"15:25~17:00"," "," "," "," "," "," "," "},{"19:00~20:30"," "," "," "," "," "," "," "}};
		String[] day = { "Times","Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday" };

		DefaultTableModel tmd = new DefaultTableModel(content, day);
		   //To achieve the table data additions and deletions to change
		
		table = new JTable(tmd);

		scrollPane.setViewportView(table);
		   //Add scroll bar
		
		setTitle("course");
		   //named the table course
		setSize(800, 600);
		   //set the height and width of the window
		setLocation(500, 200);
		   //show the coordinate 
		scrollPane.setSize(320, 200);
		   //set the size of the scroll bar

		DefaultTableCellRenderer r = new DefaultTableCellRenderer();
		r.setHorizontalAlignment(JLabel.CENTER);
		   //make the word show in the center of window
		table.setDefaultRenderer(Object.class, r);
		   //Set renderer for table
		table.setRowHeight(80);
		   //define the row's height as 80
		table.setFillsViewportHeight(true);
		   //Create the scroll pane and add the table to it.
		scrollPane.setViewportView(table);
		add(scrollPane);
		
		/*add listener for the table*/
		listenerForTable myaction = new listenerForTable(this);
		table.getModel().addTableModelListener(myaction);
		save.addMouseListener(myaction);
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		   //When the user clicks a button ,close the window
		setVisible(true);
		   //Enable visualization of the table
	}
	
	/*Returns true if the cell at rowIndex and columnIndex is editable.
	  Otherwise, setValueAt on the cell will not change the value of that cell. */
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	public static void main(String[] args) {
		new CourseTable();
	}

}
