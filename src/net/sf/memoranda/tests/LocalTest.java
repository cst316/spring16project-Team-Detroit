package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import junit.framework.Assert;
import net.sf.memoranda.util.Local;

public class LocalTest {
	Local testLocal = new Local();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void getStringFromEnglishAlwaysReturnsKey() {
		testLocal.setMessages("en");
		assertTrue(testLocal.getString("Test Random").equals("Test Random"));
		assertTrue(testLocal.getString("Another_Test 1234").equals("Another_Test 1234"));
	}
	@Test
	public void settingMessagesShouldSetFileIO() {
		testLocal.setMessages("es");
		assertTrue(testLocal.getString("Test Random").equals("Test Random - Requires Translation - Spanish"));
	}

}
