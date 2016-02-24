package net.sf.memoranda;

import net.sf.memoranda.util.ContactList;

//////////INCOMPLETE CODE////////////////
public class PersonalEmail extends Emailer {
	public PersonalEmail(String senderEmail, String senderPwd, String subject, String message) {
		super(senderEmail, senderPwd, subject, "d", message);
	}
	
	public String getSender() {
		return ContactList.getContact("User").getEmail();
	}

}
