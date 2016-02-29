package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import net.sf.memoranda.EmailContact;
import net.sf.memoranda.util.ContactList;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContactPanelTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		ContactList contactList = new ContactList();
	}

	@Test
	public void addContactTest() {
		EmailContact contact1 = new EmailContact("Bob", "bob@gmail.com", "1234567890", "my friend");
		ContactList.addContactToMap(contact1.getName(), contact1);
		assertTrue(ContactList.getContact(contact1.getName()) == contact1);
	}
	
	@Test
	public void deleteContactTest() {
		EmailContact contact1 = new EmailContact("Bob", "bob@gmail.com", "1234567890", "my friend");
		ContactList.removeContact(contact1);
		assertTrue(ContactList.getContact(contact1.getName()) == null);
	}

}
