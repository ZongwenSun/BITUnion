package cn.edu.bit.szw.bitunion.network;

import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cn.edu.bit.szw.bitunion.tools.LogUtils;

/**
 * Created by szw on 15-10-11.
 */
public class CustomJsonObjectRequst extends JsonObjectRequest {
    public CustomJsonObjectRequst(String url, JSONObject jsonRequest, Response.Listener listener) {
        this(url, jsonRequest, listener, false);
    }
    public CustomJsonObjectRequst(String url, JSONObject jsonRequest, Response.Listener listener, boolean useCache){
        super(Method.POST, url, jsonRequest, listener);
        setShouldCache(useCache);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data,
                    HttpHeaderParser.parseCharset(response.headers, PROTOCOL_CHARSET));
            JSONObject jsonObject = new JSONObject(jsonString);
            String result = jsonObject.getString("result");
            LogUtils.log("response result : "+result);
            if (result.equals("success")){
                return Response.success(jsonObject, HttpHeaderParser.parseCacheHeaders(response));
            }
            else {
                BUError buError = new BUError(response);
                buError.setErrorType(BUError.ErrorType.CUSTOM);
                return Response.error(buError);
            }

        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        if (volleyError instanceof BUError){
            return volleyError;
        }
        BUError buError = new BUError(volleyError.networkResponse);
        if (volleyError instanceof NoConnectionError) {
            buError.setErrorType(BUError.ErrorType.NOCONNECTION);
        } else if (volleyError instanceof ServerError) {
            buError.setErrorType(BUError.ErrorType.SERVER);
        } else if (volleyError instanceof TimeoutError) {
            buError.setErrorType(BUError.ErrorType.TIMEOUT);
        } else if (volleyError instanceof ParseError) {
            buError.setErrorType(BUError.ErrorType.PARSE);
        }
        return buError;
    }
}
