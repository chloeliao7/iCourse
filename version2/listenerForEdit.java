package version2;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import setting.users;

public class listenerForEdit implements MouseListener {
	edit a;
	
	public listenerForEdit(edit edit) {
		a = edit;
	}

	@Override
	public void mouseClicked(MouseEvent e) {

		if (e.getSource() == a.confirm) {
			////////// add the value to the course object
			int row = a.row;
			int column = a.column;

			if (a.name.getText() != null)
				users.accounts.get(a.account_id).courses[row][column].setName(a.name.getText());
			if (a.location.getText() != null)
				users.accounts.get(a.account_id).courses[row][column].setLocation(a.location.getText());
			if (a.teacher.getText() != null)
				users.accounts.get(a.account_id).courses[row][column].setLecturer(a.teacher.getText());

			new CourseTable(a.account_id);
		}
		if (e.getSource() == a.exit) {
			a.dispose();
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
