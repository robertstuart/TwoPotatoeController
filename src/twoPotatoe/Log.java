package twoPotatoe;

public class Log {
	// Just print this out for now. We may add a sysem log file.
	public static void logTrace(String msg, boolean trace) {
		System.err.println("TPLog: " + msg);
		if (trace) {
			StackTraceElement[] el = Thread.currentThread().getStackTrace();
			for (int i = 0; i < el.length; i++) {
				System.err.println("TPStack: " + el[i].toString());
			}
		}
	}
	
	public static void log(String msg) {
		System.err.println("TPLog: " + msg); 
	}
}
