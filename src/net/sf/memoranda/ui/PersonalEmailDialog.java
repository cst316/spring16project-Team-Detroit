package net.sf.memoranda.ui;

import java.awt.*;
import java.awt.event.ActionEvent;

import javax.swing.*;

import net.sf.memoranda.util.Local;

public class PersonalEmailDialog extends JDialog {
	JPanel mainPanel = new JPanel(new BorderLayout());
	JPanel toSubjectWrapPanel = new JPanel(new GridLayout(2,0));
	JPanel messageWrapPanel = new JPanel(new GridLayout(1,0));
	JPanel toPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
	JPanel subjectPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
	JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));	
	
	JButton toB = new JButton();		
	JTextField toTextField = new JTextField(20);
	JLabel subjectLabel = new JLabel();
	JTextField subjectTextField = new JTextField(20);	
	JTextArea messageTextArea = new JTextArea();
	
	JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
	JButton sendB = new JButton();
	JButton cancelB = new JButton();
		
	public PersonalEmailDialog(Frame frame) {
		super(frame, Local.getString("Email"), true);
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
		
		toB.setMaximumSize(new Dimension(50, 20));
        toB.setMinimumSize(new Dimension(50, 20));
        toB.setPreferredSize(new Dimension(50, 20));
        toB.setText(Local.getString("To"));
        toB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //sendB_actionPerformed(e);
            }
        });
		
		subjectLabel.setText(Local.getString("Subject") + ":  ");
		messageTextArea.setPreferredSize(new Dimension (500,300));
		
		mainPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		toPanel.add(toB);
		toPanel.add(toTextField);
		subjectPanel.add(subjectLabel);
		subjectPanel.add(subjectTextField);
		messagePanel.add(messageTextArea);
		
		toSubjectWrapPanel.add(toPanel);
		toSubjectWrapPanel.add(subjectPanel);
		messageWrapPanel.add(messagePanel);
		
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
        cancelB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cancelB_actionPerformed(e);
            }
        });
        cancelB.setText(Local.getString("Cancel"));
        cancelB.setPreferredSize(new Dimension(100, 26));
        cancelB.setMinimumSize(new Dimension(100, 26));
        cancelB.setMaximumSize(new Dimension(100, 26));
        buttonsPanel.add(sendB);
        buttonsPanel.add(cancelB);
		
        mainPanel.add(toSubjectWrapPanel, BorderLayout.NORTH);
        mainPanel.add(messageWrapPanel, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);
		this.getContentPane().add(mainPanel, BorderLayout.CENTER);
	}
	
	/**
	  Method:	okB_actionPerformed
	  @param:	N/A
	  @return: 	N/A

	  Description: Sets up user email after validation - Ok button action event
	*/
    void sendB_actionPerformed(ActionEvent e) {		    	
    	
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