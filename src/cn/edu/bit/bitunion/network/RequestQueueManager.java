package cn.edu.bit.bitunion.network;

import org.json.JSONObject;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

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

	public void postJsonRequest(String url, JSONObject jsonRequest, Listener<JSONObject> listener, ErrorListener errorListener) {
		JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonRequest, listener, errorListener);
		request.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
		mRequestQueue.add(request);
	}

	public void start() {
		mRequestQueue.start();
	}

	public void stop() {
		mRequestQueue.stop();
	}
}
