package cn.edu.bit.szw.bitunion;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;

import cn.edu.bit.szw.bitunion.base.BaseActivity;
import cn.edu.bit.szw.bitunion.global.GlobalUrls;
import cn.edu.bit.szw.bitunion.global.LoginManager;
import cn.edu.bit.szw.bitunion.global.ResponseParser;
import cn.edu.bit.szw.bitunion.network.RequestQueueManager;
import cn.edu.bit.szw.bitunion.tools.ToastHelper;

public class WelcomeActivity extends BaseActivity {
	private static final String TAG = "WelcomeActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
			mHandler.sendEmptyMessageDelayed(0, 300);
	}
	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
            if (LoginManager.getInstance().hasLogin()) {
                HashMap<String, String> paramsMap = new HashMap<String, String>();
                paramsMap.put("action", "login");
                paramsMap.put("username", LoginManager.getInstance().getUserName());
                paramsMap.put("password", LoginManager.getInstance().getPassword());

                RequestQueueManager.getInstance().postJsonRequest(GlobalUrls.getLoginUrl(),
                        paramsMap,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onStart() {
                            }

                            @Override
                            public void onSuccess(JSONObject response, boolean isResponseFromCache) {
                                MainApplication.instance.setLoginInfo(ResponseParser.parseLoginInfo(response));
                                startAndFinishSelf(HomeFragment.class, null);
                            }

                            @Override
                            public void onError(VolleyError error) {
                                ToastHelper.showError(error);
                            }

                            @Override
                            public void onFinish() {
                                //  startAndFinishSelf(HomeFragment.class, null);
                            }
                        }
                        ,true);
            }
            else {
			    startFragment(LoginFragment.class, null);
            }
		}
	};

}
