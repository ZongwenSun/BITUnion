package cn.edu.bit.szw.bitunion.global;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import cn.edu.bit.szw.bitunion.tools.LogUtils;

/**
 *
 * 
 * @author szw
 * 
 */
public class NetworkManager extends BroadcastReceiver {
	private static final String TAG = "NetworkManager";
	private static NetworkManager instance = null;
	private Context mContext;
	private ConnectivityManager mConnManager;
	private boolean isNetAvailable = false;
	private boolean isConnected = false;

	public static NetworkManager getInstance() {
		if (instance == null) {
			throw new RuntimeException("NetworkManager not initialized!");
		}
		return instance;
	}

	public static void init(Application appContext) {
		if (instance == null) {
			instance = new NetworkManager(appContext);
		}
	}

	private NetworkManager(Context context) {
		mContext = context;
		mConnManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	}

	public void updateNetworkState() {
		NetworkInfo networkInfo = mConnManager.getActiveNetworkInfo();
		if (null == networkInfo) {
			isNetAvailable = false;
			isConnected = false;
		} else {
			isNetAvailable = networkInfo.isAvailable();
			isConnected = networkInfo.isConnected();
			if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
				GlobalUrls.setInSchool(false);
				LogUtils.log(TAG, "��������: mobile");
			} else if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
				if (isCampusWifi()) {
					GlobalUrls.setInSchool(true);
					LogUtils.log(TAG, "��������: wifi in school");
				} else {
					GlobalUrls.setInSchool(false);
					LogUtils.log(TAG, "��������: wifi out school");
				}
			}
		}
	}

	public boolean isNetworkAvailable() {
		return isNetAvailable;
	}

	public boolean isConnected() {
		return isConnected;
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		updateNetworkState();
	}

	public boolean isCampusWifi() {
		WifiManager wifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();
		if (wifiInfo != null) {
			int ipAddress = wifiInfo.getIpAddress();
			if ((ipAddress & 0xFF) == 10) {
				return true;
			} else {
				return false;
			}
		}
		return false;
	}
}
