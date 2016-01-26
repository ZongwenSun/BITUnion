package cn.edu.bit.szw.bitunion.rest;


import org.json.JSONObject;

import retrofit.http.Body;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by szw on 2016/1/24.
 */
public interface BUApi {
    @POST("bu_logging.php")
    Observable<JSONObject> login(
            @Body String body);
    //@POST("/")

}
