package Table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import useless.CourseTableButton;

public class listenerForSign implements ActionListener {

	Sign a;

	public listenerForSign(Sign window) {
		a = window;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == a.Enter) {
			new CourseTable();
		}
		if (e.getSource() == a.Cancel) {
			a.dispose();
		}
	}

}
