/* 
  File:		PersonalEmail.java
  Author:	Ryan Schultz	
  Date:		2/25/2016

  Description: Creates personal email and calls to send email
*/

package net.sf.memoranda;

/**
Class:	PersonalEmail

Description:  Calls super to create email and to send it
*/
public class PersonalEmail extends Emailer {
	public PersonalEmail(String senderEmail, String senderPwd, String recipient, String subject, String message) {
		super(senderEmail, senderPwd, recipient, subject, message);
		Emailer.sendEmail();
	}
}
