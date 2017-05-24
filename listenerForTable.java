package version2;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import version2.Course;


public class listenerForTable implements MouseListener {
	CourseTable a;

	public listenerForTable(CourseTable window) {
		a = window;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == a.save) {
			// // Incoming server
			System.out.println("save button correct");
			//System.out.println(a.user.courses[2][3].getName());
		}
		if (e.getSource() == a.table) {
			int row = a.table.getSelectedRow();
			int column = a.table.getSelectedColumn();	
			new edit(row,column,a.account_id);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
