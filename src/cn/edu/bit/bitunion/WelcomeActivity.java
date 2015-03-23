package cn.edu.bit.bitunion;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import cn.edu.bit.bitunion.global.LoginManager;

public class WelcomeActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);

		mHandler.sendEmptyMessageDelayed(0, 1000);
	}

	private Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			boolean hasLogin = LoginManager.getInstance(getAppContext()).hasLogin();
			if (hasLogin) {
				jumpToPage(HomeActivity.class, null, true);
			} else {
				jumpToPage(LoginActivity.class, null, true);
			}
		}

	};
}
