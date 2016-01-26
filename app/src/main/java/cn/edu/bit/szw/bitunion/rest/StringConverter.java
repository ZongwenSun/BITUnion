package cn.edu.bit.szw.bitunion.rest;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import java.io.IOException;

import retrofit.Converter;

/**
 * Created by szw on 2016/1/26.
 */
public class StringConverter implements Converter<String>{
    private static final MediaType MEDIA_TYPE = MediaType.parse("text/plain charset=UTF-8");

    @Override
    public String fromBody(ResponseBody body) throws IOException {
        return body.string();
    }

    @Override
    public RequestBody toBody(String value) {
        return RequestBody.create(MEDIA_TYPE,value);
    }
}
