/* 
  File:		EmailRecipientDialog.java
  Author:	Ryan Schultz	
  Date:		2/25/2016

  Description: Manages dialog box for user to search contact list for email recipient
*/

package net.sf.memoranda.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.*;

import net.sf.memoranda.EmailContact;
import net.sf.memoranda.util.ContactList;
import net.sf.memoranda.util.Local;

/**
Class:	EmailRecipientDialog

Description:  Allows user to search contact list by name for email recipient
*/
public class EmailRecipientDialog extends JDialog {
	private static final long serialVersionUID = 4578634207512659215L;
	
	JPanel toMainPanel = new JPanel(new BorderLayout());
	JPanel toContactWrapPanel = new JPanel(new GridLayout(3,0));	
	JPanel contactSearchInputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	JPanel contactBoxPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
	JPanel contactSearchButtonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));	
	
	JLabel searchContactLabel = new JLabel();
	JTextField searchContactTextField = new JTextField(20);	
	JLabel contactBoxLabel = new JLabel();
	
	@SuppressWarnings("rawtypes")
	JComboBox contactBox;
	
	JButton searchB = new JButton();
	JButton cancelB = new JButton();
	
	public EmailRecipientDialog(Frame frame) {
		super(frame, Local.getString("Contact"), true);
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

	  Description: Initializes and displays EmailRecipientDialog 
	*/
	void jbInit() throws Exception {
		this.setResizable(false);				
    	toMainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    	
		searchContactLabel.setText(Local.getString("Search By Name") + ":  ");
		searchContactTextField.setToolTipText(Local.getString("Full Contact Name (Case Sensitive)"));
		contactBoxLabel.setText(Local.getString("Select Contact") + ":      ");
		
		ArrayList<String> contacts = ContactList.getKeys();
		Collections.sort(contacts, String.CASE_INSENSITIVE_ORDER);
		String[] contactKeys = contacts.toArray(new String[contacts.size()]);		
		contactBox = createComboBox(contactKeys);
					
		contactBox.addActionListener(new java.awt.event.ActionListener() {
		    public void actionPerformed(ActionEvent e) {		    	
		    	contactBox_actionPerformed(e);
		    }			
		});
		
		searchB.setMaximumSize(new Dimension(100, 26));
        searchB.setMinimumSize(new Dimension(100, 26));
        searchB.setPreferredSize(new Dimension(100, 26));
        searchB.setText(Local.getString("Search"));
        searchB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchB_actionPerformed(e);
            }
        });
        this.getRootPane().setDefaultButton(searchB);
        
        cancelB.setText(Local.getString("Cancel"));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setMaximumSize(new Dimension(100, 26));
        cancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelB_actionPerformed(e);
            }
        });
		
		contactSearchInputPanel.add(searchContactLabel);		
		contactSearchInputPanel.add(searchContactTextField);
		contactBoxPanel.add(contactBoxLabel);
		contactBoxPanel.add(contactBox);
		contactSearchButtonsPanel.add(searchB);
        contactSearchButtonsPanel.add(cancelB);
        		
		toContactWrapPanel.add(contactSearchInputPanel);		
		toContactWrapPanel.add(contactBoxPanel);        
        toContactWrapPanel.add(contactSearchButtonsPanel);
		
        toMainPanel.add(toContactWrapPanel, BorderLayout.NORTH);
		this.getContentPane().add(toMainPanel, BorderLayout.CENTER);
	}	
	
	/**
	  Method:	searchB_actionPerformed
	  @param:	N/A
	  @return: 	N/A

	  Description: Searches contact list with user input, adds email address of user entered name to email
	               recipient text field.
	*/
	void searchB_actionPerformed(ActionEvent e) {
		String recipientName = searchContactTextField.getText();
		if (recipientName != null && !recipientName.isEmpty()) {
			EmailContact ec = ContactList.getContact(recipientName);
			if (ec != null) {
				String recipientEmail = ContactList.getEmail(ec);
				PersonalEmailDialog.toTextField.setText(recipientEmail);
				PersonalEmailDialog.toTextField.transferFocus();
				this.dispose();
			}
			else {
				JOptionPane.showMessageDialog(null, Local.getString("Contact doesn't exist\nEnter Full Contact Name (Case Sensitive)"), 
						"Contact Error!", JOptionPane.INFORMATION_MESSAGE);
				this.setVisible(true);
				cancelB.transferFocus();				
			}
		}
		else {
			JOptionPane.showMessageDialog(null, Local.getString("Please enter a name!"), 
					"Name Not Entered", JOptionPane.INFORMATION_MESSAGE);
			this.setVisible(true);
			cancelB.transferFocus();
		}
	}
	
	/**
	  Method:	contactBox_actionPerformed
	  @param:	N/A
	  @return: 	N/A

	  Description: Uses contactBox to set email in email GUI
	*/
	void contactBox_actionPerformed(ActionEvent e) {
        String recipientName = (String)contactBox.getSelectedItem();
        EmailContact ec = ContactList.getContact(recipientName);
        PersonalEmailDialog.toTextField.setText(ContactList.getEmail(ec));
		PersonalEmailDialog.toTextField.transferFocus();
		this.dispose();
	}
		
	/**
	  Method:	cancelB_actionPerformed
	  @param:	N/A
	  @return: 	N/A

	  Description: Cancels search, closes dialog
	*/
	void cancelB_actionPerformed(ActionEvent e) {
		this.dispose();
	}
	
	/**
	  Method:	createComboBox
	  @param:	Array of contact names
	  @return: 	Combo box that holds contacts

	  Description: Creates and returns new Combo Box that holds contacts
	*/
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private JComboBox createComboBox(String[] contactKeys) {
		return new JComboBox(contactKeys);
	}
}
