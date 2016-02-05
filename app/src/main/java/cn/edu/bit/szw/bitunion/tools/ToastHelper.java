package cn.edu.bit.szw.bitunion.tools;

import android.content.Context;
import android.widget.Toast;

import cn.edu.bit.szw.bitunion.MainApplication;

public class ToastHelper {

	public static void showToast(Context contxt, String msg) {
		Toast.makeText(contxt, msg, Toast.LENGTH_SHORT).show();
	}

	public static void show(String toast){
		Toast.makeText(MainApplication.instance, toast, Toast.LENGTH_SHORT).show();
	}

	public static void show(int resId) {
		show(MainApplication.instance.getString(resId));
	}

}
