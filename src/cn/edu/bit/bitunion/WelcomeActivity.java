package cn.edu.bit.bitunion;

import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.edu.bit.bitunion.entities.RequestJsonFactory;
import cn.edu.bit.bitunion.entities.response.LoginResponse;
import cn.edu.bit.bitunion.global.GlobalUrls;
import cn.edu.bit.bitunion.global.LoginManager;
import cn.edu.bit.bitunion.network.RequestQueueManager;
import cn.edu.bit.bitunion.tools.LogUtils;
import cn.edu.bit.bitunion.tools.ToastHelper;

import com.alibaba.fastjson.JSON;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

public class WelcomeActivity extends BaseActivity {
	private static final String TAG = "WelcomeActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		mHandler.sendEmptyMessageDelayed(0, 1000);
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			boolean hasLogin = LoginManager.getInstance(getAppContext()).hasLogin();
			if (hasLogin) {
				if (checkConnection()) {
					LoginManager loginManager = LoginManager.getInstance(getAppContext());

					RequestQueueManager.getInstance(getAppContext()).postJsonRequest(GlobalUrls.getLoginUrl(),
							RequestJsonFactory.loginJson(loginManager.getUserName(), loginManager.getPassword()), new Listener<JSONObject>() {

								@Override
								public void onResponse(JSONObject response) {
									// TODO Auto-generated method stub
									LoginResponse loginResponse = JSON.parseObject(response.toString(), LoginResponse.class);
									if (loginResponse.getResult().equalsIgnoreCase("success")) {
										getAppContext().setLoginInfo(loginResponse.toLoginInfo());
										jumpToPage(HomeActivity.class, null, true);
									}
								}

							}, new ErrorListener() {

								@Override
								public void onErrorResponse(VolleyError error) {
									// TODO Auto-generated method stub

									LogUtils.log(TAG, error.toString());
									ToastHelper.showToast(WelcomeActivity.this, "Õ¯¬Á“Ï≥£");
									finish();
								}
							});
				}

				else {
					finish();
				}
			} else {
				jumpToPage(LoginActivity.class, null, true);
			}
		}

	};
}
