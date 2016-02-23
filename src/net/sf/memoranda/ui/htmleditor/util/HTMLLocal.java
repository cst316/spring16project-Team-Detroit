/**
 * Local.java
 * Created on 05.09.2003, 16:43:39 Alex
 * Package: org.openmechanics.htmleditor
 * 
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 OpenMechanics.org
 */
package net.sf.memoranda.ui.htmleditor.util;
import java.util.Hashtable;
import java.util.Locale;
import net.sf.memoranda.util.Local;

/**
 * 
 */
public class HTMLLocal {

   static Hashtable<?, ?> messages = null;
   
   public static void setMessages(Hashtable<?, ?> msgs) {
        messages = msgs;
   }  
   
   public static String getString(String key) {
        if (messages == null){
            return key;
        }
        String msg = (String) messages.get(key.trim().toUpperCase(Locale.forLanguageTag("en")));
        if ((msg != null) && (msg.length() > 0)){
            return msg;
        }
        else {
            return key;
        }
   }

public static Locale getCurrentLocale() {
	return Local.getCurrentLocale();
}

}