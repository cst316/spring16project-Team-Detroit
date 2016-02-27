package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import java.awt.Frame;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.AdminEmail;
import net.sf.memoranda.EmailContact;
import net.sf.memoranda.ui.UserProfileSetUpDialog;
import net.sf.memoranda.util.ContactList;
import net.sf.memoranda.util.ContactListStorage;

public class UserProfileTest {	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/* Creating profile requires the user to enter valid email credentials
	 * This is an email account set up as our admin email but we will use it for this test
	 */
	@Before
	public void setUp() throws Exception {
		//new UserProfileSetUpDialog(new Frame());
	}

	// Console will show the an email was sent from the admin email to memorandasystem@gmail.com
	@Test
	public void testCorrectProfileSetUp() {
		assertTrue(AdminEmail.sendAE(new AdminEmail("memorandasystem@gmail.com", "cst316project", "memorandasystem@gmail.com")));
		ContactListStorage.addUserToList(new EmailContact("TestName", "memorandasystem@gmail.com", "cst316project"));
		assertTrue(ContactList.contains("User")); 
	}
//	
////	/* Input (formatted email, name entered, password entered) is validated in UserProfileSetUpDialog 
////	 * but we will test incorrect credentials input of email/password input
////	 * 
////	 * Console displays error of incorrect email. Program would still operate with this error and
////	 * ask for correct user input.
////	 */
//	@Test
//	public void testIncorrectProfileSetUp() {
//		
//		assertFalse(AdminEmail.sendAE(new AdminEmail("memorandasystem@gmail.com", "cst316project11", "memorandasystem@gmail.com")));
//	}
//	
//	/* Originally user name is set as "TestName" when profile set up. This will edit the profile
//	 * and test it's function.
//	 */
//	@Test
//	public void testEditProfile() {
//		ContactListStorage.addUserToList(new EmailContact("TestName", "memorandasystem@gmail.com", "cst316project"));
//		assertTrue(AdminEmail.sendAE(new AdminEmail("memorandasystem@gmail.com", "cst316project", "memorandasystem@gmail.com")));
//		ContactListStorage.removeUser();
//		ContactListStorage.addUserToList(new EmailContact("TestNameEdit", "memorandasystem@gmail.com", "cst316project"));
//		assertEquals("TestNameEdit", ContactList.getContact("User").getName());		
//	}
}
