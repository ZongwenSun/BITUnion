package cn.edu.bit.szw.bitunion.rest;

import org.json.JSONObject;

import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by szw on 2016/1/24.
 */
public class RestClient {
    private static final String BASE_URL = "http://out.bitunion.org/open_api/";
    private static RestClient mInstance = null;
    private BUApi mBUApi;
    private RestClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverter(String.class, new StringConverter())
                .addConverter(JSONObject.class, new JSONConverter())
                //.addConverterFactory(GsonConverterFactory.create())
                .build();
        mBUApi = retrofit.create(BUApi.class);
    }
    public static RestClient getInstance() {
        if (mInstance == null) {
            mInstance = new RestClient();
        }
        return mInstance;
    }
    public BUApi getApi(){
        return mBUApi;
    }
}
