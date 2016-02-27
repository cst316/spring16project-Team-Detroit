package net.sf.memoranda.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ CalendarDateTest.class, ClockObservableTest.class, ContactListStorageTest.class, ContactListTest.class,
		DailyEmailTest.class, EmailContactTest.class, EmailerTest.class, LocalTest.class, SerializationUtilTest.class,
		UserProfileTest.class })
public class AllTests {

}
