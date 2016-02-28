package net.sf.memoranda.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ContactListStorageTest.class, ContactListTest.class, DailyEmailTest.class, EmailContactTest.class, EmailerTest.class, 
	SerializationUtilTest.class, UserProfileTest.class, TaskObserverTest.class, PersonalEmailTest.class })
public class ProfileEmailContactTestSuite {

}
