/* 
  File:		TaskObserver.java
  Author:	Ryan Schultz	
  Date:		2/26/2016

  Description: Observer of tasks completion status
*/

package net.sf.memoranda.util;

import java.awt.Frame;
import java.util.*;
import javax.swing.JOptionPane;

import net.sf.memoranda.AdminEmail;
import net.sf.memoranda.EmailContact;
import net.sf.memoranda.ui.AddContactDialog;
import net.sf.memoranda.ui.UserProfileSetUpDialog;

/**
Class:	TaskObserver

Description:  Observes Task completion status. Create progress email to be sent to supervisors, calls for email send
*/
public class TaskObserver implements Observer {
	private String task;
	private boolean sent;	

	public TaskObserver(String t) {
		task = t;
	}
	
	/**
	  Method:	update
	  @param:	observable object, object observed
	  @return: 	N/A

	  Description: create and send email upon task completion observation
	*/
	@Override
	public void update(Observable o, Object arg) {		
		ArrayList<EmailContact> supervisorList = ContactList.getSupervisors();
		if (ContactList.getContact("User") != null && supervisorList.size() != 0) {
			for (Iterator<EmailContact> it = supervisorList.iterator(); it.hasNext(); ) {
				EmailContact ec = it.next();
				AdminEmail progressEmail = new AdminEmail(ec.getEmail(),task);
				AdminEmail.sendAE(progressEmail);
				sent = true;
			}			
		}
		else {
			if (ContactList.getContact("User") == null && supervisorList.size() == 0) {
				JOptionPane.showMessageDialog(null,Local.getString("To send progress emails to your supervisor(s)\n"
						+ "you must set up your user profile and add a supervisor"), 
						"Progress Emails", JOptionPane.INFORMATION_MESSAGE);
					
				UserProfileSetUpDialog dlg = new UserProfileSetUpDialog(new Frame());
			    dlg.setLocationRelativeTo(null);
			    dlg.setVisible(true);
			        
			    AddContactDialog cdlg = new AddContactDialog(new Frame());
			    cdlg.setLocationRelativeTo(null);
			    cdlg.setVisible(true);
			}
			else if (ContactList.getContact("User") != null && supervisorList.size() == 0) {
				JOptionPane.showMessageDialog(null,Local.getString("To send progress emails to your supervisor(s)\n"
						+ "you must add a supervisor"), 
						"Progress Emails", JOptionPane.INFORMATION_MESSAGE);
			        
			    AddContactDialog cdlg = new AddContactDialog(new Frame());
			    cdlg.setLocationRelativeTo(null);
			    cdlg.setVisible(true);	
			}
			else {
				JOptionPane.showMessageDialog(null,Local.getString("To send progress emails to your supervisor(s)\n"
						+ "you must set up your user profile"), 
						"Progress Emails", JOptionPane.INFORMATION_MESSAGE);
				UserProfileSetUpDialog dlg = new UserProfileSetUpDialog(new Frame());
		        dlg.setLocationRelativeTo(null);
		        dlg.setVisible(true);
			}
			
		} 		
	}
	
	public boolean getSentStatus() {
		return sent;
	}
}
