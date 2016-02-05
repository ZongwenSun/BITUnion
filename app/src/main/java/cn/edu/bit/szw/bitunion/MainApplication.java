package cn.edu.bit.szw.bitunion;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import cn.edu.bit.szw.bitunion.entities.LoginInfo;
import cn.edu.bit.szw.bitunion.global.LoginManager;
import cn.edu.bit.szw.bitunion.global.NetworkManager;
import cn.edu.bit.szw.bitunion.global.SharedPrefrenceUtil;
import cn.edu.bit.szw.bitunion.tools.LogUtils;

public class MainApplication extends Application {
	public static MainApplication instance;
	LoginManager mLoginManager = null;
	NetworkManager mNetworkManager = null;
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
		initImageLoader(getApplicationContext());
		registerReceiver(mNetworkManager, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
		LogUtils.setDebug(true);
		instance = this;
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		unregisterReceiver(mNetworkManager);
	}

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo mLoginInfo) {
		this.loginInfo = mLoginInfo;
	}

	private void initImageLoader(Context context) {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context);
		builder.defaultDisplayImageOptions(defaultOptions);
		ImageLoader.getInstance().init(builder.build());
	}

}
