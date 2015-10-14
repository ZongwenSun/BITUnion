package cn.edu.bit.szw.bitunion.network;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RequestQueueManager {
	private static final int MY_SOCKET_TIMEOUT_MS = 3000;
	private static RequestQueueManager instance = null;
	private RequestQueue mRequestQueue = null;

	private RequestQueueManager(Context context) {
		mRequestQueue = Volley.newRequestQueue(context);
	}

	public static RequestQueueManager getInstance() {
		if (instance == null) {
			throw new RuntimeException("RequestQueueManager not initialized");
		}
		return instance;
	}

	public static void init(Context context) {
		if (instance == null) {
			instance = new RequestQueueManager(context);
		}
	}

	public RequestQueue getRequestQueue() {
		return mRequestQueue;
	}

	public void postJsonRequest(final String url, final HashMap<String, String> paramsMap, Listener<JSONObject> listener) {
		postJsonRequest(url, paramsMap, listener, false);
	}

	public void postJsonRequest(final String url, final HashMap<String, String> paramsMap, Listener<JSONObject> listener, boolean shouldCache) {
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parseParamsMap(paramsMap), listener);
		request.setShouldCache(shouldCache);
		mRequestQueue.add(request);
	}



	private JSONObject parseParamsMap(HashMap<String, String> paramsMap) {
		JSONObject json = new JSONObject();
		try {
			for (String key: paramsMap.keySet()) {
				json.put(key, paramsMap.get(key));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	public void start() {
		mRequestQueue.start();
	}

	public void stop() {
		mRequestQueue.stop();
	}
}
