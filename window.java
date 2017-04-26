package connect;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;

public class window extends JComponent{
 public static void main(String[] args){
	 new window();
	 ////you can use this method in main operation
  }
 public window(){
	 JFrame window=new JFrame(); 
	    //creat a new window named window
	 window.setSize(300,150);
	    //set the length and width of the window
	 window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    //When the user clicks the closing button on the window the window close
	 JOptionPane.showMessageDialog(null, "You created a new account!");
	 JLabel label = new JLabel("user has been saved!",SwingConstants.CENTER);
	    //add this sentence to this window and make this sentence in the center of it
	 label.setFont(new java.awt.Font("Dialog", 1, 25));
	    //define the typeface ,size,shape of the words
	 window.add(label);
	    //add the sentence in the window so that it can show up
     window.setVisible(true);
        //Let the JFrame object display
 }
}


