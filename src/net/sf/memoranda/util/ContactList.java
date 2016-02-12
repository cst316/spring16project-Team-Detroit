/* 
  File:		ContactList.java
  Author:	Ryan Schultz	
  Date:		2/11/2016

  Description: Manages contact list as a hash map
*/

package net.sf.memoranda.util;

import java.util.*;

import net.sf.memoranda.EmailContact;

/**
Class:	ContactList

Description:  Creates hash map out of contact list and manages the created hash map
*/
public class ContactList {
	
	static HashMap<String, EmailContact> contactList = new HashMap<String, EmailContact>();
	
	public ContactList(ArrayList<EmailContact> cl) {		
		if (cl.size() != 0) {
			contactList.put("USER", cl.get(0));
			for (int i=1; i < cl.size(); i++) {
			    contactList.put(cl.get(i).getName().toUpperCase(),cl.get(i));
			}
		}		
	}
	
	public ContactList() {		
	}
	
	public static EmailContact getContact(String key) {
		if (contactList.containsKey(key)) {
			return contactList.get(key);
		}
		return null;
	}
	
	public static String getEmail(EmailContact ec) {
		return ec.getEmail();		
	}
	
	/**
	  Method:	addContactToMap
	  @param:	Key value for object, Email contact object to be stored in map
	  @return: 	N/A

	  Description: Adds a contact to the contact list hash map
	*/
	public static void addContactToMap(String key, EmailContact ec) {
		contactList.put(key,ec);		
	}
	
	/**
	  Method:	addUserToMap
	  @param:	Key value for object, User email object to be stored in map
	  @return: 	N/A

	  Description: Adds user name and email to the contact list hash map
	*/
	public static void addUserToMap(String key, EmailContact ec) {
		contactList.put(key,ec);
	}
	
		
}