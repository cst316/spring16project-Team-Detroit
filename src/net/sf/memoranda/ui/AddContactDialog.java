package net.sf.memoranda.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.sf.memoranda.EmailContact;
import net.sf.memoranda.util.ContactList;
import net.sf.memoranda.util.Local;

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
	
	@SuppressWarnings("static-access")
	// ok button action
    void okB_actionPerformed(ActionEvent e) {
    	//  ADDED FOR CONFIG STUFF
    	ContactList contactList = new ContactList();
    	String name = nameTextField.getText();
    	String email = emailTextField.getText();
    	String phone = phoneTextField.getText();
    	String notes = notesTextField.getText();
    	EmailContact contact = new EmailContact();// = new EmailContact(email);
    	if (name != null && !name.isEmpty()) {
    		if (email != null && !name.isEmpty()) {
    			if (phone != null && !phone.isEmpty()) {
    				if (phone != null && !phone.isEmpty()) {
				    	if (contact.validateEmail(email)) {
				    		if (contactList.get(name.toUpperCase()).equals("")) {
				    			contactList.put(name.toUpperCase(), new EmailContact(name,email,phone,notes));
				    			this.dispose();
				    			JOptionPane.showMessageDialog(null, name + " added to contacts!", "Successfully Added", JOptionPane.INFORMATION_MESSAGE);
				    		}
				    		else {
				    			JOptionPane.showMessageDialog(null, "Contact already exists!", "Invalid Contact", JOptionPane.INFORMATION_MESSAGE);
				    		}
				    	}
				    	else {
				    		JOptionPane.showMessageDialog(null, "You entered an invalid email!" + "\n" + "Example:  abcd@gmail.com", "Invalid Email", JOptionPane.INFORMATION_MESSAGE);
				    	}
    				}
    				else {
                		JOptionPane.showMessageDialog(null, "You left Notes Blank!", "Blank Notes Input", JOptionPane.INFORMATION_MESSAGE);
                	}
    			}
    			else {
            		JOptionPane.showMessageDialog(null, "You left Phone Blank!", "Blank Phone Input", JOptionPane.INFORMATION_MESSAGE);
            	}
    		}
    		else {
        		JOptionPane.showMessageDialog(null, "You left Email Blank!", "Blank Email Input", JOptionPane.INFORMATION_MESSAGE);
        	}
    	}
    	else {
    		JOptionPane.showMessageDialog(null, "You left Name Blank!", "Blank Name Input", JOptionPane.INFORMATION_MESSAGE);
    	}
    }
    
  //cancel button action
    void cancelB_actionPerformed(ActionEvent e) {
        //CANCELLED = true;
        this.dispose();
    }

}
