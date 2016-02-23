/**
 * MimeTypesList.java
 * Created on 24.03.2003, 13:54:52 Alex
 * Package: net.sf.memoranda.util
 *
 * @author Alex V. Alishevskikh, alex@openmechanics.net
 * Copyright (c) 2003 Memoranda Team. http://memoranda.sf.net
 */
package net.sf.memoranda.util;
import java.util.Locale;
import java.util.Vector;

import nu.xom.Attribute;
import nu.xom.Document;
import nu.xom.Element;
import nu.xom.Elements;
/**
 *
 */
/*$Id: MimeTypesList.java,v 1.3 2004/01/30 12:17:42 alexeya Exp $*/
public class MimeTypesList {
    static Document _doc = null;
    static Element _root = null;

    static {
        CurrentStorage.get().openMimeTypesList();
        _root = _doc.getRootElement();
    }

    public static Vector<MimeType> getAllMimeTypes() {
        Vector<MimeType> v = new Vector<MimeType>();
        Elements els = _root.getChildElements("mime-type");
        for (int i = 0; i < els.size(); i++)
            v.add(new MimeType(els.get(i)));
        return v;
    }

    public static MimeType getMimeTypeForFile(String path) {
        return getMimeTypeByExt(getExtension(path));
    }

    public static MimeType getMimeType(String mimeId) {
        Elements els = _root.getChildElements("mime-type");
        for (int i = 0; i < els.size(); i++)
            if (els.get(i).getAttribute("id").getValue().equals(mimeId))
                return new MimeType(els.get(i));
        return new MimeType();
    }

    public static MimeType getMimeTypeByExt(String ext) {
        Elements els = _root.getChildElements("mime-type");
        for (int i = 0; i < els.size(); i++) {
            Element el = els.get(i);
            Elements exts = el.getChildElements("ext");
            for (int j = 0; j < exts.size(); j++)
                if (exts.get(j).getValue().toLowerCase(Locale.forLanguageTag("en")).equals(ext.toLowerCase(Locale.forLanguageTag("en"))))
                    return new MimeType(el);
        }
        return new MimeType();
    }

    public static MimeType addMimeType(String mimeId) {
        Element el = new Element("mime-type");
        el.addAttribute(new Attribute("id", mimeId));
        _root.appendChild(el);
        return new MimeType(el);
    }

    public static void removeMimeType(String mimeId) {
        Elements els = _root.getChildElements("mime-type");
        for (int i = 0; i < els.size(); i++)
            if (els.get(i).getAttribute("id").getValue().equals(mimeId)) {
                _root.removeChild(els.get(i));
                return;
            }
    }

    public static AppList getAppList() {
        return new AppList(_root.getChildElements("applications").get(0));
    }

    public static String getExtension(String s) {
        String ext = null;
        int i = s.lastIndexOf('.');
        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase(Locale.forLanguageTag("en"));
        }
        return ext;
    }


}
