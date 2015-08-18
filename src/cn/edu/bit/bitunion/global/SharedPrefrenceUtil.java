package cn.edu.bit.bitunion.global;

import java.util.Map;
import java.util.Set;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPrefrenceUtil {
	private static final String PREFRENCE_FILE_STRING = "pref";
	private static SharedPreferences mSp = null;

	public static void init(Application appContext) {
		if (mSp == null) {
			mSp = appContext.getSharedPreferences(PREFRENCE_FILE_STRING, Context.MODE_PRIVATE);
		}
	}

	public static String getString(String key, String defValue) {
		return mSp.getString(key, defValue);
	}

	public static Set<String> getStringSet(String key, Set<String> defValues) {
		return mSp.getStringSet(key, defValues);
	}

	public static Map<String, ?> getAll() {
		// TODO Auto-generated method stub
		return mSp.getAll();
	}

	public static int getInt(String key, int defValue) {
		// TODO Auto-generated method stub
		return mSp.getInt(key, defValue);
	}

	public static long getLong(String key, long defValue) {
		// TODO Auto-generated method stub
		return mSp.getLong(key, defValue);
	}

	public static float getFloat(String key, float defValue) {
		// TODO Auto-generated method stub
		return mSp.getFloat(key, defValue);
	}

	public static boolean getBoolean(String key, boolean defValue) {
		// TODO Auto-generated method stub
		return mSp.getBoolean(key, defValue);
	}

	public static boolean contains(String key) {
		// TODO Auto-generated method stub
		return mSp.contains(key);
	}

	public static Editor putString(String key, String value) {
		// TODO Auto-generated method stub
		return mSp.edit().putString(key, value);
	}

	public static Editor putStringSet(String key, Set<String> values) {
		// TODO Auto-generated method stub
		return mSp.edit().putStringSet(key, values);
	}

	public static Editor putInt(String key, int value) {
		// TODO Auto-generated method stub
		return mSp.edit().putInt(key, value);
	}

	public static Editor putLong(String key, long value) {
		// TODO Auto-generated method stub
		return mSp.edit().putLong(key, value);
	}

	public static Editor putFloat(String key, float value) {
		// TODO Auto-generated method stub
		return mSp.edit().putFloat(key, value);
	}

	public static Editor putBoolean(String key, boolean value) {
		// TODO Auto-generated method stub
		return mSp.edit().putBoolean(key, value);
	}

	public static Editor remove(String key) {
		// TODO Auto-generated method stub
		return mSp.edit().remove(key);
	}

	public static Editor clear() {
		// TODO Auto-generated method stub
		return mSp.edit().clear();
	}

}
