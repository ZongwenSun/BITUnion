package cn.edu.bit.szw.bitunion;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.szw.mvp.presenter.ActivityPresenter;

import cn.edu.bit.szw.bitunion.global.GlobalUrls;
import cn.edu.bit.szw.bitunion.global.LoginManager;
import cn.edu.bit.szw.bitunion.global.ResponseParser;
import cn.edu.bit.szw.bitunion.network.BUApi;
import cn.edu.bit.szw.bitunion.tools.LogUtils;
import cn.edu.bit.szw.bitunion.tools.ToastHelper;
import cn.edu.bit.szw.bitunion.view.LoginView;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by szw on 16-2-3.
 */
public class LoginActivity extends ActivityPresenter<LoginView>{

    @Override
    public void afterCreateView(Bundle savedInstanceState) {
        super.afterCreateView(savedInstanceState);

        mView.clickLogin()
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        login();
                    }
                });
    }

    private void login() {
        final String username = mView.getUserName();
        final String password = mView.getPassword();
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {
            mView.emptyUsernameOrPassword();
            return;
        }

        JSONObject body = new JSONObject();
            body.put("action", "login");
            body.put("username", username);
            body.put("password", password);
       BUApi.request(this, GlobalUrls.getLoginUrl(), body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<JSONObject>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.log("error------" + e.toString());
                    }

                    @Override
                    public void onNext(JSONObject jsonObject) {
                        ToastHelper.show("hello");

                        MainApplication.instance.setLoginInfo(ResponseParser.parseLoginInfo(jsonObject));
                        LoginManager.getInstance().saveLoginInfo(username, password);
                        Intent intent = new Intent(LoginActivity.this, HomeFragment.class);
                        startActivity(intent);
                    }
                });

    }
}
