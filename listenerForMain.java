package Client;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class listenerForMain implements MouseListener {

	Main a;

	public listenerForMain(Main window) {
		a = window;
		//add a listener for window 
	}

	@Override
	/*another window will appear 
	 * when users press and release the mouse button*/
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == a.Loging) {
			new Loging();
			a.dispose();
			/*when user click loging 
			 * the loging window will appear 
			 *and when user finished the window will close*/
		}
		if (e.getSource() == a.SignUp) {
			new Sign();
			a.dispose();
			/*when user click signup 
			 * the sign window will qppear 
			 * and when user finished the window will close*/
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
