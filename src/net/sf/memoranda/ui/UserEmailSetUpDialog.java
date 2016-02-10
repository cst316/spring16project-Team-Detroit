package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import javax.swing.*;

import net.sf.memoranda.EmailContact;
import net.sf.memoranda.util.ContactList;
import net.sf.memoranda.util.Local;

public class UserEmailSetUpDialog extends JDialog {
	
	JPanel mainPanel = new JPanel(new BorderLayout());
	
	JPanel inputPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));	
	JLabel emailLabel = new JLabel();
	JTextField emailTextField = new JTextField(20);
	
	JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	JButton okB = new JButton();
	JButton cancelB = new JButton("Cancel");
		
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
		
	void jbInit() throws Exception {
		this.setResizable(false);
		emailLabel.setText("Email:  ");
		
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		inputPanel.add(emailLabel);
		inputPanel.add(emailTextField);
		
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
	
	@SuppressWarnings("static-access")
	// ok button action
    void okB_actionPerformed(ActionEvent e) {
    	//  ADDED FOR CONFIG STUFF
    	ContactList contactList = new ContactList();    	    	
    	String email = emailTextField.getText();
    	EmailContact contact = new EmailContact();// = new EmailContact(email);
    	if (contact.validateEmail(email) && email != null && !email.isEmpty()) {
    		if (contactList.get("USER").equals("")) {
    			contactList.put("USER", contact = new EmailContact(email));
    			this.dispose();
    			JOptionPane.showMessageDialog(null, "User Email Set Up!", "Successful Setup", JOptionPane.INFORMATION_MESSAGE);
    		}
    		else {
    			this.dispose();
    			JOptionPane.showMessageDialog(null, "User email already exists!", "Invalid Email", JOptionPane.INFORMATION_MESSAGE);
    		}
    	}
    	else {
    		JOptionPane.showMessageDialog(null, "You entered an invalid email!" + "\n" + "Example:  abcd@gmail.com", "Invalid Email", JOptionPane.INFORMATION_MESSAGE);
    	}
    }
    
  //cancel button action
    void cancelB_actionPerformed(ActionEvent e) {
        //CANCELLED = true;
        this.dispose();
    }
}