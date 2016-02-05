package cn.edu.bit.szw.bitunion.network;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by szw on 16-2-4.
 */
public class HttpUtil {
    private static MediaType MEDIA_TYPE_PLAIN = MediaType.parse("text/plain;charset=utf-8");
    private static HttpUtil instance = null;
    OkHttpClient client;


    private HttpUtil() {
        client = new OkHttpClient();
    }

    public static HttpUtil getInstance() {
        if (instance == null) {
            instance = new HttpUtil();
        }
        return instance;
    }


    public OkHttpClient getClient() {
        return client;
    }

    public Call post(String url, String content) {
        return post(null, url, content);
    }

    public Call post(Object tag, String url, String content) {
        RequestBody body = RequestBody.create(MEDIA_TYPE_PLAIN, content);
        Request request = new Request.Builder()
                .url(url)
                .tag(tag)
                .post(body)
                .build();
        return  client.newCall(request);
    }

    public Call post(Object tag, String url, HashMap<String, String> params) {
        String content = mapToJsonString(params);
        return post(tag, url, content);
    }

    public Call post(String url, HashMap<String, String> params) {
        String content = mapToJsonString(params);
        return post(null, url, content);
    }

    private String mapToJsonString(HashMap<String, String> paramsMap) {
        if (paramsMap == null) {
            return "";
        }
        JSONObject jsonObject = new JSONObject();
        for(String key: paramsMap.keySet()) {
            jsonObject.put(key, paramsMap.get(key));
        }
        return jsonObject.toString();
    }

    public Observable<JSONObject> postBu(final Object tag, final String url, final String content) {
        return Observable.create(new Observable.OnSubscribe<JSONObject>() {
            @Override
            public void call(Subscriber<? super JSONObject> subscriber) {
                try {
                    String response = post(tag, url, content)
                            .execute()
                            .body()
                            .string();
                    JSONObject jsonObject = JSONObject.parseObject(response);
                    subscriber.onNext(jsonObject);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

    public Observable<JSONObject> postBu(final Object tag, final String url, final HashMap<String,String> params) {

        return Observable.create(new Observable.OnSubscribe<JSONObject>() {
            @Override
            public void call(Subscriber<? super JSONObject> subscriber) {
                try {
                    String response = post(tag, url, mapToJsonString(params))
                            .execute()
                            .body()
                            .string();
                    JSONObject jsonObject = JSONObject.parseObject(response);
                    subscriber.onNext(jsonObject);
                    subscriber.onCompleted();
                } catch (IOException e) {
                    subscriber.onError(e);
                }
            }
        });
    }

}
