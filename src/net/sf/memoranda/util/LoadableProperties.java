package net.sf.memoranda.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.*;

/*$Id: LoadableProperties.java,v 1.4 2004/01/30 12:17:42 alexeya Exp $*/
@SuppressWarnings("serial")
public class LoadableProperties extends Hashtable<Object, Object> {

    public LoadableProperties() {
        super();
    }

    public void load(InputStream inStream) throws IOException {

    	////Reader reader = new InputStreamReader(inStream);
    	//BufferedReader in = new BufferedReader(reader);
        BufferedReader in = new BufferedReader(new InputStreamReader(inStream, "UTF-8"));

        String aKey;
        String aValue;
        int index;
        String line = getNextLine(in);
        while (line != null) {
            line = line.trim();
            if (isValid(line)) {
                index = line.indexOf("=");
                aKey = line.substring(0, index).trim();
                aValue = line.substring(index + 1).trim();
                put(aKey.toUpperCase(Locale.forLanguageTag("en")), aValue);
            }
            line = getNextLine(in);
        }
    }

    public void save(OutputStream outStream, boolean sorted) throws IOException {
    	if (!sorted) {
    		save(outStream);
    		return;
    	}
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(outStream, "UTF-8"));
        String aKey;
        Object aValue;
        TreeMap<?, ?> tm = new TreeMap<Object, Object>(this);
        for (@SuppressWarnings("unchecked")
        Iterator<String> i = (Iterator<String>) tm.keySet().iterator(); i.hasNext();) {
            aKey = (String) i.next();
            aValue = get(aKey);
            out.write(aKey + " = " + aValue);
            out.newLine();
        }
        out.flush();
        out.close();
    }
    
    public void save(OutputStream outStream) throws IOException {
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(outStream, "UTF-8"));
        String aKey;
        Object aValue;
        for (Enumeration<?> e = keys(); e.hasMoreElements();) {
            aKey = (String) e.nextElement();
            aValue = get(aKey);
            out.write(aKey + " = " + aValue);
            out.newLine();
        }
        out.flush();
        out.close();
    }

    private boolean isValid(String str) {
        if (str == null)
            return false;
        if (str.length() > 0) {
            if (str.startsWith("#") || str.startsWith("!")) {
                return false;
            }
        }
        else {
            return false;
        }

        int index = str.indexOf("=");
        if (index > 0 && str.length() > index) {
            return true;
        }
        else {
            return false;
        }
    }

    private String getNextLine(BufferedReader br) {
        try {
            return br.readLine();
        }
        catch (Exception e) {
            return null;
        }

    }

}
