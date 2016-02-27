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
	
	private String senderEmail;
	private String senderPassword;
	private String recipient;
	private String subject;
	private String message;
	
	
	public Emailer (String senderEm, String senderPwd, String rcpt, String sub, String msg) {
		senderEmail = senderEm;
		senderPassword = senderPwd;
		recipient = rcpt;
		subject = sub;
		message = msg;
	}
	
	/**
	  Method:	sendEmail
	  @param:	N/A
	  @return: 	N/A

	  Description: Sends email to recipient
	*/
	public boolean sendEmail() {
		Email email = new SimpleEmail();
		email.setHostName("smtp.googlemail.com");
		email.setSmtpPort(465);
		email.setAuthenticator(new DefaultAuthenticator(senderEmail, senderPassword));
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