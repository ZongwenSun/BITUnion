package cn.edu.bit.szw.bitunion.base;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.HashMap;

import cn.edu.bit.szw.bitunion.HomeFragment;
import cn.edu.bit.szw.bitunion.MainApplication;
import cn.edu.bit.szw.bitunion.global.GlobalUrls;
import cn.edu.bit.szw.bitunion.global.LoginManager;
import cn.edu.bit.szw.bitunion.global.ResponseParser;
import cn.edu.bit.szw.bitunion.network.RequestQueueManager;
import cn.edu.bit.szw.bitunion.tools.ToastHelper;

public class BaseActivity extends FragmentActivity {
	@Override
	protected void onCreate(Bundle savedInstance) {
		super.onCreate(savedInstance);
	}
	protected void startFragment(Class<? extends Fragment> cls, Bundle bundle) {
		Intent intent = new Intent();
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		intent.setAction("cn.edu.bit.szw.bitunion.rootactivity");
		intent.setData(Uri.parse("fragment://" + cls.getName()));
		startActivity(intent);
	}

	protected void startAndFinishSelf(final Class<? extends Fragment> cls, Bundle bundle) {
		startFragment(cls, bundle);
		finish();
	}

	protected void startFragmentForResult(final Class<? extends Fragment> cls, Bundle bundle, int requestCode) {
		Intent intent = new Intent();
		if (bundle != null) {
			intent.putExtras(bundle);
		}
		intent.setAction("cn.edu.bit.szw.bitunion.rootactivity");
		intent.setData(Uri.parse("fragment://" + cls.getName()));
		startActivityForResult(intent, requestCode);
	}
}