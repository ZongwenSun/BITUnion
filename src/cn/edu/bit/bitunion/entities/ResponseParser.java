package cn.edu.bit.bitunion.entities;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.alibaba.fastjson.JSON;

public class ResponseParser {
	public static boolean isSuccess(JSONObject response) {
		try {
			if (response.getString("result").equalsIgnoreCase("success")) {
				return true;
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static LoginInfo parseLoginResponse(JSONObject response) {
		LoginInfo result = JSON.parseObject(response.toString(), LoginInfo.class);
		Decoder.decode(result);
		return result;
	}

	public static List<NewPost> parseNewPostResponse(JSONObject response) {
		List<NewPost> result = new ArrayList<NewPost>();
		JSONArray array;
		try {
			array = response.getJSONArray("newlist");
			for (int i = 0; i < array.length(); i++) {
				NewPost post = JSON.parseObject(array.getJSONObject(i).toString(), NewPost.class);
				Decoder.decode(post);
				result.add(post);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
