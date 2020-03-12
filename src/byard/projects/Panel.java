package byard.projects;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
/**
 * 
 * @author Isaac Byard
 * The JPanel responsible for all of the graphics and user interface
 *
 */
public class Panel extends JPanel
{
	private BufferedImage image; //buffer for lock image

	JPanel panel = new JPanel(); //instantiates the JPanel class
	Listener listener = new Listener(); //instantiates the private class Lister for the buttons
	ListListener lListener = new ListListener(); //instantiates the list Listener for the JList
	FileHandler file = new FileHandler(); //instantiates the fileHandler for manipulating the two files
	Encrypter encrypter = new Encrypter(); //instantiates the encrypter

	static Toolkit toolkit = Toolkit.getDefaultToolkit(); //not quite sure...
	static Clipboard clipboard = toolkit.getSystemClipboard(); //enables password to be copied to clipboard
	public static String dialogPass = ""; //not quite sure
	JList descriptionList = new JList(file.arrayFromDescriptionFile()); //instantiates the JList from the description file
	JScrollPane pane = new JScrollPane(descriptionList); //instantiates pane for list to be nested inside
	JTextField description = new JTextField("description");
	JTextField text = new JTextField("password"); //JFields for text input
	JButton addPassword = new JButton("Add Password"); //button to add password
	JButton addDescription = new JButton("Add Description"); //not sure
	static JButton delete = new JButton("Delete Password"); //button to delete selected password

	InputDialog adminPass = new InputDialog("Admin Password"); //The dialog box that prompts for the ADMIN password

	MListener mListener = new MListener();
	
	static String passwordMsg = "";
	static String password = "unchanged";
	String descriptionMsg = "";
	Color textColor = Color.LIGHT_GRAY;
	Color descriptionColor = Color.LIGHT_GRAY;
	/**
	 * Default Constructor
	 */
	Panel()
	{
		add(pane);
		add(text);
		add(addPassword);
		add(description);
		add(delete);
		pane.doLayout();
		delete.addActionListener(listener);
		delete.setActionCommand("delete");
		delete.setVisible(false); //make the delete button invisible until the admin is proven

		addPassword.addActionListener(listener);
		addPassword.setActionCommand("addPassword");
		descriptionList.addListSelectionListener(lListener);
		
		description.addMouseListener(mListener); //adds mouse interaction functionality to this JTextField
		text.addMouseListener(mListener); //adds mouse interaction functionality to this JTextField

		if (file.isEmpty())
		{
			description.setText("ADMIN"); //sets the description JTextField to "ADMIN" on first run
			text.setText("Admin Password");
		}
	       try {                
	           image = ImageIO.read(new File("Enc.png")); //load the encryption logo     
	        } catch (IOException ex) {}
	} //end default constructor

	/**
	 * paintComponent()
	 * Responsible for painting all the graphics for the JPanel
	 */
	public void paintComponent(Graphics pen)
	{
		Graphics2D g2d =  (Graphics2D) pen;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		pen.setColor(Color.WHITE);
		pen.fillRect(0, 0, Window.getWidth(), Window.getHeight());
		pen.drawImage(image,300, 221, null);
		pen.setColor(Color.BLACK);
		description.setForeground(descriptionColor);
		text.setForeground(textColor);
		text.setSize(200, 20);
		text.setLocation(250,120);
		description.setSize(200,20);
		description.setLocation(250,80);
		addPassword.setLocation(300, 180);
		addPassword.setBackground(Color.LIGHT_GRAY);
		delete.setBackground(Color.LIGHT_GRAY);
		delete.setLocation(293, 220);
		pen.setFont( new Font("font",Font.BOLD, 12 ) );
		pen.drawString(passwordMsg, 250, 400); //paints password!
		pen.setFont( new Font("font",Font.BOLD, 25 ) );
		pen.drawString(descriptionMsg, 250, 340);
		pane.setLocation(20,20);
		pane.setSize(200,Window.WINDOW_SIZE_Y-80);
		descriptionList.setSize(pane.getWidth(), pane.getHeight());
	}//end paint()
	
	/**
	 * Copy password to computer clipboard
	 * return void;
	 */
	static void passToClipboard()
	{
		StringSelection strSel = new StringSelection(password);
		clipboard.setContents(strSel, null);
		passwordMsg = "Password copied to clipboard";
		
	}
	
	///////////////////////////////
	//Begin subclasses////////////
	//////////////////////////////
	
	
	/**
	 * 
	 * @author Java Library and Isaac Byard
	 * The list listener for the JList
	 *
	 */
	protected class ListListener implements ListSelectionListener
	{
		
         public void valueChanged(ListSelectionEvent arg0) 
         {
        	 try
        	 {
	             if (!arg0.getValueIsAdjusting()) 
	             {
	            	int lineNum =  descriptionList.getAnchorSelectionIndex()+1;
	 				descriptionMsg = "" + file.readDescriptionLine(lineNum);
	 				adminPass.turnOn();
	 				password = encrypter.decrypter(file.readLine(lineNum));
	 				passwordMsg = "";
	 				delete.setVisible(false);
	 				descriptionList.setSelectedIndex(descriptionList.getAnchorSelectionIndex());
	 				repaint();
	             }
        	 }catch (Exception e) {}
         }
         /**
          * Returns with the selected list item
          * @return int line number
          */
         public int getLineNum()
         {
        	 return descriptionList.getAnchorSelectionIndex()+1;
         }        
     }//end class listListener
	

	
	/**
	 * 
	 * @author Java Library and Isaac Byard
	 * 
	 */
	protected class Listener implements ActionListener
	{
		ListListener lListener=new ListListener();
		public void actionPerformed(ActionEvent event) 
		{
			String action = event.getActionCommand();
			if (action.equals("addPassword")&&!description.getText().equals("")&&!text.getText().equals(""))//actions for the add password button
			{
			
				dialogPass = text.getText(); //password text
				file.encryptToFile(dialogPass); //encrypts password to file
				text.setText(""); //clears text box
				file.writeToDescriptionFile(description.getText()); //saves name
				descriptionList.clearSelection(); 
				
				description.setText(""); //clears text  box
				descriptionList.removeAll(); //refresh list
				descriptionList.setListData(file.arrayFromDescriptionFile()); //refresh
				descriptionList.repaint(); //refresh
				adminPass.turnOff();
			}
			if (action.equals("delete")&&lListener.getLineNum()!=1) //actions for the delete button. Cannot delete admin password
			{
				
				file.deletePassword(lListener.getLineNum());
				descriptionList.clearSelection();
				descriptionList.setListData(file.arrayFromDescriptionFile()); //refresh
				descriptionList.repaint(); //refresh
				descriptionMsg = "";
				passwordMsg = "";
				delete.setVisible(false);
				adminPass.turnOff();
				repaint();
			}


		}//end actionPerformed()
	}//end class ActionListener
	
	private class MListener implements MouseListener
	{

	    public void mouseClicked(MouseEvent e) 
	    {
	    	if (text.getText().equals("password")&&description.getText().equals("description"))
	    	{
	    	descriptionColor = Color.black;
			textColor = Color.black;
			text.setText("");
			description.setText("");
			repaint();
	    	}
	    	else if (text.getText().equals("Admin Password")&&description.getText().equals("ADMIN"))
	    	{
			textColor = Color.black;
			text.setText("");
			repaint();
	    	}
	    	else if (text.getText().equals("")||description.getText().equals(""))
	    	{
	    	descriptionColor = Color.black;
			textColor = Color.black;
			repaint();
	    	}
	    }
	    //unimplemented methods for potential future use
		public void mouseEntered(MouseEvent arg0) {}
		public void mouseExited(MouseEvent arg0) {}
		public void mousePressed(MouseEvent arg0) {}
		public void mouseReleased(MouseEvent arg0) {}			
		
	}
}//end class Panel
