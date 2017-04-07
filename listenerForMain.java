package Table;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class listenerForMain implements MouseListener {

	Main a;

	public listenerForMain(Main window) {
		a = window;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == a.Loging) {
			new Loging();
			a.dispose();
		}
		if (e.getSource() == a.SignUp) {
			new Sign();
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
