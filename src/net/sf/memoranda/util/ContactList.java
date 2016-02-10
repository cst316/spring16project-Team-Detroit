//package net.sf.memoranda.util;

/**
 * Configuration.java
 * Created on 12.03.2003, 0:31:22 Alex
 * Package: net.sf.memoranda.util
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda.util;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import net.sf.memoranda.EmailContact;
import net.sf.memoranda.ui.AppFrame;
import net.sf.memoranda.ui.ExceptionDialog;

public class ContactList {

    static LoadableProperties config  = new LoadableProperties();
    static String configPath = getConfigPath();

    static {
    	AppFrame.addExitListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //CurrentStorage.get().storeContacts();
            	saveConfig();
            }
        });
    try {
     config.load(new FileInputStream(configPath));
     System.out.println("Loaded from " + configPath);
    }
    catch (Exception e) {      
      File f = new File(configPath);
      new File(f.getParent()).mkdirs();      
      /*DEBUG*/System.out.println("New contact list created: "+configPath);
      try {
        //config.load(ContactList.class.getResourceAsStream("resources/memoranda.default.properties"));
        saveConfig();      
      }
      catch (Exception e2) {
        new ExceptionDialog(e2, "Failed to load default configuration from resources.", "");
        config = null;
      }
    }
  }
  
  static String getConfigPath() {
    String p = Util.getEnvDir() + "contacts" + File.separator + "contactsList.properties";
    if (new File(p).exists()) 
        return p;   
    return p;
  }

  public static void saveConfig() {
    try {
    config.save(new FileOutputStream(configPath));
    System.out.println("[DEBUG] Save contacts list: " + getConfigPath());
    //System.out.println("Saving contact list");
    }
    catch (Exception e) {
     new ExceptionDialog(e, "Failed to save a contactList file:<br>"+configPath, "");
    }
  }

  public static Object get(String key) {
    if ((config.get(key)) == null) {
        return "";
    }
    return config.get(key);
  }

  @SuppressWarnings("unchecked")
public static void put(String key, EmailContact value) {
    config.put(key, value);
  }
}