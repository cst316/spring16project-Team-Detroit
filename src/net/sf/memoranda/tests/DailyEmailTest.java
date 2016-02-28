package net.sf.memoranda.tests;

import static org.junit.Assert.*;



import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.AdminEmail;
import net.sf.memoranda.EmailContact;
import net.sf.memoranda.util.ContactList;
import net.sf.memoranda.util.ContactListStorage;

public class DailyEmailTest {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		ContactListStorage.addUserToList(new EmailContact("user", "dailyEmailUnitTest@gmail.com", "password"));
		new AdminEmail();
	}

	@Test
	public void testRecipient() {
		// recipient is a private method, this tests the code inside the method
		assertEquals("dailyEmailUnitTest@gmail.com", ContactList.getContact("User").getEmail());
	}
}
