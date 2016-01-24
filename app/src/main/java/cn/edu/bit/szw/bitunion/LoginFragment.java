package cn.edu.bit.szw.bitunion;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.squareup.okhttp.ResponseBody;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.edu.bit.szw.bitunion.base.BaseFragment;
import cn.edu.bit.szw.bitunion.global.LoginManager;
import cn.edu.bit.szw.bitunion.rest.RestClient;
import cn.edu.bit.szw.bitunion.tools.ToastHelper;
import cn.edu.bit.szw.bitunion.widgets.LoadingView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginFragment extends BaseFragment {
	public static final int RESULT_OK = 1;
	private static final String TAG = "LoginFragment";
	@Bind(R.id.login_username_edit)
	EditText usernameEdit;
	@Bind(R.id.login_password_edit)
	EditText passwordEdit;
	@Bind(R.id.login_sign_in_button)
	Button loginBtn;
	@Bind(R.id.loading)
	LoadingView mLoadingView;

	@Override
	protected View createContentView(LayoutInflater inflater, Bundle savedInstanceState) {
		mTitle.setText(getString(R.string.login));
		View view = inflater.inflate(R.layout.login_fragment, null);
		ButterKnife.bind(this, view);
		/*loginBtn.setOnClickListener(new OnClickListener() {

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
		})*/;
		return view;
	}

	@OnClick(R.id.login_sign_in_button)
	void login(){
		final String username = usernameEdit.getText().toString();
		final String password = passwordEdit.getText().toString();
		if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
			ToastHelper.showToast(getActivity(), getString(R.string.username_and_pass_should_not_be_empty));
			return;
		}
		mLoadingView.startLoading(true);
		try {

			RestClient.getInstance().getApi().login("login", username, password)
					.subscribeOn(Schedulers.io())
					.observeOn(AndroidSchedulers.mainThread())
					.subscribe(new Subscriber<ResponseBody>() {
						@Override
						public void onCompleted() {
							mLoadingView.stopLoading();
						}

						@Override
						public void onError(Throwable e) {
							ToastHelper.show(e.toString());
							mLoadingView.stopLoading();
						}

						
						@Override
						public void onNext(ResponseBody response) {
							ToastHelper.show("hello");
							//MainApplication.instance.setLoginInfo(ResponseParser.parseLoginInfo(response));
							LoginManager.getInstance().saveLoginInfo(username, password);
							startAndFinishSelf(HomeFragment.class, new Bundle());
						}
					});
		}
		catch (Exception e){
			e.printStackTrace();
			Log.e(TAG, e.toString());
		}

	}

	@Override
	public void reload(int type) {

	}
}
