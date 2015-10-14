package cn.edu.bit.szw.bitunion.tools;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import cn.edu.bit.szw.bitunion.MainApplication;
import cn.edu.bit.szw.bitunion.network.BUError;

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

	public static void showError(VolleyError error) {
		if (error instanceof BUError) {
			BUError buError = (BUError)error;
			BUError.ErrorType errorType = buError.getErrorType();
			show(errorType.getErrorTipRes());
		}
		else {
			show(error.getMessage());
		}
	}
}
