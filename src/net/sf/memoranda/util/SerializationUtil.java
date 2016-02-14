/* 
  File:		SerializationUtil.java
  Author:	Ryan Schultz	
  Date:		2/12/2016

  Description: Reads and Writes contact list ArrayList using serialization
*/

package net.sf.memoranda.util;

import java.io.*;
import java.util.ArrayList;

import net.sf.memoranda.EmailContact;

/**
Class:	SerializationUtil

Description:  Serializes and Deserializes contact list ArrayList for write/Read
*/
public class SerializationUtil {
	
	/**
	  Method:	seserializeList
	  @param:	N/A
	  @return: 	N/A

	  Description: Serializes contact list ArrayList and writes file
	*/
	public static boolean serilaizeList(ArrayList<EmailContact> cl) {
		try {
			FileOutputStream fileOut = new FileOutputStream(getContactPath());
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(cl);
            out.close();
            fileOut.close();
            return true;
        } 
		catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } 
		catch (IOException e) {
            e.printStackTrace();
            return false;
        }
	}
	
	/**
	  Method:	deserializeList
	  @param:	N/A
	  @return: 	Contact list ArrayList deserialized

	  Description: Deserializes contact list ArrayList
	*/
	@SuppressWarnings("unchecked")
	public static ArrayList<EmailContact> deserializeList() {
		try {
            FileInputStream fileIn = new FileInputStream(getContactPath());
            ObjectInputStream in = new ObjectInputStream(fileIn);
            ArrayList<EmailContact> cl = (ArrayList<EmailContact>) in.readObject(); 
            in.close();
            fileIn.close();
            return cl;
        } 
		catch (ClassNotFoundException e) {
            e.printStackTrace();
        } 
		catch (FileNotFoundException e) {
            e.printStackTrace();
        } 
		catch (IOException e) {
            e.printStackTrace();
        } 
		return null;
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