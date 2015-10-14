package cn.edu.bit.szw.bitunion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;

import cn.edu.bit.szw.bitunion.base.BaseFragment;
import cn.edu.bit.szw.bitunion.global.GlobalUrls;
import cn.edu.bit.szw.bitunion.global.LoginManager;
import cn.edu.bit.szw.bitunion.global.ResponseParser;
import cn.edu.bit.szw.bitunion.network.RequestQueueManager;
import cn.edu.bit.szw.bitunion.tools.StringUtils;
import cn.edu.bit.szw.bitunion.tools.ToastHelper;
import cn.edu.bit.szw.bitunion.widgets.LoadingView;

public class LoginFragment extends BaseFragment {
	public static final int RESULT_OK = 1;
	private static final String TAG = "LoginFragment";
	EditText usernameEdit;
	EditText passwordEdit;
	Button loginBtn;
	LoadingView mLoadingView;

	@Override
	protected View createContentView(LayoutInflater inflater, Bundle savedInstanceState) {
		mTitle.setText(getString(R.string.login));
		View view = inflater.inflate(R.layout.login_fragment, null);
		mLoadingView = (LoadingView) view.findViewById(R.id.loading);
		usernameEdit = (EditText) view.findViewById(R.id.login_username_edit);
		passwordEdit = (EditText) view.findViewById(R.id.login_password_edit);
		loginBtn = (Button) view.findViewById(R.id.login_sign_in_button);
		loginBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String username = usernameEdit.getText().toString();
				final String password = passwordEdit.getText().toString();
				if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
					ToastHelper.showToast(getActivity(), getString(R.string.username_and_pass_should_not_be_empty));
				} else {
					HashMap<String, String> paramsMap = new HashMap<String, String>();
					paramsMap.put("action", "login");
					paramsMap.put("username", username);
					paramsMap.put("password", password);

					RequestQueueManager.getInstance().postJsonRequest(GlobalUrls.getLoginUrl(),
							paramsMap,
							new Listener<JSONObject>() {
								@Override
								public void onStart() {
									mLoadingView.startLoading(true);
								}

								@Override
								public void onSuccess(JSONObject response, boolean isResponseFromCache) {
									MainApplication.instance.setLoginInfo(ResponseParser.parseLoginInfo(response));
									LoginManager.getInstance().saveLoginInfo(username, password);
									startAndFinishSelf(HomeFragment.class, new Bundle());
								}

								@Override
								public void onError(VolleyError error) {
									mLoadingView.showErrorAsToast(error.toString());
								}

								@Override
								public void onFinish() {
									mLoadingView.stopLoading();
								}
							}
					);
				}
			}
		});
		return view;
	}

	@Override
	public void reload(int type) {
		
	}
}
