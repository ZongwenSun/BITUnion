package cn.edu.bit.szw.bitunion.rest;


import com.squareup.okhttp.ResponseBody;

import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by szw on 2016/1/24.
 */
public interface BUApi {
    @POST("bu_logging.php")
    Observable<ResponseBody> login(
            @Query("action") String action,
            @Query("username")String username,
            @Query("password") String password);
    //@POST("/")

}
