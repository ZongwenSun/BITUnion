package cn.edu.bit.bitunion.tools;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {
	private static final int SHORT = 1000;
	private static final int MIDDLE = 2000;
	private static final int LONG = 3000;

	public static void showToast(Context contxt, String msg) {
		Toast.makeText(contxt, msg, MIDDLE).show();
	}

	public static void showToastLong(Context contxt, String msg) {
		Toast.makeText(contxt, msg, LONG).show();
	}

	public static void showToastShort(Context contxt, String msg) {
		Toast.makeText(contxt, msg, SHORT).show();
	}

	public static void showToastTime(Context context, String msg, int duration) {
		Toast.makeText(context, msg, duration).show();
	}

}
