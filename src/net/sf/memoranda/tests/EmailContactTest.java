package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.EmailContact;


public class EmailContactTest {
	private static EmailContact testContact1 = null;
	private static EmailContact testContact2 = null;
	private static EmailContact testContact3 = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		testContact1 = new EmailContact("NameTest1", "EmailTest1", "1111111111", "NotesTest1");
		testContact2 = new EmailContact();
		testContact3 = new EmailContact("NameTest3","testemail@gmail.com", "password");
	}

	@Test
	public void testGetters() {
		assertEquals("NameTest1", testContact1.getName());
		assertEquals("EmailTest1", testContact1.getEmail());
		assertEquals("1111111111", testContact1.getPhone());
		assertEquals("NotesTest1", testContact1.getNotes());
	}
	
	@org.junit.Test
	public void testSetters() {
		testContact2.setName("NameTest2");
		testContact2.setEmail("EmailTest2");
		testContact2.setPhone("2222222222");
		testContact2.setNotes("NotesTest2");
		assertEquals("NameTest2", testContact2.getName());
		assertEquals("EmailTest2", testContact2.getEmail());
		assertEquals("2222222222", testContact2.getPhone());
		assertEquals("NotesTest2", testContact2.getNotes());
	}
	
	@org.junit.Test
	public void testEmailValidation() {
		assertTrue(testContact3.validateEmail(testContact3.getEmail()));
		testContact3.setEmail("testemail@yahoo.com");
		assertTrue(testContact3.validateEmail(testContact3.getEmail()));
		testContact3.setEmail("testemail2yahoo.com");
		assertFalse(testContact3.validateEmail(testContact3.getEmail()));
		testContact3.setEmail("testemail");
		assertFalse(testContact3.validateEmail(testContact3.getEmail()));	
	}

}
