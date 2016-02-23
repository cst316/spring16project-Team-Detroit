package net.sf.memoranda.tests;

import static org.junit.Assert.*;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.CountDownLatch;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import net.sf.memoranda.util.ClockObservable;
import net.sf.memoranda.util.Configuration;
import net.sf.memoranda.util.MultiThreadedRunner;

@RunWith(MultiThreadedRunner.class)
public class ClockObservableTest implements Observer {
	/*private ClockObservable clock_one = null;
	private ClockObservable clock_two = null;
	private ClockObservable clock_three = null;
	
	private static final CountDownLatch latch = new CountDownLatch(3);
	
	@Before
	public void setUp() throws Exception {
		//System.out.println(Runtime.getRuntime().availableProcessors());
		clock_one = new ClockObservable();
		clock_one.addObserver(this);
		Thread.sleep(600);
		clock_two = new ClockObservable();
		clock_two.addObserver(this);
	}
	
	@Test (timeout = 2500)
	public void afterEachObserverIsUpdatedTheyShouldReturnEqualTimes() 
			throws InterruptedException {
		latch.await();
		assertTrue(clock_one.getTime().equals(clock_two.getTime()));
	}

	@Test (timeout = 1500)
	public void aNewlyInstantiatedClockObservableShouldEqualPreviousClockObservables() 
			throws InterruptedException {
		Thread.sleep(600);
		clock_three = new ClockObservable();
		clock_three.addObserver(this);
		
		assertTrue(clock_one.getTime().equals(clock_three.getTime()));
	}
	
	@Test (timeout = 2500)
	public void afterTwoSecondsAClockShouldIncrementAccordingly() 
			throws InterruptedException {
		String firstString = clock_one.getTime();
		Thread.sleep(2000);
		String secondString = clock_two.getTime();
		
		int firstValue = parseTime(firstString);
		int secondValue = parseTime(secondString);
		
		if (secondValue > 1) {
			assertTrue(secondValue == firstValue + 2);
		} else {
			if (Configuration.get("MILITARY_TIME").equals("yes")){
				assertTrue(firstValue == 24 * 3600 - secondValue);
			} else {
				assertTrue(firstValue == 12 * 3600 - secondValue);
			}
		}
	}*/
	
	@Test //(timeout = 2500)
	public void testMilitaryMorning() {
		assertTrue(ClockObservable.getTime(0,0,0, true).equals("00:00:00"));
	}
	
	@Test //(timeout = 2500)
	public void testMilitaryEvening() {
		assertTrue(ClockObservable.getTime(13,59,59, true).equals("13:59:59"));
	}
	
	@Test //(timeout = 2500)
	public void testCivilianMorning() {
		assertTrue(ClockObservable.getTime(0,0,0, false).equals("12:00:00 am"));
	}
	
	@Test //(timeout = 2500)
	public void testCivilianEvening() {
		assertTrue(ClockObservable.getTime(13, 59, 59, false).equals("1:59:59 pm"));
	}
	
	/*public static int parseTime(String aTime) {
		int result = 0;
		
		boolean militaryTime = Configuration.get("MILITARY_TIME").equals("yes");
		
		if (militaryTime) {
			result += Integer.parseInt(aTime.substring(0, 2)) * 3600;
			result += Integer.parseInt(aTime.substring(3, 5)) * 60;
			result += Integer.parseInt(aTime.substring(6, 8));
		} else if (!(aTime.substring(1)).equals(":")) {
			result += Integer.parseInt(aTime.substring(0, 2)) * 3600;
			result += Integer.parseInt(aTime.substring(3, 5)) * 60;
			result += Integer.parseInt(aTime.substring(6, 8));
		} else {
			result += Integer.parseInt(aTime.substring(0, 1)) * 3600;
			result += Integer.parseInt(aTime.substring(2, 4)) * 60;
			result += Integer.parseInt(aTime.substring(5, 7));
		}
		
		//System.out.println(result);
		
		return result;
	}*/

	@Override
	public void update(Observable o, Object arg) {
		//if (clock_one == o) {
		//	latch.countDown();
		//}
		
		/*System.out.println(latch.getCount() + " " + arg +
				" " + clock_one.getTime() + " 2-" + 
				((clock_two != null) ? clock_two.getTime() : "") + " 3-" + 
				((clock_three != null) ? clock_two.getTime() : ""));*/
	}
}
