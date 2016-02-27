/* 
  File:		AddContactDialog.java
  Author:	Ryan Schultz	
  Date:		2/10/2016

  Description: Creates, displays, contains functionality of Add Contact dialog box
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
Class:	AddContactDialog

Description:  Creates Add Contact dialog box and handles action events to add contact or cancel
*/
@SuppressWarnings("serial")
public class AddContactDialog extends JDialog {
	
	JPanel mainPanel = new JPanel(new BorderLayout());
	
	JPanel inputPanel = new JPanel(new GridLayout(5,0));
	JPanel nameInputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	JPanel emailInputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	JPanel phoneInputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	JPanel notesInputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
	JLabel nameLabel = new JLabel();
	JTextField nameTextField = new JTextField(20);
	JLabel emailLabel = new JLabel();
	JTextField emailTextField = new JTextField(20);
	JLabel phoneLabel = new JLabel();
	JTextField phoneTextField = new JTextField(20);
	JLabel notesLabel = new JLabel();
	JTextField notesTextField = new JTextField(20);
			
	JButton okB = new JButton();
	JButton cancelB = new JButton();
		
	public AddContactDialog(Frame frame) {
		super(frame, Local.getString("Add Contact"), true);
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

	  Description: Initializes AddContactDialog box
	*/
	void jbInit() throws Exception {
		this.setResizable(false);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		
		nameLabel.setText(Local.getString("Name") + ":  ");
		emailLabel.setText(Local.getString("Email") + ":  ");
		phoneLabel.setText(Local.getString("Phone") + ":  ");
		notesLabel.setText(Local.getString("Notes") + ":  ");		
		
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
        
        cancelB.setText(Local.getString("Cancel"));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setMaximumSize(new Dimension(100, 26));
        cancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelB_actionPerformed(e);
            }
        });
        
        nameInputPanel.add(nameLabel);
		nameInputPanel.add(nameTextField);
		emailInputPanel.add(emailLabel);
		emailInputPanel.add(emailTextField);
		phoneInputPanel.add(phoneLabel);
		phoneInputPanel.add(phoneTextField);
		notesInputPanel.add(notesLabel);
		notesInputPanel.add(notesTextField);
		buttonsPanel.add(okB);
        buttonsPanel.add(cancelB);
		
		inputPanel.add(nameInputPanel);
		inputPanel.add(emailInputPanel);
		inputPanel.add(phoneInputPanel);
		inputPanel.add(notesInputPanel);
		inputPanel.add(buttonsPanel);
        		
        mainPanel.add(inputPanel, BorderLayout.NORTH);
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
	}
	
	/**
	  Method:	okB_actionPerformed
	  @param:	N/A
	  @return: 	N/A

	  Description: Adds contact after validation - Ok button action event
	*/
    void okB_actionPerformed(ActionEvent e) {
    	String name = nameTextField.getText();
    	String email = emailTextField.getText();
    	String phone = phoneTextField.getText();
    	String notes = notesTextField.getText();
    	EmailContact contact = new EmailContact();
    	if (name != null && !name.isEmpty()) {
    		if (email != null && !name.isEmpty()) {
    			if (phone != null && !phone.isEmpty() && phone.matches("^-?\\d+$")) {
    				if (notes != null && !notes.isEmpty()) {
				    	if (contact.validateEmail(email)) {
				    		if (ContactList.getContact(name) == null) {
				    			ContactListStorage.addContactToList(new EmailContact(name,email,phone,notes));
				    			this.dispose();
				    			JOptionPane.showMessageDialog(null, name + " " + Local.getString("added to contacts!"), 
				    					"Successfully Added", JOptionPane.INFORMATION_MESSAGE);
				    		}
				    		else {
				    			JOptionPane.showMessageDialog(null, Local.getString("Contact already exists!"), 
				    					"Invalid Contact", JOptionPane.INFORMATION_MESSAGE);
				    			cancelB.transferFocus();
				    		}
				    	}
				    	else {
				    		JOptionPane.showMessageDialog(null, Local.getString("You entered an invalid email!") + 
				    				"\n" + "Example:  abcd@gmail.com", "Invalid Email", JOptionPane.INFORMATION_MESSAGE);
				    		nameTextField.transferFocus();
				    	}
    				}
    				else {
                		JOptionPane.showMessageDialog(null, Local.getString("You left Notes Blank!"), 
                				"Blank Notes Input", JOptionPane.INFORMATION_MESSAGE);
                		phoneTextField.transferFocus();
                		
                	}
    			}
    			else {
            		JOptionPane.showMessageDialog(null, Local.getString("You entered an invalid phone number!") + "\n" +
            				Local.getString("Please only enter numbers"), "Invalid Phone Input", JOptionPane.INFORMATION_MESSAGE);
            		emailTextField.transferFocus();
            	}
    		}
    		else {
        		JOptionPane.showMessageDialog(null, Local.getString("You left Email Blank!"), 
        				"Blank Email Input", JOptionPane.INFORMATION_MESSAGE);
        		nameTextField.transferFocus();
        	}
    	}
    	else {
    		JOptionPane.showMessageDialog(null, Local.getString("You left Name Blank!"), 
    				"Blank Name Input", JOptionPane.INFORMATION_MESSAGE);
    		cancelB.transferFocus();
    	}
    }
    
    /**
	  Method:	cancelB_actionPerformed
	  @param:	N/A
	  @return: 	N/A

	  Description: Closes Add Contact dialog box - Cancel Button action
	*/
    void cancelB_actionPerformed(ActionEvent e) {
        this.dispose();
    }
}