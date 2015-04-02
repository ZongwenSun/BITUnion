package cn.edu.bit.bitunion.entities;

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
		return JSON.parseObject(response.toString(), LoginInfo.class);
	}

}
