package cn.edu.bit.szw.bitunion.global;

import android.app.Application;
import android.content.Context;
import cn.edu.bit.szw.bitunion.tools.StringUtils;

public class LoginManager {
	private static LoginManager instance;
	private boolean hasLogin;
	private String userName;
	private String password;

	public static LoginManager getInstance() {
		if (instance == null) {
			throw new RuntimeException("LoginManager not initialized");
		}
		return instance;
	}

	public static void init(Application appContext) {
		if (instance == null) {
			instance = new LoginManager(appContext);
		}
	}

	private LoginManager(Context context) {
		loadPreference();
	}

	public void loadPreference() {
		this.hasLogin = SharedPrefrenceUtil.getBoolean("hasLogin", false);
		this.userName = SharedPrefrenceUtil.getString("userName", "");
		this.password = SharedPrefrenceUtil.getString("password", "");
	}

	public void savePreference() {
		SharedPrefrenceUtil.putBoolean("hasLogin", hasLogin).putString("userName", userName).putString("password", password).commit();
	}

	public boolean hasLogin() {
		return hasLogin;
	}

	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public void saveLoginInfo(String userName, String password) {
		if (StringUtils.notEmpty(userName) && StringUtils.notEmpty(password)) {
			this.hasLogin = true;
			this.userName = userName;
			this.password = password;
			savePreference();
		}
	}

}
