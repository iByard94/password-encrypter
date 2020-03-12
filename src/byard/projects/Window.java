package byard.projects;

import java.awt.datatransfer.StringSelection;

import javax.swing.JFrame;
/**
 * 
 * @author Isaac Byard
 * Window class contains the JFrame object and acts as the application window for the program. 
 *
 */
public class Window 
{
	static JFrame window = new JFrame("ENCRYPTER");
	Panel panel = new Panel();
	static final int WINDOW_SIZE_X = 500; //window width is 500 pixels
	static final int WINDOW_SIZE_Y = 500; //windows height is 500 pixels
	StringSelection strSel = new StringSelection("");

	public Window()
	{
		window.add(panel);
		window.setSize(WINDOW_SIZE_X,WINDOW_SIZE_Y);
		window.setVisible(true);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);// disables resizing
	}
	/**
	 * getHeight()
	 * @return int the window's height
	 */
	 static int getHeight()
	{
		return window.getHeight();
	}
	
	 /**
	  * getWidth()
	  * @return int the window's width
	  */
	 static int getWidth()
	{
		return window.getWidth();
	}
}//end class
