/**
 * EventsScheduler.java
 * Created on 10.03.2003, 20:20:08 Alex
 * Package: net.sf.memoranda
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;
import java.util.concurrent.TimeUnit;

import net.sf.memoranda.date.CalendarDate;

/**
 *
 */
/*$Id: EventsScheduler.java,v 1.4 2004/01/30 12:17:41 alexeya Exp $*/
public class EventsScheduler {

    static Vector<EventTimer> _timers = new Vector<EventTimer>();
    static Vector<EventNotificationListener> _listeners = new Vector<EventNotificationListener>();

    static Timer changeDateTimer = new Timer();

    static {
        addListener(new DefaultEventNotifier());            
    }

    public static void init() {
        cancelAll();
        //changeDateTimer.cancel();
        Vector<Event> events = (Vector<Event>)EventsManager.getNextActiveEventWithinThirtyDays();
        _timers = new Vector<EventTimer>();
        /*DEBUG*/System.out.println("----------");
        for (int i = 0; i < events.size(); i++) {
            Event ev = (Event)events.get(i);
            Date evTime = ev.getTime();
            ///*DEBUG*/System.out.println(evTime.toString() + (Calendar.getInstance()).getTime());
            //  if (evTime.after(new Date())) {
            if (evTime.getTime() == 0) {
                EventTimer t = new EventTimer(ev);
                _timers.add(t);
            }
	          if (evTime.after((Calendar.getInstance()).getTime())) {	
                EventTimer t = new EventTimer(ev);
                t.schedule(new NotifyTask(t), ev.getTime());                
                _timers.add(t);
                ///*DEBUG*/System.out.println(ev.getTimeString());
            }
        }
        /*DEBUG*/System.out.println("----------");
        Date midnight = getMidnight();
        changeDateTimer.schedule(new TimerTask() {
                public void run() {
                    init();
                    this.cancel();
                }
        }, midnight);
        notifyChanged();
    }

    public static void cancelAll() {
        for (int i = 0; i < _timers.size(); i++) {
            EventTimer t = _timers.get(i);
            t.cancel();
        }
    }
    
    public static Vector<Event> getScheduledEvents() {
        Vector<Event> v = new Vector<Event>();
        for (int i = 0; i < _timers.size(); i++) 
            v.add(_timers.get(i).getEvent());
        return v;
    }
    
    public static Event getFirstScheduledEvent() {
        if (!isEventScheduled()) return null;
        Event e1 = _timers.get(0).getEvent();
        for (int i = 1; i < _timers.size(); i++) { 
            Event ev = _timers.get(i).getEvent();
            if (ev.getTime().before(e1.getTime()))
                e1 = ev;
            if (ev.getTime().getTime() == 0) {
              return ev;
            }
        }
        return e1;
    }
            

    public static void addListener(EventNotificationListener enl) {
        _listeners.add(enl);
    }

    public static boolean isEventScheduled() {
        return _timers.size() > 0;
    }
        
    private static void notifyListeners(Event ev) {
        for (int i = 0; i < _listeners.size(); i++)
            _listeners.get(i).eventIsOccured(ev);
    }

    private static void notifyChanged() {
        for (int i = 0; i < _listeners.size(); i++)
            _listeners.get(i).eventsChanged();
    }

    private static Date getMidnight() {
       Calendar cal = Calendar.getInstance();
       cal.set(Calendar.HOUR_OF_DAY, 0);
       cal.set(Calendar.MINUTE, 0);
       cal.set(Calendar.SECOND,0);
       cal.set(Calendar.MILLISECOND,0);
       cal.add(Calendar.DAY_OF_MONTH,1);
       return cal.getTime();
    }

    static class NotifyTask extends TimerTask {
        
        EventTimer _timer;

        public NotifyTask(EventTimer t) {
            super();            
            _timer = t;
        }
        
        public void run() {            
            _timer.cancel();
            _timers.remove(_timer);
            notifyListeners(_timer.getEvent());
            notifyChanged();
        }
    }
    
    static class EventTimer extends Timer {
        Event _event;
        
        public EventTimer(Event ev) {
            super();
            _event = ev;
        }
        
        public Event getEvent() {
            return _event;
        }
    }

    public static long getTimeToNextEventInSeconds() {
      Event ev = getFirstScheduledEvent();
      
      if (ev == null) {
        //System.out.println("Could not find event in the next 30 days");
        return -1;
      }
      
      if (ev.getTime().getTime() == 0) {
        //System.out.println("Event needs to be updated using current version");
        return -2;
      }
      
      long seconds = CalendarDate.getDateDiff(new Date(), 
          ev.getTime(), TimeUnit.SECONDS);
      
      return seconds;
    }

}
