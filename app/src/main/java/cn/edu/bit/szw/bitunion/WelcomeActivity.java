package cn.edu.bit.szw.bitunion;

import android.content.Intent;

import com.alibaba.fastjson.JSONObject;
import com.szw.mvp.presenter.ActivityPresenter;

import cn.edu.bit.szw.bitunion.global.GlobalUrls;
import cn.edu.bit.szw.bitunion.global.LoginManager;
import cn.edu.bit.szw.bitunion.global.ResponseParser;
import cn.edu.bit.szw.bitunion.network.BUApi;
import cn.edu.bit.szw.bitunion.network.HttpUtil;
import cn.edu.bit.szw.bitunion.tools.LogUtils;
import cn.edu.bit.szw.bitunion.tools.ToastHelper;
import cn.edu.bit.szw.bitunion.view.WelcomeView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class WelcomeActivity extends ActivityPresenter<WelcomeView> {
    private static final String TAG = "WelcomeActivity";

    @Override
    protected void onResume() {
        super.onResume();
        login();
    }

    private void login() {
        if (LoginManager.getInstance().hasLogin()) {
            JSONObject body = new JSONObject();
            body.put("action", "login");
            body.put("username", LoginManager.getInstance().getUserName());
            body.put("password", LoginManager.getInstance().getPassword());
           BUApi.request(this, GlobalUrls.getLoginUrl(), body)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<com.alibaba.fastjson.JSONObject>() {
                        @Override
                        public void onCompleted() {}

                        @Override
                        public void onError(Throwable e) {
                            ToastHelper.show(e.getMessage());
                            LogUtils.log("error------" + e.toString());
                        }

                        @Override
                        public void onNext(JSONObject jsonObject) {
                            MainApplication.instance.setLoginInfo(ResponseParser.parseLoginInfo(jsonObject));
                            Intent intent = new Intent(WelcomeActivity.this, HomeFragment.class);
                            startActivity(intent);
                            finish();
                        }
                    });

        } else {
            Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }

}
