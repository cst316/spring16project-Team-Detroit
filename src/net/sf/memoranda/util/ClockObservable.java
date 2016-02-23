package net.sf.memoranda.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import java.util.Observable;

import javax.swing.Timer;

//Source code obtained and modified from GitHub user luizfonesca - Clock.java
public class ClockObservable extends Observable implements ActionListener{
	private static Timer timer = null;

	public ClockObservable() {
		startClock();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		setChanged();
		notifyObservers(this.getTime());
	}
	
	public static String getTime(int hour, int minute, int second, boolean isMilitaryTime) {
		String time = "";
		
		if (isMilitaryTime && hour < 10) {
			time += "0";
		}
			
		if (hour > 12) {
			time += ((isMilitaryTime) ? hour : hour - 12) + ":";
		} else if (hour == 0 && !isMilitaryTime) {
			time += (hour + 12) + ":";
		} else {
			time += hour + ":";
		}
		
		time += ((minute < 10) ? "0" : "") + minute + ":";
		time += ((second < 10) ? "0" : "") + second;
		
		if (!isMilitaryTime && hour > 12) {
			time += " pm";
		} else if (!isMilitaryTime) {
			time += " am";
		}
		
		return time;
	}
	
	public String getTime() {
		GregorianCalendar calendar = new GregorianCalendar();
		
		int h = calendar.get(GregorianCalendar.HOUR_OF_DAY);
		int m = calendar.get(GregorianCalendar.MINUTE);
		int s = calendar.get(GregorianCalendar.SECOND);
		
		boolean militaryTime = Configuration.get("MILITARY_TIME").equals("yes");
		
		return getTime(h, m, s, militaryTime);
	}
	
	public void startClock() {
		if (timer == null) {
			timer = new Timer(1000, this);
			timer.setInitialDelay(0);
			timer.start();
		}
	}
}
