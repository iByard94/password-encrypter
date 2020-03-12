package byard.projects;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.border.EmptyBorder;
/**
 * 
 * @author Isaac Byard
 * This class is responsible for prompting
 * the user for the admin password.
 *
 */
public class InputDialog extends JDialog
{
	JDialog inputDialog = new JDialog();
	Listener listener = new Listener();
	FileHandler file = new FileHandler();
	Encrypter encrypter = new Encrypter();
	
	JPasswordField text = new JPasswordField(10);
    JButton okButton = new JButton("OK");
    
    /**
     * Parameterized Constructor
     * @param title
     */
	InputDialog(String title)
	{
		setTitle(title);
        setBounds(100, 100, 250, 100);
        setAlwaysOnTop(true);
        setFocusable(true);
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(panel, BorderLayout.CENTER);
        JPanel buttonPane = new JPanel();
        buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
        okButton.setActionCommand("ok");
        okButton.addActionListener(listener);
        buttonPane.add(okButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setActionCommand("cancel");
        cancelButton.addActionListener(listener);
        buttonPane.add(cancelButton);
        getContentPane().add(buttonPane, BorderLayout.SOUTH);   
        getContentPane().add(text, BorderLayout.NORTH);
        text.setBounds(20, 20, 100, 20);

        pack(); //makes window a nice size
        setBounds(100, 100, 180, 100);
        text.requestFocusInWindow();
        }//end parameterized constructor
	
	/**
	 * simple makes visible
	 */
	void turnOn()
	{
		setVisible(true);
	}
	
	/**
	 * make invisible
	 */
	void turnOff()
	{
		setVisible(false);
	}
	
	/**
	 * @return the decrypted password
	 */
	String getText()
	{
		return text.getText();
	}
	/**
	 * Class Listener
	 * @author Java Libraries and Isaac Byard
	 * The action listeners for the two buttons on the prompt
	 */
	public class Listener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent event) 
		{
			String action = event.getActionCommand();
			if (action.equals("cancel"))
			{
				turnOff();
			}
			if (action.equals("ok"))
			{
				Panel.dialogPass = getText();
				
				if ( text.getText().equals(encrypter.decrypter(file.readLine(1))) )
				{
				Panel.passToClipboard();
				Panel.delete.setVisible(true);
				turnOff();
				}
				text.setText("");
		        text.requestFocusInWindow();
			}//end if

		}//end actionPerformed()
	}//end Listener class

}//end class
