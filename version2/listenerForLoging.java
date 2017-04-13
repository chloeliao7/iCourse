package version2;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class listenerForLoging implements ActionListener {

	Loging a;

	public listenerForLoging(Loging window) {
		a = window;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == a.Enter) {
			String user_name=a.name.getText();
			String pin=a.pin.getText();
			///////在database中查询与username和pin 对应的账户添加到下面
			new CourseTable();
		}
		if (e.getSource() == a.Cancel) {
			a.dispose();
		}
	}
}