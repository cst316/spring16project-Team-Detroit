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
		for (Iterator<EmailContact> it = cl.iterator(); it.hasNext(); ) {		    
			EmailContact ec = it.next();
		    if (ec.getCredentials().equals("User")) {
		    	contactList.put("User", ec);
		    }
		    else {
		    	contactList.put(ec.getName(), ec);
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
	
	/**
	  Method:	removeUser
	  @param:	Key value for object, User email object to be stored in map
	  @return: 	N/A

	  Description: Removes user contact from the contact list hashmap
	*/
	public static void removeUser() {
		contactList.remove("User");
	}  
	
	/**
	  Method:	contains
	  @param:	Key value for object
	  @return: 	Returns true if map contains key, if not returns false

	  Description: Checks if contactList hashmap contains key
	*/
	public static boolean contains(String key) {
		if (contactList.containsKey(key)) {
			return true;
		}
		return false;
	} 
	
	/**
	  Method:	getKeys
	  @param:	N/A
	  @return: 	Returns array of contact names

	  Description: Returns array of contact names
	*/
	public static ArrayList<String> getKeys() {
		ArrayList<String> keys = new ArrayList<String>();
		for (String key : contactList.keySet()) {
		    keys.add(key);
		}
		return keys;
	} 
}