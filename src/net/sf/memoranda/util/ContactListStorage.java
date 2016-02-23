/* 
  File:		ContactListStorage.java
  Author:	Ryan Schultz	
  Date:		2/11/2016

  Description: Manages contact list as an array list for file storage using serialization
*/

package net.sf.memoranda.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

import net.sf.memoranda.EmailContact;
import net.sf.memoranda.ui.AppFrame;
import net.sf.memoranda.ui.ExceptionDialog;

/**
Class:	ContactListStorage

Description:  Creates hash map out of contact list and manages the created hash map
*/
public class ContactListStorage implements Serializable {
	
	private static final long serialVersionUID = 8363682447274136375L;
	
	static ArrayList<EmailContact> contactList = new ArrayList<EmailContact>();
		
	public static int getSize() {
		return contactList.size();
	}
   
	/**
	  Method:	addContactToList
	  @param:	Email contact object to be stored in list
	  @return: 	N/A

	  Description: Adds a contact to the contact list ArrayList
	*/
	public static void addContactToList(EmailContact ec) {
		contactList.add(ec);
		ContactList.addContactToMap(ec.getName().toUpperCase(Locale.forLanguageTag("en")) , ec);
	}
	
	/**
	  Method:	addUserToList
	  @param:	User email object to be stored in list
	  @return: 	N/A

	  Description: Adds user contact to the contact list ArrayList
	*/
	public static void addUserToList(EmailContact user) {
		contactList.add(user);
		ContactList.addUserToMap("USER", user);
	}
	
	/**
	  Method:	saveList
	  @param:	N/A
	  @return: 	N/A

	  Description: Calls SerializationUtil methods to save contact list ArrayList
	*/
	public static boolean saveList() {
		try {
			SerializationUtil.serilaizeList(contactList);
			System.out.println("[DEBUG] Save contacts list: " + getContactPath());
	    }
	    catch (Exception e) {
	    	new ExceptionDialog(e, "Failed to save a contactList file:<br>"+ getContactPath(), "");
	    	return false;
	    }
		return true;
	}
	
	/**
	  Method:	N/A
	  @param:	N/A
	  @return: 	N/A

	  Description: Calls method to save contact list when program closed, checks if contact list exits if so
	               loads the file, if not creates the file
	*/
	static {
    	AppFrame.addExitListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	saveList();
            }
        });
	    try {
	    	boolean check = new File(Util.getEnvDir() + "contacts" + File.separator, "contactsList.txt").exists();
	    	if (check) {
	    		contactList = SerializationUtil.deserializeList();
		    	System.out.println("Loaded from: " + getContactPath());
		    	@SuppressWarnings("unused")
				ContactList cl = new ContactList(contactList);
	    	}
	    	else {
	    		File f = new File(getContactPath());
		    	new File(f.getParent()).mkdirs();      
		    	/*DEBUG*/System.out.println("New contact list created: "+getContactPath());
		    	try {
		    		saveList();      
		    	}
		    	catch (Exception e2) {
		    		new ExceptionDialog(e2, "Failed to load default configuration from resources.", "");
		    	}
	    	}	    	
	    }
	    catch (Exception e) {      
	    	e.printStackTrace();
	    }
	}
  	
	/**
	  Method:	getContactPath
	  @param:	N/A
	  @return: 	Returns String representation of path to contact list stored text file

	  Description: Returns path to contact list
	*/
	static String getContactPath() {
	    String p = Util.getEnvDir() + "contacts" + File.separator + "contactsList.txt";
	    if (new File(p).exists()) {
	    	return p;
	    }           
	    return p;
	}
}