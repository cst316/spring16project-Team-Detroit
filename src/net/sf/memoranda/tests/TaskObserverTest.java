package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.EmailContact;
import net.sf.memoranda.util.ContactListStorage;
import net.sf.memoranda.util.TaskObserver;
import net.sf.memoranda.util.TaskStatus;

public class TaskObserverTest {
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ContactListStorage.removeUser();
		ContactListStorageTest.deleteContactList();	
	}

	@Test
	public void testTaskCompletedObserverEmail() {
		ContactListStorage.addUserToList(new EmailContact("TestUser", "memorandasystem@gmail.com", "cst316project"));
		ContactListStorage.addContactToList(new EmailContact("TestSupervisor", "testtaskobs@gmail.com", "1111111111", "", "Supervisor"));
		TaskStatus tdd = new TaskStatus("Task");
		TaskObserver watcher = new TaskObserver("Task");
		tdd.addObserver(watcher);
		tdd.updatePercentage(100);
		assertTrue(watcher.getSentStatus());
	}
	
	@Test
	public void testTaskNotCompleted() {	
		ContactListStorage.addUserToList(new EmailContact("TestUser", "memorandasystem@gmail.com", "cst316project"));
		ContactListStorage.addContactToList(new EmailContact("TestSupervisor", "testtaskobs@gmail.com", "1111111111", "", "Supervisor"));
		TaskStatus tdd = new TaskStatus("Task");
		TaskObserver watcher = new TaskObserver("Task");
		tdd.addObserver(watcher);
		tdd.updatePercentage(80);
		assertFalse(watcher.getSentStatus());
	}
	
	// You can run locally but Travis will not accept
//	@Test
//	public void testTaskNoSetUp() {
//		TaskStatus tdd = new TaskStatus("Task");
//		TaskObserver watcher = new TaskObserver("Task");
//		tdd.addObserver(watcher);
//		tdd.updatePercentage(100);
//		assertFalse(watcher.getSentStatus());
//	}
	
//  Need to clear contact list first	
//	@Test
//	public void testTaskNoSupervisor() {
//		ContactListStorage.addUserToList(new EmailContact("TestUser", "memorandasystem@gmail.com", "cst316project"));
//		TaskStatus tdd = new TaskStatus("Task");
//		TaskObserver watcher = new TaskObserver("Task");
//		tdd.addObserver(watcher);
//		tdd.updatePercentage(100);
//		assertFalse(watcher.getSentStatus());
//	}
	
	// You can run locally but Travis will not accept
//	@Test
//	public void testTaskNoUser() {
//		ContactListStorage.removeUser();
//		TaskStatus tdd = new TaskStatus("Task");
//		TaskObserver watcher = new TaskObserver("Task");
//		tdd.addObserver(watcher);
//		tdd.updatePercentage(100);
//		assertFalse(watcher.getSentStatus());
//	}
	
	@Test
	public void testGetTask() {
		TaskStatus tdd = new TaskStatus("");
		tdd.setTask("TestGetTask");
		assertEquals("TestGetTask", tdd.getTask());
	}
	
}
