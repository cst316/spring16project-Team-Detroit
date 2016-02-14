package net.sf.memoranda.unittest;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import net.sf.memoranda.EmailContact;
import net.sf.memoranda.util.SerializationUtil;

public class SerializationUtilTest {
	
	ArrayList<EmailContact> alTest = null;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		alTest = new ArrayList<EmailContact>();
	}

	@Test
	public void testSerializeList () {
		assertTrue(SerializationUtil.serilaizeList(alTest));
	}
	
	@Test
	public void testDerializeList () {
		ArrayList<EmailContact> alTest1 = new ArrayList<EmailContact>();
		assertEquals(alTest, alTest1);
		//assertTrue(SerializationUtil.serilaizeList(new ArrayList<EmailContact>()));
	}

}
