/* 
  File:		Print.java
  Author:	Ryan Schultz	
  Date:		1/31/2016

  Description: Prints events for current day
*/

package net.sf.memoranda;

import java.awt.print.*;
import java.util.*;
import java.awt.*;
import java.text.*;

/**
Class:	Print

Description:  Prints events for current day to users default printer using dialog box
*/
public class Print implements Printable {
	
	public Print() {
	}
	
	/**
	  Method:	print
	  @param:	Formatted graphics, page format, pages to be printed
	  @return: 	Pages printed

	  Description: Formats and prints the events
	*/
	public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
		String evlist = new SimpleDateFormat("EEEE, MMMM, dd yyyy").format(new Date()) + "\n";
        for (Iterator<Event> it = EventsScheduler.getScheduledEvents().iterator(); it.hasNext();) {
            net.sf.memoranda.Event ev = (net.sf.memoranda.Event)it.next();   
            evlist += ev.getTimeString()+" - "+ev.getText()+"\n";
        }

        String[] lines = evlist.split("\r?\n|\r");
        
		if (page > 0) { /* We have only one page, and 'page' is zero-based */
			return NO_SUCH_PAGE;
		}

		//  X and Y values for page format
		Graphics2D g2d = (Graphics2D)g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());
		
		// Alignment format
        int y = 50;

		// Rendering
		for (int i = 0; i < lines.length; i++) {
            g.drawString(lines[i], 30, y);
            y = y + 15;
        }
		return PAGE_EXISTS;
	}
	
	/**
	  Method:	printEvents
	  @param:	N/A
	  @return: 	N/A

	  Description: Creates printer job and brings up print dialog
	*/
	public void printEvents() {
		PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                 job.print();
            } catch (PrinterException ex) {
            	ex.getStackTrace();
            }
        }
	}
}
