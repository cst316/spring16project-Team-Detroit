/* 
  File:		PersonalEmailDialog.java
  Author:	Ryan Schultz	
  Date:		2/25/2016

  Description: Email GUI
*/

package net.sf.memoranda.ui;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;

import net.sf.memoranda.EmailContact;
import net.sf.memoranda.PersonalEmail;
import net.sf.memoranda.util.ContactList;
import net.sf.memoranda.util.Local;

/**
Class:	PersonalEmailDialog

Description:  Creates Email GUI and gathers information to create email
*/
public class PersonalEmailDialog extends JDialog {
	private static final long serialVersionUID = -1313533403570455677L;
	
	public static JTextField toTextField = new JTextField(20);
	
	JPanel mainPanel = new JPanel(new BorderLayout());
	JPanel toSubjectWrapPanel = new JPanel(new GridLayout(2,0));
	JPanel messageWrapPanel = new JPanel(new GridLayout(1,0));
	JPanel toPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
	JPanel subjectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
	JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
		
	JLabel subjectLabel = new JLabel();
	JTextField subjectTextField = new JTextField(20);	
	JTextArea messageTextArea = new JTextArea();
	
	JButton toB = new JButton();
	JButton sendB = new JButton();
	JButton cancelB = new JButton();
		
	public PersonalEmailDialog(Frame frame) {
		super(frame, Local.getString("Email"), true);
		if (ContactList.getContact("User") == null) {
			JOptionPane.showMessageDialog(null, "You must set up user email first!", "Error", JOptionPane.INFORMATION_MESSAGE); 
			UserProfileSetUpDialog dlg = new UserProfileSetUpDialog(new Frame());
			dlg.pack();
	        dlg.setLocationRelativeTo(this);
	        dlg.setVisible(true);
		}		
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

	  Description: Initializes PersonalEmailDialog
	*/
	void jbInit() throws Exception {
		this.setResizable(false);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		subjectLabel.setText(Local.getString("Subject") + ":  ");
		toTextField.setText("");
		messageTextArea.setPreferredSize(new Dimension (500,300));
				
		toB.setMaximumSize(new Dimension(50, 20));
        toB.setMinimumSize(new Dimension(50, 20));
        toB.setPreferredSize(new Dimension(50, 20));
        toB.setText(Local.getString("To"));
        toB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                toB_actionPerformed(e);
            }
        });
        
        sendB.setMaximumSize(new Dimension(100, 26));
        sendB.setMinimumSize(new Dimension(100, 26));
        sendB.setPreferredSize(new Dimension(100, 26));
        sendB.setText(Local.getString("Send"));
        sendB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendB_actionPerformed(e);
            }
        });
        this.getRootPane().setDefaultButton(sendB);
        
        cancelB.setText(Local.getString("Cancel"));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setMaximumSize(new Dimension(100, 26));
        cancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelB_actionPerformed(e);
            }
        });
								
		toPanel.add(toB);
		toPanel.add(toTextField);
		subjectPanel.add(subjectLabel);
		subjectPanel.add(subjectTextField);
		messagePanel.add(messageTextArea);
		buttonsPanel.add(sendB);
        buttonsPanel.add(cancelB);
				
		toSubjectWrapPanel.add(toPanel);
		toSubjectWrapPanel.add(subjectPanel);
		messageWrapPanel.add(messagePanel);
				
        mainPanel.add(toSubjectWrapPanel, BorderLayout.NORTH);
        mainPanel.add(messageWrapPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);        
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
	}
	
	/**
	  Method:	sendB_actionPerformed
	  @param:	N/A
	  @return: 	N/A

	  Description: Creates email to be sent and validates user input
	*/
    void sendB_actionPerformed(ActionEvent e) {
    	String recipient = toTextField.getText();
    	String subject = subjectTextField.getText();
    	String message = messageTextArea.getText();
    	EmailContact ec = new EmailContact();
    	if (recipient != null && !recipient.isEmpty() && ec.validateEmail(recipient)) {   		
    		if (subject != null && !subject.isEmpty()) {
    			if (message == null || message.isEmpty()) {
    				message = "From: " + ContactList.getContact("User").getName();
    			}
    			@SuppressWarnings("unused")
				PersonalEmail pe = new PersonalEmail(ContactList.getContact("User").getEmail(),ContactList.getContact("User").getPassword(), recipient, subject, message);
    			this.dispose();
    		}
    		else {
    			if (JOptionPane.showConfirmDialog(null, "Do you want to send message without any subject?", "Send Message?", 
    					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
    				if (message == null || message.isEmpty()) {
        				message = "From: " + ContactList.getContact("User").getName();
        			}
    				@SuppressWarnings("unused")
					PersonalEmail pe = new PersonalEmail(ContactList.getContact("User").getEmail(),ContactList.getContact("User").getPassword(), recipient, subject, message);
    				this.dispose();
    			}
    			else {
    				toTextField.transferFocus();
    			}    			
    		}
    	}
    	else {
    		JOptionPane.showMessageDialog(null, Local.getString("You entered an invalid email!") + 
    				"\n" + "Example:  abcd@gmail.com", "Invalid Email", JOptionPane.INFORMATION_MESSAGE);
    		toB.transferFocus();
    	}    	    	
    }
    
    /**
	  Method:	toB_actionPerformed
	  @param:	N/A
	  @return: 	N/A

	  Description: Displays pop up box to search contacts
	*/
    void toB_actionPerformed(ActionEvent e) {	
    	EmailRecipientDialog peDlg = new EmailRecipientDialog(new JFrame());
    	peDlg.pack();
        peDlg.setLocationRelativeTo(this);
        peDlg.setVisible(true);   	
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