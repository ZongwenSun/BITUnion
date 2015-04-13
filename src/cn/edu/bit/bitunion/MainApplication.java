package cn.edu.bit.bitunion;

import java.io.File;

import android.app.Application;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import cn.edu.bit.bitunion.entities.LoginInfo;
import cn.edu.bit.bitunion.global.LoginManager;
import cn.edu.bit.bitunion.global.NetworkManager;
import cn.edu.bit.bitunion.network.RequestQueueManager;
import cn.edu.bit.bitunion.tools.LogUtils;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class MainApplication extends Application {
	LoginManager mLoginManager = null;
	NetworkManager mNetworkManager = null;
	RequestQueueManager mRequestQueueManager = null;
	private LoginInfo loginInfo;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mLoginManager = LoginManager.getInstance(this);
		mNetworkManager = NetworkManager.getInstance(this);
		mRequestQueueManager = RequestQueueManager.getInstance(this);
		mRequestQueueManager.start();

		// ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));
		initImageLoader(getApplicationContext());
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

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo mLoginInfo) {
		this.loginInfo = mLoginInfo;
	}

	// private void initImageLoader() {
	// HttpParams params = new BasicHttpParams();
	// // Turn off stale checking. Our connections break all the time anyway,
	// // and it's not worth it to pay the penalty of checking every time.
	// HttpConnectionParams.setStaleCheckingEnabled(params, false);
	// // Default connection and socket timeout of 10 seconds. Tweak to taste.
	// HttpConnectionParams.setConnectionTimeout(params, 10 * 1000);
	// HttpConnectionParams.setSoTimeout(params, 10 * 1000);
	// HttpConnectionParams.setSocketBufferSize(params, 8192);
	//
	// // Don't handle redirects -- return them to the caller. Our code
	// // often wants to re-POST after a redirect, which we must do ourselves.
	// HttpClientParams.setRedirecting(params, false);
	// // Set the specified user agent and register standard protocols.
	// HttpProtocolParams.setUserAgent(params, "some_randome_user_agent");
	// SchemeRegistry schemeRegistry = new SchemeRegistry();
	// schemeRegistry.register(new Scheme("http",
	// PlainSocketFactory.getSocketFactory(), 80));
	// schemeRegistry.register(new Scheme("https",
	// SSLSocketFactory.getSocketFactory(), 443));
	//
	// ClientConnectionManager manager = new ThreadSafeClientConnManager(params,
	// schemeRegistry);
	//
	// File cacheDir = StorageUtils.getCacheDirectory(this);
	// ImageLoaderConfiguration config = new
	// ImageLoaderConfiguration.Builder(this).defaultDisplayImageOptions(DisplayImageOptions.createSimple())
	// .discCache(new
	// UnlimitedDiscCache(cacheDir)).threadPoolSize(1).memoryCache(new
	// WeakMemoryCache())
	// .imageDownloader(new HttpClientImageDownloader(this, new
	// DefaultHttpClient(manager, params))).build();
	// ImageLoader.getInstance().init(config);
	// }

	private void initImageLoader(Context context) {
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder().cacheInMemory(true).cacheOnDisc(true).build();
		File cacheDir = StorageUtils.getCacheDirectory(context);
		ImageLoaderConfiguration.Builder builder = new ImageLoaderConfiguration.Builder(context);
		builder.defaultDisplayImageOptions(defaultOptions);
		ImageLoader.getInstance().init(builder.build());
	}

}
