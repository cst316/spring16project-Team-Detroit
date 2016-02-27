/* 
  File:		EmailContact.java
  Author:	Ryan Schultz	
  Date:		2/4/2016

  Description: Creates contacts
*/

package net.sf.memoranda;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
Class:	EmailContact

Description:  Creates contact with following attributes: name, email, phone, notes
*/
public class EmailContact implements Serializable {
	
	private static final long serialVersionUID = 7110740542273981533L;
	
	private String name;
	private String email;
	private String phone;
	private String notes;
	private String credentials;
	private String password;
	
	// Default Constructor
	public EmailContact() {		
	}
	
	// User constructor
	public EmailContact(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
		credentials = "User";
		phone = "";
		notes = "";	
		
	}
	
	// Normal contact constructor
	public EmailContact(String name, String email, String phone, String notes) {
		this.name = name;
		this.email = email;
		password = "";
		credentials = "";
		this.phone = phone;
		this.notes = notes;
	}
	
	// Getters/Setters
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNotes() {
		return notes;
	}	
	
	public void setCredentials(String credentials) {
		this.credentials = credentials;
	}
	
	public String getCredentials() {
		return credentials;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPassword() {
		return password;
	}
		
	/**
	  Method:	validateEmail
	  @param:	email - contacts email address
	  @return: 	true if valid email, false otherwise

	  Description: Validates email entered for contact creation is valid
	*/
	public boolean validateEmail(String email) {
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
}
