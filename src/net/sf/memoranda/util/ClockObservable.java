package net.sf.memoranda.util;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.GregorianCalendar;
import java.util.Observable;

import javax.swing.Timer;

//Source code obtained and modified from GitHub user luizfonesca - Clock.java
public class ClockObservable extends Observable implements ActionListener{
	public static Timer timer = null;

	public ClockObservable() {
		startClock();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		setChanged();
		notifyObservers(this.getTime());
	}
	
	public String getTime() {
		String time = "";
		
		GregorianCalendar calendar = new GregorianCalendar();
		
		int h = calendar.get(GregorianCalendar.HOUR_OF_DAY);
		int m = calendar.get(GregorianCalendar.MINUTE);
		int s = calendar.get(GregorianCalendar.SECOND);
		
		boolean militaryTime = Configuration.get("MILITARY_TIME").equals("yes");
		
		if (militaryTime && h < 0) {
			time += "0";
		}
		time += ((militaryTime) ? h : h - 12) + ":";
		time += ((m < 10) ? "0" : "") + m + ":";
		time += ((s < 10) ? "0" : "") + s;
		
		if (!militaryTime && h > 12) {
			time += " pm";
		} else if (!militaryTime) {
			time += " am";
		}
		
		return time;
	}
	
	public static int parseTime(String aTime) {
		int result = 0;
		
		boolean militaryTime = Configuration.get("MILITARY_TIME").equals("yes");
		
		if (militaryTime) {
			result += Integer.parseInt(aTime.substring(0, 2)) * 3600;
			result += Integer.parseInt(aTime.substring(3, 5)) * 60;
			result += Integer.parseInt(aTime.substring(6, 8));
		} else {
			if (aTime.substring(1) != ":") {
				result += Integer.parseInt(aTime.substring(0, 2)) * 3600;
				result += Integer.parseInt(aTime.substring(3, 5)) * 60;
				result += Integer.parseInt(aTime.substring(6, 8));
			} else {
				result += Integer.parseInt(aTime.substring(0, 1)) * 3600;
				result += Integer.parseInt(aTime.substring(2, 4)) * 60;
				result += Integer.parseInt(aTime.substring(5, 7));
			}
		}
		
		//System.out.println(result);
		
		return result;
	}
	
	public void startClock() {
		if (timer == null) {
			timer = new Timer(1000, this);
			timer.setInitialDelay(0);
			timer.start();
		}
	}
}
