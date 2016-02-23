package net.sf.memoranda;

import java.awt.Desktop;
import java.awt.Toolkit;
import java.awt.datatransfer.*;
import java.net.URI;
import java.net.URL;

/*
 * Open Default browser when asking to preview note
 * created by Casey Froke Sprint 1  added: 1/31/20016
 */
public class OpenBrowser 
{
	public static void openBrowser(String url)
	{
		try
		{
			URI uri = new URL(url).toURI();
			Desktop desktop = Desktop.isDesktopSupported() ? Desktop.getDesktop() : null;
			if (desktop != null && desktop.isSupported(Desktop.Action.BROWSE))
				desktop.browse(uri);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			
			StringSelection stringSelection = new StringSelection(url);
			Clipboard clipped = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipped.setContents(stringSelection, null);
		}
	}

}
