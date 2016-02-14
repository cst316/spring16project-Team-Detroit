/* 
  File:		Emailer.java
  Author:	Ryan Schultz	
  Date:		2/10/2016

  Description: Parent class to DailyEmail, sends email
*/

package net.sf.memoranda;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;
import org.apache.commons.mail.EmailException;

/**
Class:	Emailer

Description:  Parent class to DailyEmail, sends email to recipient
*/
public class Emailer {
	
	private static String senderEmail;
	private static String senderPwd;
	private static String recipient;
	private static String subject;
	private static String message;
	
	
	public Emailer (String senderEmail, String senderPwd, String recipient, String subject, String message) {
		Emailer.senderEmail = senderEmail;
		Emailer.senderPwd = senderPwd;
		Emailer.recipient = recipient;
		Emailer.subject = subject;
		Emailer.message = message;
	}
	
	/**
	  Method:	sendEmail
	  @param:	N/A
	  @return: 	N/A

	  Description: Sends email to recipient
	*/
	public static boolean sendEmail() {
		Email email = new SimpleEmail();
		email.setHostName("smtp.googlemail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator(senderEmail, senderPwd));
		email.setSSLOnConnect(true);
		try {
			email.setFrom(senderEmail);
			email.setSubject(subject);
			email.setMsg(message);
			email.addTo(recipient);
			System.out.println("[DEBUG] Sent email: " + recipient);
			email.send();
		}
		catch (EmailException e) {
			System.out.println(e);
			return false;
		}
		return true;
	}			
}
