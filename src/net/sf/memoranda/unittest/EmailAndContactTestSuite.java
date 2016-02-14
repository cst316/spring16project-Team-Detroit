package net.sf.memoranda.unittest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import net.sf.memoranda.util.SerializationUtil;

@RunWith(Suite.class)
@SuiteClasses({ ContactListStorageTest.class, ContactListTest.class, DailyEmailTest.class, EmailContactTest.class, EmailerTest.class, SerializationUtil.class })
public class EmailAndContactTestSuite {

}
