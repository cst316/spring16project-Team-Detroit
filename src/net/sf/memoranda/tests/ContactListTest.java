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
	
	//private ArrayList<EmailContact> listTest;
	private EmailContact userTest;
	private EmailContact contactTest;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {			
		userTest = new EmailContact("user", "testusercontactlist@gmail.com", "password");
		contactTest = new EmailContact("clTestName", "testconcontactlist@gmail.com", "1111111111", "notes contact list test");
		ContactListStorage.addUserToList(userTest);
		ContactListStorage.addContactToList(contactTest);
		
	}
	
	@Test
	public void testGetContact() {		
		assertEquals(userTest, ContactList.getContact("User"));
		assertEquals("testusercontactlist@gmail.com", ContactList.getContact("User").getEmail());
		assertEquals(null, ContactList.getContact("tester"));
	}

	@Test
	public void testGetEmail() {		
		assertEquals("testusercontactlist@gmail.com", ContactList.getEmail(userTest));
		assertEquals("testconcontactlist@gmail.com", ContactList.getEmail(contactTest));
	}
	
	@Test
	public void testGetKeys() {
		ArrayList<String> listTest2 = ContactList.getKeys();
		assertTrue(listTest2.contains("User"));
		assertTrue(listTest2.contains("clTestName"));
		assertEquals(2,listTest2.size());		
	}
	
	@Test
	public void testConstructorHashMapSetUp() {
		ArrayList<EmailContact> listTest = new ArrayList<EmailContact>();
		listTest.add(userTest);
		listTest.add(contactTest);
		new ContactList(listTest);		
	}
}
