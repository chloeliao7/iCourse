package version1;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

public class listenerForTable implements MouseListener, TableModelListener {
	CourseTable a;

	public listenerForTable(CourseTable window) {
		a = window;
	}


	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		int row = e.getFirstRow();
		int column = e.getColumn();
		String content = (String) a.table.getValueAt(row, column);
		a.courses[row][column].content=content;
		System.out.println("test");
		System.out.println(a.courses[row][column].content);	
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==a.save){
			//传入服务器
			System.out.println("save button correct");
			//a.repaint();
			//a.table.repaint();
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
