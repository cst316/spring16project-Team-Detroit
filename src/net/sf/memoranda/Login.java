package net.sf.memoranda;

import net.sf.memoranda.ui.LoginDialogBox;
import net.sf.memoranda.ui.InitialLogin;
import net.sf.memoranda.util.Configuration;

public class Login {

	static boolean cancelled = false;
	static boolean valid = true;
	static boolean loginOpen = false;

	public static void getDetails() {
		if (Configuration.get("USER_PASSWORD").toString().equalsIgnoreCase("default")
				|| Configuration.get("USER_PASSWORD") == "") {
			loginOpen = true;
			new InitialLogin();
		} else if (Configuration.get("USER_PASSWORD").toString().equalsIgnoreCase("none")) {
			cancelled = true;
		} else {
			loginOpen = true;
			new LoginDialogBox();
		}
	}

	public static boolean isOpen() {
		return loginOpen;
	}

	public static boolean isValid() {
		return valid;
	}

	public static boolean isCancelled() {
		return cancelled;
	}

	public static void cancelled() {
		cancelled = true;
	}

	public static void invalid() {
		valid = false;
	}
}