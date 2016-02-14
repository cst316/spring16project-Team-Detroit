package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.util.Configuration;
import net.sf.memoranda.util.Local;

//Write tests for setMessages and sample of methods that may be
//affected [getString(), getWeekdayNames()]
public class LocalTest {
	Local testLocal = new Local();
	
	@SuppressWarnings("static-access")
	@Test
	public void getStringFromEnglishAlwaysReturnsKey() {
		testLocal.setMessages("en");
		assertTrue(testLocal.getString("Test Random").equals("Test Random"));
		assertTrue(testLocal.getString("Another_Test 1234").equals("Another_Test 1234"));
	}
	@SuppressWarnings("static-access")
	@Test
	public void settingMessagesShouldSetFileIO() {
		testLocal.setMessages("es");
		assertTrue(testLocal.getString("Test Random").equals("Test Random - Requires Translation - Spanish"));
		
		testLocal.setMessages("de");
		assertTrue(testLocal.getString("Test Random").equals("Test Random - Requires Translation - German"));
		
	}

	@SuppressWarnings("static-access")
	@Test
	public void settingMessagesShouldSetCurrentLocale() {
		testLocal.setMessages("es");
		assertTrue(testLocal.getCurrentLocale().equals(Locale.forLanguageTag("es")));
		
		testLocal.setMessages("de");
		assertTrue(testLocal.getCurrentLocale().equals(Locale.forLanguageTag("de")));
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void weekdayNamesShouldReturnTranslatedAbbreviations() {
		testLocal.setMessages("en");
		String[] weekdayName = testLocal.getWeekdayNames();
		if (Configuration.get("FIRST_DAY_OF_WEEK").equals("mon")) {
			assertTrue(weekdayName[0].equals("Mon"));
			assertTrue(weekdayName[4].equals("Fri"));
			assertTrue(weekdayName[6].equals("Sun"));
			assertFalse(weekdayName[1].equals("Wed"));
		} else {
			assertTrue(weekdayName[0].equals("Sun"));
			assertTrue(weekdayName[4].equals("Thu"));
			assertTrue(weekdayName[6].equals("Sat"));
			assertFalse(weekdayName[1].equals("Tue"));
		}
		
		testLocal.setMessages("es");
		weekdayName = testLocal.getWeekdayNames();
		if (Configuration.get("FIRST_DAY_OF_WEEK").equals("mon")) {
			assertTrue(weekdayName[0].equals("Lu"));
			assertTrue(weekdayName[4].equals("Vi"));
			assertTrue(weekdayName[6].equals("Do"));
			assertFalse(weekdayName[1].equals("Mi"));
		} else {
			assertTrue(weekdayName[0].equals("Do"));
			assertTrue(weekdayName[4].equals("Ju"));
			assertTrue(weekdayName[6].equals("Sa"));
			assertFalse(weekdayName[1].equals("Ma"));
		}
		
		testLocal.setMessages("de");
		weekdayName = testLocal.getWeekdayNames();
		if (Configuration.get("FIRST_DAY_OF_WEEK").equals("mon")) {
			assertTrue(weekdayName[0].equals("Mo"));
			assertTrue(weekdayName[4].equals("Fr"));
			assertTrue(weekdayName[6].equals("So"));
			assertFalse(weekdayName[1].equals("Mi"));
		} else {
			assertTrue(weekdayName[0].equals("So"));
			assertTrue(weekdayName[4].equals("Do"));
			assertTrue(weekdayName[6].equals("Sa"));
			assertFalse(weekdayName[1].equals("Di"));
		}
	}
}
