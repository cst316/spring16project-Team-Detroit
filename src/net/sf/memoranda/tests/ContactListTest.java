package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import net.sf.memoranda.EmailContact;
import net.sf.memoranda.util.ContactList;
import net.sf.memoranda.util.ContactListStorage;

public class ContactListTest {
	
	private static ArrayList<EmailContact> listTest = null;
	private static EmailContact userTest = null;
	private static EmailContact contactTest = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {		
		listTest = new ArrayList<EmailContact>();
		userTest = new EmailContact("user", "testusercontactlist@gmail.com");
		contactTest = new EmailContact("clTestName", "testconcontactlist@gmail.com", "1111111111", "notes contact list test");
		ContactListStorage.addUserToList(userTest);
		ContactListStorage.addContactToList(contactTest);
		
	}
	
	@Test
	public void testGetContact() {		
		assertEquals(userTest, ContactList.getContact("USER"));
		assertEquals("testusercontactlist@gmail.com", ContactList.getContact("USER").getEmail());
		assertEquals(null, ContactList.getContact("tester"));
	}

	@Test
	public void testGetEmail() {		
		assertEquals("testusercontactlist@gmail.com", ContactList.getEmail(userTest));
		assertEquals("testconcontactlist@gmail.com", ContactList.getEmail(contactTest));
	}
	
	@Test
	public void testConstructorHashMapSetUp() {
		listTest.add(userTest);
		listTest.add(contactTest);
		@SuppressWarnings("unused")
		ContactList clTest = new ContactList(listTest);		
	}

}
