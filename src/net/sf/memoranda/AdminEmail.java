/* 
  File:		DailyEmail.java
  Author:	Ryan Schultz	
  Date:		2/10/2016

  Description: Creates the daily email sent to the user showing their schedule of events for the next day.
*/

package net.sf.memoranda;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.*;

import net.sf.memoranda.date.CalendarDate;
import net.sf.memoranda.ui.AppFrame;
import net.sf.memoranda.util.ContactList;

/**
Class:	DailyEmail

Description:  Creates the daily email sent to the user showing their schedule of events for the next day.
              Sends email when program is closed.
*/
public class AdminEmail extends Emailer {
	
	private static final String ADMIN_EMAIL = "memorandasystem@gmail.com";
	private static final String ADMIN_EMAIL_PWD = "cst316project";
	private static final String SUBJECT = "Tomorrows Schedule";
	
	//  Creates daily event email
	public AdminEmail() {
		super(ADMIN_EMAIL, ADMIN_EMAIL_PWD, recipient(), SUBJECT, eventMessage());
	}
	
	//  Create test email for user profile set up
	public AdminEmail(String email, String pwd, String recipient) {
		super(email, pwd, recipient, "Test Email", "Testing email/password input");
	}
	
	//  Create progress email to be sent to supervisor
	public AdminEmail(String recipient, String task) {
		super(ADMIN_EMAIL, ADMIN_EMAIL_PWD, recipient, "Progress Report", progressMessage(task));
	}
	
	/**
	  Method:	N/A
	  @param:	N/A
	  @return: 	N/A

	  Description: Calls method to send email when program is closed
	*/
	static {
    	AppFrame.addExitListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	sendEmail();
            }
        });
	}
	
	/**
	  Method:	recipient
	  @param:	N/A
	  @return: 	User email address

	  Description: Gets and returns user email address
	*/
	private static String recipient() {
		return ContactList.getContact("User").getEmail();
	}
	
	/**
	  Method:	message
	  @param:	N/A
	  @return:	Formatted daily events email to be sent to user

	  Description:
	*/
	private static String eventMessage() {
		CalendarDate c = new CalendarDate();
		c = CalendarDate.tomorrow();				

		String evlist = "Dear " + ContactList.getContact("User").getName() + ",\n\n" + "Here is your schedule for tomorrow:\n\n"
		                        + new SimpleDateFormat("EEEE, MMMM, dd yyyy").format(tomorrow()) + "\n";
		                        

		for (Iterator<Event> it = EventsManager.getEventsForDate(c).iterator(); it.hasNext();) {
			net.sf.memoranda.Event ev = (net.sf.memoranda.Event)it.next();   
		    evlist += ev.getTimeString()+" - "+ev.getText()+"\n";
		}
		
		evlist += "\nFrom your friendly Memoranda Admin";
		return evlist;
	}
	
	/**
	  Method:	tomorrow
	  @param:	N/A
	  @return:	Tomorrows date

	  Description:  Returns tomorrows date to be used to get tomorrows events for email
	*/
	private static Date tomorrow() {
		Calendar calendar = Calendar.getInstance();
		@SuppressWarnings("unused")
		Date today = calendar.getTime();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date tomorrow = calendar.getTime();
		return tomorrow;		
	}
	
	/**
	  Method:	progressMessage
	  @param:	N/A
	  @return:	Formatted progress message to be sent to supervisors

	  Description: Creates and returns formatted progress message to be sent to supervisors
	*/
	private static String progressMessage(String task) {				
		String taskMessage = ContactList.getContact("User").getName() + " has completed the following task:  " + task;		                       
		return taskMessage;
	}
}