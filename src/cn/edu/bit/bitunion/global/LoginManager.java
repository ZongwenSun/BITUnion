package cn.edu.bit.bitunion.global;

import cn.edu.bit.bitunion.tools.StringUtils;
import android.content.Context;
import android.content.SharedPreferences;

public class LoginManager{
	private static final String LOGIN_SHARED_PREFERENCE = "login_info";
	private static LoginManager instance;
	SharedPreferences mPreferences;
	private boolean hasLogin;
	private String userName;
	private String password;
	
	public static LoginManager getInstance(Context context){
		if (instance == null) {
			synchronized (LoginManager.class) {
				if (instance == null) {
					instance = new LoginManager(context);
				}
			}
		}
		return instance;
	}
	private LoginManager(Context context){
		mPreferences = context.getSharedPreferences(LOGIN_SHARED_PREFERENCE, Context.MODE_PRIVATE);
		loadPreference();
	}
	
	public void loadPreference() {
		this.hasLogin = mPreferences.getBoolean("hasLogin", false);
		this.userName = mPreferences.getString("userName", "");
		this.password = mPreferences.getString("password", "");
	}
	
	public void savePreference(){
		mPreferences.edit().putBoolean("hasLogin", hasLogin)
							.putString("userName", userName)
							.putString("password", password)
							.commit();
	}
	public boolean hasLogin(){
		return hasLogin;
	}
	public String getUserName() {
		return userName;
	}
	public String getPassword() {
		return password;
	}
	
	public void saveLoginInfo(String userName, String password){
		if (StringUtils.notEmpty(userName) && StringUtils.notEmpty(password) ) {
			this.hasLogin = true;
			this.userName = userName;
			this.password = password;
			savePreference();
		}
	}
	
}
