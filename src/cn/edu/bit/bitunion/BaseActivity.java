package cn.edu.bit.bitunion;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import cn.edu.bit.bitunion.global.NetworkManager;
import cn.edu.bit.bitunion.tools.ToastHelper;

public class BaseActivity extends FragmentActivity {
	MainApplication AppContext;
	private ProgressDialog progressDialog;
	private RelativeLayout mTitlebarLayout;

	public BaseActivity() {
		AppContext = (MainApplication) getApplication();
	}

	public MainApplication getAppContext() {
		return AppContext;
	}

	/**
	 * ��ת����������
	 * 
	 * @param cls
	 *            ��תҳ��
	 * @param bundle
	 *            Bundle����
	 * @param isReturn
	 *            �Ƿ񷵻�
	 * @param requestCode
	 *            ����Code
	 * @param isFinish
	 *            �Ƿ����ٵ�ǰҳ��
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
	 * ��ת����������
	 * 
	 * @param cls
	 *            ��תҳ��
	 * @param bundle
	 *            Bundle����
	 * @param isFinish
	 *            �Ƿ����ٵ�ǰҳ��
	 */
	protected void jumpToPage(Class<?> cls, Bundle bundle, boolean isFinish) {
		jumpToPage(cls, bundle, false, 0, isFinish);
	}

	/**
	 * ��ת���������棬�����ٵ�ǰҳ�棬Ҳ��������
	 * 
	 * @param cls
	 *            ��תҳ��
	 */
	protected void jumpToPage(Class<?> cls) {
		jumpToPage(cls, null, false, 0, false);
	}

	protected boolean checkConnection() {
		if (NetworkManager.getInstance(this).isConnected()) {
			return true;
		} else {
			ToastHelper.showToast(this, "�]���������ӣ�������������");
			return false;
		}
	}

	public void showLoadingDialog() {
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this, R.style.dialog);
		}

		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.setMessage("��ȴ�...");
		progressDialog.show();
	}

	public void hideLoadingDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.dismiss();
		}

	}

	protected void initTitleBar() {
		mTitlebarLayout = (RelativeLayout) findViewById(R.id.title_bar_layout);
	}

	protected void setTitle(String title) {
		if (mTitlebarLayout == null) {
			initTitleBar();
		}
		TextView titleTextView = (TextView) mTitlebarLayout.findViewById(R.id.title_bar_title);
		titleTextView.setText(title);

	}

	protected void setLeftBtnVisible(boolean visible) {
		if (mTitlebarLayout == null) {
			initTitleBar();
		}
		Button leftButton = (Button) mTitlebarLayout.findViewById(R.id.title_bar_left_button);
		if (visible) {
			leftButton.setVisibility(View.VISIBLE);
			leftButton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		} else {
			leftButton.setVisibility(View.GONE);
		}
	}

}
