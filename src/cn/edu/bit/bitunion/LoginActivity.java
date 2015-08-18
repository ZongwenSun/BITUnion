package cn.edu.bit.bitunion;

import org.json.JSONObject;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import cn.edu.bit.bitunion.global.GlobalUrls;
import cn.edu.bit.bitunion.global.LoginManager;
import cn.edu.bit.bitunion.global.RequestJsonFactory;
import cn.edu.bit.bitunion.global.ResponseParser;
import cn.edu.bit.bitunion.network.RequestQueueManager;
import cn.edu.bit.bitunion.tools.LogUtils;
import cn.edu.bit.bitunion.tools.StringUtils;
import cn.edu.bit.bitunion.tools.ToastHelper;

import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

public class LoginActivity extends BaseActivity {
	private static final String TAG = "LoginActivity";
	EditText usernameEdit;
	EditText passwordEdit;
	Button loginBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		setTitle("北理联盟");
		initUI();
	}

	private void initUI() {
		usernameEdit = (EditText) findViewById(R.id.login_username_edit);
		passwordEdit = (EditText) findViewById(R.id.login_password_edit);
		loginBtn = (Button) findViewById(R.id.login_sign_in_button);
		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String username = usernameEdit.getText().toString();
				final String password = passwordEdit.getText().toString();
				if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
					ToastHelper.showToast(LoginActivity.this, "用户名和密码不能为空!");
				} else {
					if (checkConnection()) {
						showLoadingDialog();
						RequestQueueManager.getInstance().postJsonRequest(GlobalUrls.getLoginUrl(), RequestJsonFactory.loginJson(username, password),
								new Listener<JSONObject>() {

									@Override
									public void onResponse(JSONObject response) {
										// TODO Auto-generated method stub
										hideLoadingDialog();

										if (ResponseParser.isSuccess(response)) {
											LoginManager.getInstance().saveLoginInfo(username, password);
											getAppContext().setLoginInfo(ResponseParser.parseLoginInfo(response));
											jumpToPage(HomeActivity.class, null, true);
										} else {
											LogUtils.log(TAG, response.toString());
											ToastHelper.showToast(LoginActivity.this, "用户名或密码错误");
										}
									}

								}, new ErrorListener() {

									@Override
									public void onErrorResponse(VolleyError error) {
										// TODO Auto-generated method stub
										hideLoadingDialog();
										LogUtils.log(TAG, error.toString());
										ToastHelper.showToast(LoginActivity.this, "网络异常");
									}
								});
					}
				}
			}
		});
	}
}
