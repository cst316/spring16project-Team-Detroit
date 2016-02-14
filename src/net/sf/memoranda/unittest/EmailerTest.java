package net.sf.memoranda.unittest;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import net.sf.memoranda.Emailer;

public class EmailerTest {
	
	private static Emailer emailerTest = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		emailerTest = new Emailer("memorandasystem@gmail.com", "cst316project", "emailerUnitTest@gmail.com", "Test Subject", "Test Message");
		
	}

	@Test
	public void testSendEmailPass() {
		assertTrue(Emailer.sendEmail());
	}
	
	@Test
	public void testSendEmailFail() {
		emailerTest = new Emailer("memorandasystem", "cst316project", "emailerUnitTest@gmail.com@gmail.com", "Test Subject", "Test Message");
		assertFalse(Emailer.sendEmail());
	}

}
