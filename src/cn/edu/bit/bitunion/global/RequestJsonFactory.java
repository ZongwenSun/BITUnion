package cn.edu.bit.bitunion.global;

import org.json.JSONException;
import org.json.JSONObject;

public class RequestJsonFactory {
	public static JSONObject loginJson(String username, String password) {
		JSONObject json = new JSONObject();
		try {
			json.put("action", "login");
			json.put("username", username);
			json.put("password", password);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	public static JSONObject logoutJson(String username, String password,
			String session) {
		JSONObject json = new JSONObject();
		try {
			json.put("action", "logout");
			json.put("username", username);
			json.put("password", password);
			json.put("session", session);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	public static JSONObject forumListJson(String username, String session) {
		JSONObject json = new JSONObject();
		try {
			json.put("action", "forum");
			json.put("username", username);
			json.put("session", session);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	public static JSONObject newPostsJson(String username, String session) {
		JSONObject json = new JSONObject();
		try {
			json.put("username", username);
			json.put("session", session);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}

	public static JSONObject PostListJson(String username, String session,
			String tid, String from, String to) {
		JSONObject json = new JSONObject();
		try {
			json.put("action", "post");
			json.put("username", username);
			json.put("session", session);
			json.put("tid", tid);
			json.put("from", from);
			json.put("to", to);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
}
