package net.sf.memoranda.ui.htmleditor;
import java.util.Hashtable;

/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */

class Context {

  static java.util.Hashtable<Object, Object> hash = new Hashtable<Object, Object>();

  public static Object get(Object key) {
    return hash.get(key);
  }

  public static void put(Object key, Object value) {
    hash.put(key, value);
  }

}