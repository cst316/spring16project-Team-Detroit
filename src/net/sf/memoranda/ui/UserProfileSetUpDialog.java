/* 
  File:		UserProfileSetUpDialog.java
  Author:	Ryan Schultz	
  Date:		2/8/2016

  Description: Creates, displays, contains functionality of Set Up User Profile dialog box
*/
package net.sf.memoranda.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

import net.sf.memoranda.AdminEmail;
import net.sf.memoranda.EmailContact;
import net.sf.memoranda.util.ContactList;
import net.sf.memoranda.util.ContactListStorage;
import net.sf.memoranda.util.Local;

/**
Class:	UserProfileSetUpDialog

Description:  Creates User Profile Set Up dialog box and handles action events to set up/edit user profile or cancel
*/
@SuppressWarnings("serial")
public class UserProfileSetUpDialog extends JDialog {
	
	JPanel mainPanel = new JPanel(new BorderLayout());
	JPanel inputPanel = new JPanel(new GridLayout(3,0));
	JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
	JPanel emailPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
	JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
	JLabel nameLabel = new JLabel();
	JTextField nameTextField = new JTextField(20);
	JLabel emailLabel = new JLabel();
	JTextField emailTextField = new JTextField(20);
	JLabel passwordLabel = new JLabel();
	JTextField passwordTextField = new JTextField(20);
		
	JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	JButton okB = new JButton();
	JButton cancelB = new JButton();
		
	public UserProfileSetUpDialog(Frame frame) {
		super(frame, Local.getString("Profile Set Up"), true);
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

	  Description: Initializes UserProfileSetUpDialog box
	*/
	void jbInit() throws Exception {
		this.setResizable(false);
		nameLabel.setText(Local.getString("Name") + ":  ");
		emailLabel.setText(Local.getString("Email (Gmail Only)") + ":  ");
		passwordLabel.setText(Local.getString("Password") +":  ");
		
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		namePanel.add(nameLabel);
		namePanel.add(nameTextField);
		emailPanel.add(emailLabel);
		emailPanel.add(emailTextField);
		passwordPanel.add(passwordLabel);
		passwordPanel.add(passwordTextField);
		
		inputPanel.add(namePanel);
		inputPanel.add(emailPanel);
		inputPanel.add(passwordPanel);
		
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
    	String password = passwordTextField.getText();
    	EmailContact user = new EmailContact();// = new EmailContact(email);
    	if (name != null && !name.isEmpty()) {
    		if (user.validateEmail(email) && email != null && !email.isEmpty()) { 
    			if (password != null && !name.isEmpty()) {
    				AdminEmail testEmail = new AdminEmail(email, password, email);
    				boolean testSend = AdminEmail.sendEmail();   				
			    		if (testSend && ContactList.getContact("User") == null) {
			    			ContactListStorage.addUserToList(new EmailContact(name, email, password));
			    			this.dispose();
			    			JOptionPane.showMessageDialog(null,Local.getString("User Profile Updated!"), 
			    					"Successful Update", JOptionPane.INFORMATION_MESSAGE);
			    		}
			    		else if (testSend && ContactList.getContact("User") != null) {
			    			ContactListStorage.removeUser();
			    			ContactListStorage.addUserToList(new EmailContact(name, email, password));
			    			this.dispose();
			    			JOptionPane.showMessageDialog(null,Local.getString("User Profile Updated!"), 
			    					"Successful Update", JOptionPane.INFORMATION_MESSAGE);
			    		}
			    		else {
			    	    	JOptionPane.showMessageDialog(null, "You entered an invalid email and or/password!" + "\n" + "Example:  abcd@gmail.com", "Invalid Email", JOptionPane.INFORMATION_MESSAGE);
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