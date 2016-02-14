package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.EmailContact;
import net.sf.memoranda.util.ContactList;
import net.sf.memoranda.util.ContactListStorage;
import net.sf.memoranda.util.Util;

public class ContactListStorageTest {
	
	private static EmailContact userTest = null;
	private static EmailContact contactTest = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		userTest = new EmailContact("user", "testuserclstorage@gmail.com");
		contactTest = new EmailContact("clTestName", "testconclstorage@gmail.com", "1111111111", "notes contact list test");	
	}

	@Test
	public void testGetSize() {		
		deleteContactList();
		ContactListStorage.addUserToList(userTest);
		ContactListStorage.addContactToList(contactTest);
		assertEquals(2, ContactListStorage.getSize());
	}
	
	@Test
	public void testAddContactToListAndAddUserToList() {
		//  Two contacts already added from testGetSize test 
		assertEquals("testuserclstorage@gmail.com", ContactList.getEmail(userTest));
		assertEquals("testconclstorage@gmail.com", ContactList.getEmail(contactTest));		
	}
	
	@Test
	public void testCreateFileAndSaveList() {
		deleteContactList();
				
		ContactListStorage.saveList();
		
		File newContactFile = new File(Util.getEnvDir() + "contacts" + File.separator + "contactsList.txt");
		
		assertTrue(newContactFile.exists());
	}
	
	/*TEST LOAD FILE BY COMMENTING OUT testCreateFile test and UNCOMMENTING testLoadFile test
	 * Only one test can run at a time as class only loads once to call functionality
	 */
	
//	@Test
//	public void testLoadFile() {
//		System.out.println("Load");
//		//deleteContactList();
//		ContactListStorage cls2 = new ContactListStorage();
//		File newContactFile = new File(Util.getEnvDir() + "contacts" + File.separator + "contactsList.txt");		
//		assertTrue(newContactFile.exists());
//	}
//	
	static void deleteContactList() {
		Path path = Paths.get(Util.getEnvDir() + "contacts" + File.separator + "contactsList.txt");
		try {
			Files.deleteIfExists(path);
		} 
		catch (NoSuchFileException e) {
			e.printStackTrace();
		} 
		catch (DirectoryNotEmptyException e) {
			e.printStackTrace();		
		} 
		catch (IOException e) {
			System.err.println(e);
		}
	}	
}
