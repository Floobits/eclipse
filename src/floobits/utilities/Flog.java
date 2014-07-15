package floobits.utilities;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

import floobits.Activator;

public class Flog {
    public static void log (String s, Object... args) {
    	Activator._log(IStatus.OK, String.format(s, args), Status.OK);
    }
    public static void debug (String s, Object... args) {
    	Activator._log(IStatus.OK, String.format(s, args), Status.INFO);
    }
    public static void warn (Throwable e) {
    	Activator._log(IStatus.WARNING, e.toString(), Status.WARNING);
    }
    public static void warn (String s, Object... args) {
    	Activator._log(IStatus.WARNING, String.format(s, args), Status.WARNING);
    }
    public static void info (String s, Object... args) {
    	Activator._log(IStatus.INFO, String.format(s, args), Status.INFO);
    }
    public static void statusMessage(String message) {
        log(message);
    }

    public static void errorMessage(String message) {
        warn(message);
    }

    public static void flashMessage(final String message) {
    	log(message);
    }
}
