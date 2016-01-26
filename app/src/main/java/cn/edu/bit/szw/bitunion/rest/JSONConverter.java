package cn.edu.bit.szw.bitunion.rest;

import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.ResponseBody;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit.Converter;

/**
 * Created by szw on 2016/1/26.
 */
public class JSONConverter implements Converter<JSONObject> {
    @Override
    public JSONObject fromBody(ResponseBody body) throws IOException {
        String str = body.string();
        body.close();
        try {
            JSONObject obj = new JSONObject(str);
            return obj;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public RequestBody toBody(JSONObject value) {
        return null;
    }
}
