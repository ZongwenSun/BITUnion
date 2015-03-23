package cn.edu.bit.bitunion;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import cn.edu.bit.bitunion.global.LoginManager;
import cn.edu.bit.bitunion.global.NetworkManager;
import cn.edu.bit.bitunion.network.RequestQueueManager;
import cn.edu.bit.bitunion.tools.LogUtils;

public class MainApplication extends Application {
	LoginManager mLoginManager = null;
	NetworkManager mNetworkManager = null;
	RequestQueueManager mRequestQueueManager = null;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mLoginManager = LoginManager.getInstance(this);
		mNetworkManager = NetworkManager.getInstance(this);
		mRequestQueueManager = RequestQueueManager.getInstance(this);
		mRequestQueueManager.start();
		registerReceiver(mNetworkManager, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
		LogUtils.setDebug(true);
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		mRequestQueueManager.stop();
		unregisterReceiver(mNetworkManager);
	}

}
