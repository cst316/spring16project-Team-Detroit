






package net.sf.memoranda;

import java.awt.print.*;
import java.util.*;
import java.awt.*;
import java.text.*;

/**
 * Print class to print events and possible other items in future
 * Created:  Ryan Schultz 1/31/2016
 */
public class Print implements Printable {
	
	/**
	 * Constructor for Print class
	 * Created:  Ryan Schultz 1/31/2016
	 */
	public Print() {
	}
	
	/**
	 * print prints the events
	 * @param g graphics to be printed
	 * @param pf page format
	 * @param page pages to be printed
	 * Created:  Ryan Schultz 1/31/2016
	 */
	public int print(Graphics g, PageFormat pf, int page) throws PrinterException {
		// Add date to be printed
		String evlist = new SimpleDateFormat("EEEE, MMMM, dd yyyy").format(new Date()) + "\n";
		// Get all events for the day
        for (Iterator it = EventsScheduler.getScheduledEvents().iterator(); it.hasNext();) {
            net.sf.memoranda.Event ev = (net.sf.memoranda.Event)it.next();   
            evlist += ev.getTimeString()+" - "+ev.getText()+"\n";
        }
        // Split the string to be printed on separate lines
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
	 * printEvents creates printer job and brings up print dialog
	 * Created:  Ryan Schultz 1/31/2016
	 */
	public void printEvents() {
		PrinterJob job = PrinterJob.getPrinterJob();
        job.setPrintable(this);
        boolean ok = job.printDialog();
        if (ok) {
            try {
                 job.print();
            } catch (PrinterException ex) {
            	System.out.println(ex);
            }
        }
	}
}
