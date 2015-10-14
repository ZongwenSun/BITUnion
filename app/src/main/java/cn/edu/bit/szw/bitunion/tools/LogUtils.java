package cn.edu.bit.szw.bitunion.tools;

import android.util.Log;

public class LogUtils {

	private static boolean debug = true;

	public static boolean isDebug() {
		return debug;
	}

	public static void setDebug(boolean debug) {
		LogUtils.debug = debug;
	}

	public static void log(String tag, String msg) {
		if (debug) {
			Log.i(tag, msg);
		}
	}

	public static void log(String msg) {
		if (debug) {
			Log.i("BITUnion", msg);
		}
	}
}
