package cn.edu.bit.bitunion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import cn.edu.bit.bitunion.global.NetworkManager;
import cn.edu.bit.bitunion.tools.ToastHelper;

public class BaseActivity extends ActionBarActivity {
	MainApplication AppContext;
	private ProgressDialog progressDialog;

	@Override
	protected void onCreate(Bundle arg0) {
		// TODO Auto-generated method stub
		super.onCreate(arg0);
		AppContext = (MainApplication) getApplication();
	}

	public MainApplication getAppContext() {
		return AppContext;
	}

	/**
	 * 跳转到其他界面
	 * 
	 * @param cls
	 *            跳转页面
	 * @param bundle
	 *            Bundle参数
	 * @param isReturn
	 *            是否返回
	 * @param requestCode
	 *            请求Code
	 * @param isFinish
	 *            是否销毁当前页面
	 */
	public void jumpToPage(Class<?> cls, Bundle bundle, boolean isReturn, int requestCode, boolean isFinish) {
		if (cls == null) {
			return;
		}
		Intent intent = new Intent();
		intent.setClass(this, cls);
		if (bundle != null) {
			intent.putExtras(bundle);
		}

		if (isReturn) {
			startActivityForResult(intent, requestCode);
		} else {
			startActivity(intent);
		}

		if (isFinish) {
			finish();
		}
	}

	/**
	 * 跳转到其他界面
	 * 
	 * @param cls
	 *            跳转页面
	 * @param bundle
	 *            Bundle参数
	 * @param isFinish
	 *            是否销毁当前页面
	 */
	protected void jumpToPage(Class<?> cls, Bundle bundle, boolean isFinish) {
		jumpToPage(cls, bundle, false, 0, isFinish);
	}

	/**
	 * 跳转到其他界面，不销毁当前页面，也不传参数
	 * 
	 * @param cls
	 *            跳转页面
	 */
	protected void jumpToPage(Class<?> cls) {
		jumpToPage(cls, null, false, 0, false);
	}

	protected boolean checkConnection() {
		if (NetworkManager.getInstance().isConnected()) {
			return true;
		} else {
			ToastHelper.showToast(this, "沒有网络连接，请检查网络设置");
			return false;
		}
	}

	public void showLoadingDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this, R.style.dialog);
		}
		if (!progressDialog.isShowing()) {
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setMessage("请等待...");
			progressDialog.show();
		}
	}

	public void hideLoadingDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}

	}

	protected void setTitle(String title) {
		getSupportActionBar().setTitle(title);
	}
}
