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
public class AddContactDialog extends JDialog {
	
	JPanel mainPanel = new JPanel(new BorderLayout());
	
	JPanel inputPanel = new JPanel(new GridLayout(4,0));
	JPanel inputPanel1 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	JPanel inputPanel2 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	JPanel inputPanel3 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	JPanel inputPanel4 = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
	JLabel nameLabel = new JLabel();
	JTextField nameTextField = new JTextField(20);
	JLabel emailLabel = new JLabel();
	JTextField emailTextField = new JTextField(20);
	JLabel phoneLabel = new JLabel();
	JTextField phoneTextField = new JTextField(20);
	JLabel notesLabel = new JLabel();
	JTextField notesTextField = new JTextField(20);
		
	JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	JButton okB = new JButton();
	JButton cancelB = new JButton("Cancel");
		
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
		nameLabel.setText("Name:  ");
		emailLabel.setText("Email:  ");
		phoneLabel.setText("Phone:  ");
		notesLabel.setText("Notes:  ");
		
		mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		inputPanel1.add(nameLabel);
		inputPanel1.add(nameTextField);
		inputPanel2.add(emailLabel);
		inputPanel2.add(emailTextField);
		inputPanel3.add(phoneLabel);
		inputPanel3.add(phoneTextField);
		inputPanel4.add(notesLabel);
		inputPanel4.add(notesTextField);
		
		inputPanel.add(inputPanel1);
		inputPanel.add(inputPanel2);
		inputPanel.add(inputPanel3);
		inputPanel.add(inputPanel4);
		
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

	  Description: Adds contact after validation - Ok button action event
	*/
    void okB_actionPerformed(ActionEvent e) {
    	String name = nameTextField.getText();
    	String email = emailTextField.getText();
    	String phone = phoneTextField.getText();
    	String notes = notesTextField.getText();
    	EmailContact contact = new EmailContact();// = new EmailContact(email);
    	if (name != null && !name.isEmpty()) {
    		if (email != null && !name.isEmpty()) {
    			if (phone != null && !phone.isEmpty() && phone.matches("^-?\\d+$")) {
    				if (notes != null && !notes.isEmpty()) {
				    	if (contact.validateEmail(email)) {
				    		if (ContactList.getContact(name.toUpperCase()) == null) {
				    			ContactListStorage.addContactToList(new EmailContact(name,email,phone,notes));
				    			this.dispose();
				    			JOptionPane.showMessageDialog(null, name + " added to contacts!", "Successfully Added", JOptionPane.INFORMATION_MESSAGE);
				    		}
				    		else {
				    			JOptionPane.showMessageDialog(null, "Contact already exists!", "Invalid Contact", JOptionPane.INFORMATION_MESSAGE);
				    			//  Not best way to set focus - WindowListener and requestFocus and requestFocusInWindow not working
				    			cancelB.transferFocus();
				    		}
				    	}
				    	else {
				    		JOptionPane.showMessageDialog(null, "You entered an invalid email!" + "\n" + "Example:  abcd@gmail.com", "Invalid Email", JOptionPane.INFORMATION_MESSAGE);
				    		//  Not best way to set focus - WindowListener and requestFocus and requestFocusInWindow not working
				    		nameTextField.transferFocus();
				    	}
    				}
    				else {
                		JOptionPane.showMessageDialog(null, "You left Notes Blank!", "Blank Notes Input", JOptionPane.INFORMATION_MESSAGE);
                		//  Not best way to set focus - WindowListener and requestFocus and requestFocusInWindow not working
                		phoneTextField.transferFocus();
                		
                	}
    			}
    			else {
            		JOptionPane.showMessageDialog(null, "You entered invalid Phone!\nPlease only enter numbers", "Invalid Phone Input", JOptionPane.INFORMATION_MESSAGE);
            		//  Not best way to set focus - WindowListener and requestFocus and requestFocusInWindow not working
            		emailTextField.transferFocus();
            	}
    		}
    		else {
        		JOptionPane.showMessageDialog(null, "You left Email Blank!", "Blank Email Input", JOptionPane.INFORMATION_MESSAGE);
        		//  Not best way to set focus - WindowListener and requestFocus and requestFocusInWindow not working
        		nameTextField.transferFocus();
        	}
    	}
    	else {
    		JOptionPane.showMessageDialog(null, "You left Name Blank!", "Blank Name Input", JOptionPane.INFORMATION_MESSAGE);
    		//  Not best way to set focus - WindowListener and requestFocus and requestFocusInWindow not working
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