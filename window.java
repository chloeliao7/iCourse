package connect;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class window extends JComponent{
 public static void main(String[] args){
	 JFrame window=new JFrame();
	 window.setSize(400,300);
	 window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
	 JLabel label = new JLabel("user has been saved!",SwingConstants.CENTER);
	 label.setFont(new java.awt.Font("Dialog", 1, 25));
	 window.add(label);
     window.setVisible(true);
  }
}
