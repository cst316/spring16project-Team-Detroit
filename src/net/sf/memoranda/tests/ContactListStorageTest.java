package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.EmailContact;
import net.sf.memoranda.util.ContactList;
import net.sf.memoranda.util.ContactListStorage;
import net.sf.memoranda.util.Util;

public class ContactListStorageTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//deleteContactList();
	}
	
	@Test
	public void testGetSize() {	
		//  It won't pass in eclipse unless you uncomment deleteContactList above
		assertEquals(2, ContactListStorage.getSize());
	}
	
	@Test
	public void testAddContactToListAndAddUserToList() { 
		ContactListStorage.addUserToList(new EmailContact("User", "testuserclstorage@gmail.com", "password"));
		ContactListStorage.addContactToList(new EmailContact("clTestName", "testconclstorage@gmail.com", "1111111111", "notes contact list test")); 
		assertEquals("testuserclstorage@gmail.com", ContactList.getEmail(ContactList.getContact("User")));
		assertEquals("testconclstorage@gmail.com", ContactList.getEmail(ContactList.getContact("clTestName")));		
	}
	
	@Test
	public void testCreateFileAndSaveList() {				
		ContactListStorage.saveList();		
		File newContactFile = new File(Util.getEnvDir() + "contacts" + File.separator + "contactsList.txt");		
		assertTrue(newContactFile.exists());
	}
	
	@Test
	public void testRemoveUser() {	
		ContactListStorage.removeUser();
		assertFalse(ContactList.contains("User"));
	}

	
	/*TEST LOAD FILE BY COMMENTING OUT testCreateFile test and UNCOMMENTING testLoadFile test
	 * Also need to create contacts.txt file in correct path and comment out deleteContactList 
	 * in before class
	 * Only one test can run at a time as class only loads once to call functionality
	 */	
//	@Test
//	public void testLoadFile() {
//		new ContactListStorage();
//		File newContactFile = new File(Util.getEnvDir() + "contacts" + File.separator + "contactsList.txt");		
//		assertTrue(newContactFile.exists());
//	}
	
	static void deleteContactList() {
		String folder = Util.getEnvDir() + "contacts";
		File directory = new File(folder);
		Path path = Paths.get(Util.getEnvDir() + "contacts" + File.separator + "contactsList.txt");
		try {
			Files.deleteIfExists(path);
			if (directory.isDirectory()) {
				boolean delete = directory.delete();
				if(!delete) {
					System.out.println("List failed to delete");
				};
			}
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