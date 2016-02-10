package net.sf.memoranda;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.memoranda.util.FileStorage;


public class EmailContact {
	private String name;
	private String email;
	private String phone;
	private String notes;
	
	public EmailContact(String email) {
		name = "User";
		this.email = email;
		phone = "";
		notes = "";		
	}
	
	public EmailContact(String name, String email, String phone, String notes) {
		this.name = name;
		this.email = email;
		this.phone = phone;
		this.notes = notes;
	}
	
	// ADDED FOR CONFIG
	public EmailContact() {		
	}
	
	public boolean validateEmail(String email) {
		String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}
	
	public String getName() {
		return name;
	}

}
