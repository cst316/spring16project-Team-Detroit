/* 
  File:		UserEmailSetUpDialog.java
  Author:	Ryan Schultz	
  Date:		2/8/2016

  Description: Creates, displays, contains functionality of Set Up User Email dialog box
*/
package net.sf.memoranda.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

import net.sf.memoranda.EmailContact;
import net.sf.memoranda.util.ContactList;
import net.sf.memoranda.util.ContactListStorage;
import net.sf.memoranda.util.Local;

/**
Class:	UserEmailSetUpDialog

Description:  Creates User Email Set Up dialog box and handles action events to set up user email or cancel
*/
public class UserEmailSetUpDialog extends JDialog {
	
	JPanel mainPanel = new JPanel(new BorderLayout());
	JPanel inputPanel = new JPanel(new GridLayout(2,0));
	JPanel inputPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	JPanel inputPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));	
	JLabel nameLabel = new JLabel();
	JTextField nameTextField = new JTextField(20);
	JLabel emailLabel = new JLabel();
	JTextField emailTextField = new JTextField(20);
	
	JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	JButton okB = new JButton();
	JButton cancelB = new JButton();
		
	public UserEmailSetUpDialog(Frame frame) {
		super(frame, Local.getString("Email Set Up"), true);
		try {
	           jbInit();
	           pack();
	       }
	       catch (Exception ex) {
	           new ExceptionDialog(ex);
	       }
	}
	
	/**
	  Method:	jbInit
	  @param:	N/A
	  @return: 	N/A

	  Description: Initializes UserEmailSetUpDialog box
	*/
	void jbInit() throws Exception {
		this.setResizable(false);
		nameLabel.setText(Local.getString("Name") + ":  ");
		emailLabel.setText(Local.getString("Email") + ":  ");
		
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		inputPanel1.add(nameLabel);
		inputPanel1.add(nameTextField);
		inputPanel2.add(emailLabel);
		inputPanel2.add(emailTextField);
		
		inputPanel.add(inputPanel1);
		inputPanel.add(inputPanel2);
		
		okB.setMaximumSize(new Dimension(100, 26));
        okB.setMinimumSize(new Dimension(100, 26));
        okB.setPreferredSize(new Dimension(100, 26));
        okB.setText(Local.getString("Ok"));
        okB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                okB_actionPerformed(e);
            }
        });
        this.getRootPane().setDefaultButton(okB);
        cancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelB_actionPerformed(e);
            }
        });
        cancelB.setText(Local.getString("Cancel"));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setMaximumSize(new Dimension(100, 26));
        buttonsPanel.add(okB);
        buttonsPanel.add(cancelB);
		
        mainPanel.add(inputPanel, BorderLayout.NORTH);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
	}
	
	/**
	  Method:	okB_actionPerformed
	  @param:	N/A
	  @return: 	N/A

	  Description: Sets up user email after validation - Ok button action event
	*/
    void okB_actionPerformed(ActionEvent e) {		    	
    	String name = nameTextField.getText();
    	String email = emailTextField.getText();
    	EmailContact user = new EmailContact();// = new EmailContact(email);
    	if (name != null && !name.isEmpty()) {
    		if (user.validateEmail(email) && email != null && !email.isEmpty()) {    		
	    		if (ContactList.getContact("USER") == null) {
	    			//user = new EmailContact(name,email);
	    			ContactListStorage.addUserToList(new EmailContact(name,email));
	    			this.dispose();
	    			JOptionPane.showMessageDialog(null,Local.getString("User Email Set Up!"), 
	    					"Successful Setup", JOptionPane.INFORMATION_MESSAGE);
	    			//this.setVisible(true);
	    		}
	    		else { 
	    			this.dispose();
	    			JOptionPane.showMessageDialog(null, Local.getString("User email already exists!"), 
	    					"Invalid Email", JOptionPane.INFORMATION_MESSAGE);
	    		}
    		}
	    	else {
	    		JOptionPane.showMessageDialog(null, Local.getString("You entered an invalid email!") + 
	    				"\n" + Local.getString("Example") + ":  abcd@gmail.com", 
	    				"Invalid Email", JOptionPane.INFORMATION_MESSAGE);
	    		this.setVisible(true);
	    		//  Not best way to set focus - WindowListener and requestFocus and requestFocusInWindow not working
	    		nameTextField.transferFocus();
	    	}
    	}
		else {
			JOptionPane.showMessageDialog(null, Local.getString("Please enter a name!"), 
					"Name Not Entered", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(true);
			//  Not best way to set focus - WindowListener and requestFocus and requestFocusInWindow not working
			cancelB.transferFocus();			
		}
    }
    
    /**
	  Method:	cancelB_actionPerformed
	  @param:	N/A
	  @return: 	N/A

	  Description: Closes Set Up User Email dialog box - Cancel Button action
	*/
    void cancelB_actionPerformed(ActionEvent e) {
        this.dispose();
    }
}