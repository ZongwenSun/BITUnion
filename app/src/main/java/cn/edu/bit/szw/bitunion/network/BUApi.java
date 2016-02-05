package cn.edu.bit.szw.bitunion.network;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.ObjectOutputStream;

import cn.edu.bit.szw.bitunion.MainApplication;
import cn.edu.bit.szw.bitunion.entities.LoginInfo;
import cn.edu.bit.szw.bitunion.global.GlobalUrls;
import cn.edu.bit.szw.bitunion.global.LoginManager;
import cn.edu.bit.szw.bitunion.global.ResponseParser;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by szw on 16-2-5.
 */
public class BUApi {

    public static Observable<JSONObject> request(Object tag, String url, JSONObject body) {
        return HttpUtil.getInstance()
                .postBu(tag, url, body.toString())
                .flatMap(ErrorHandler);
    }

    public static Observable<JSONObject> loginAndRequest(final Object tag, final String url, final JSONObject oriBody) {
        JSONObject loginBody = new JSONObject();
        loginBody.put("action", "login");
        loginBody.put("username", LoginManager.getInstance().getUserName());
        loginBody.put("password", LoginManager.getInstance().getPassword());
        return BUApi.request(tag, GlobalUrls.getLoginUrl(), loginBody)
                .flatMap(new Func1<JSONObject, Observable<JSONObject>>() {
                    @Override
                    public Observable<JSONObject> call(JSONObject jsonObject) {
                        LoginInfo loginInfo = ResponseParser.parseLoginInfo(jsonObject);
                        MainApplication.instance.setLoginInfo(loginInfo);
                        oriBody.put("session", loginInfo.session);
                        oriBody.put("username", loginInfo.username);
                        return request(tag, url, oriBody);
                    }
                });

    }


    public static Func1<JSONObject, Observable<JSONObject>> ErrorHandler= new Func1<JSONObject, Observable<JSONObject>>() {
        @Override
        public Observable<JSONObject> call(final JSONObject response) {
            return Observable.create(new Observable.OnSubscribe<JSONObject>() {
                @Override
                public void call(Subscriber<? super JSONObject> subscriber) {
                    String result = response.getString("result");
                    String msg = response.getString("msg");

                    if (TextUtils.equals(result, "success")) {
                        subscriber.onNext(response);
                        subscriber.onCompleted();
                    } else {
                        //需要重新登陆
                        if (TextUtils.equals(msg, "IP+logged")) {
                            subscriber.onError(BuError.SESSION);
                        }
                        else {
                            subscriber.onError(BuError.UNKNOWN);
                        }
                    }
                }
            });
        }
    };

}
