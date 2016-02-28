package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import net.sf.memoranda.PersonalEmail;

public class PersonalEmailTest {
	private String recipient;
	private String subject;
	private String message;

	@Test
	public void testFullInput() {		
		recipient = "testpersonalemail@gmail.com";
		subject = "TestSubject";
		message = "TestMessage";
		assertTrue(PersonalEmail.sendPE(new PersonalEmail("memorandasystem@gmail.com", "cst316project", recipient, subject, message)));		
	}
}
