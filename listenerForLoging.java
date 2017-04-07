package Table;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import useless.CourseTableButton;

public class listenerForLoging implements ActionListener {

	Loging a;

	public listenerForLoging(Loging window) {
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