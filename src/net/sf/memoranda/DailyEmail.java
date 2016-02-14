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
public class DailyEmail extends Emailer {
	
	private static final String ADMIN_EMAIL = "memorandasystem@gmail.com";
	private static final String ADMIN_EMAIL_PWD = "cst316project";
	private static final String SUBJECT = "Tomorrows Schedule";
	
	public DailyEmail() {
		super(ADMIN_EMAIL, ADMIN_EMAIL_PWD, recipient(), SUBJECT, message());
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
		return ContactList.getContact("USER").getEmail();
	}
	
	/**
	  Method:	message
	  @param:	N/A
	  @return:	Formatted daily events email to be sent to user

	  Description:
	*/
	private static String message() {
		CalendarDate c = new CalendarDate();
		c = c.tomorrow();				

		String evlist = "Dear " + ContactList.getContact("USER").getName() + ",\n\n" + "Here is your schedule for tomorrow:\n\n"
		                        + new SimpleDateFormat("EEEE, MMMM, dd yyyy").format(tomorrow()) + "\n";
		                        

		for (Iterator it = EventsManager.getEventsForDate(c).iterator(); it.hasNext();) {
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
		Date today = calendar.getTime();
		calendar.add(Calendar.DAY_OF_YEAR, 1);
		Date tomorrow = calendar.getTime();
		return tomorrow;		
	}
}