package cn.edu.bit.szw.bitunion;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

import cn.edu.bit.szw.bitunion.entities.LoginInfo;
import cn.edu.bit.szw.bitunion.global.GlobalUrls;
import cn.edu.bit.szw.bitunion.global.LoginManager;
import cn.edu.bit.szw.bitunion.global.NetworkManager;
import cn.edu.bit.szw.bitunion.global.ResponseParser;
import cn.edu.bit.szw.bitunion.global.SharedPrefrenceUtil;
import cn.edu.bit.szw.bitunion.network.RequestQueueManager;
import cn.edu.bit.szw.bitunion.tools.LogUtils;
import cn.edu.bit.szw.bitunion.tools.ToastHelper;

public class MainApplication extends Application {
	public static MainApplication instance;
	LoginManager mLoginManager = null;
	NetworkManager mNetworkManager = null;
	RequestQueueManager mRequestQueueManager = null;
	private LoginInfo loginInfo;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		SharedPrefrenceUtil.init(this);
		LoginManager.init(this);
		mLoginManager = LoginManager.getInstance();
		NetworkManager.init(this);
		mNetworkManager = NetworkManager.getInstance();
		RequestQueueManager.init(this);
		mRequestQueueManager = RequestQueueManager.getInstance();
		mRequestQueueManager.start();
		// ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
		initImageLoader(getApplicationContext());
		registerReceiver(mNetworkManager, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
		LogUtils.setDebug(true);
		instance = this;
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		mRequestQueueManager.stop();
		unregisterReceiver(mNetworkManager);
	}

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo mLoginInfo) {
		this.loginInfo = mLoginInfo;
	}

	public void autoLogin() {
		if (LoginManager.getInstance().hasLogin()) {
			HashMap<String, String> paramsMap = new HashMap<String, String>();
			paramsMap.put("action", "login");
			paramsMap.put("username", LoginManager.getInstance().getUserName());
			paramsMap.put("password", LoginManager.getInstance().getPassword());

			RequestQueueManager.getInstance().postJsonRequest(GlobalUrls.getLoginUrl(),
					paramsMap,
					new Response.Listener<JSONObject>() {
						@Override
						public void onStart() {}

						@Override
						public void onSuccess(JSONObject response, boolean isResponseFromCache) {
							MainApplication.instance.setLoginInfo(ResponseParser.parseLoginInfo(response));
						}

						@Override
						public void onError(VolleyError error) {
							ToastHelper.showError(error);
						}

						@Override
						public void onFinish() {}
					}
			);
		}
	}
	private void initImageLoader(Context context) {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		File cacheDir = StorageUtils.getCacheDirectory(context);
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context);
		builder.defaultDisplayImageOptions(defaultOptions);
		ImageLoader.getInstance().init(builder.build());
	}

}
