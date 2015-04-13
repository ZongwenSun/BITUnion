package cn.edu.bit.bitunion.global;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.bit.bitunion.entities.Decoder;
import cn.edu.bit.bitunion.entities.Forum;
import cn.edu.bit.bitunion.entities.LoginInfo;
import cn.edu.bit.bitunion.entities.NewPost;
import cn.edu.bit.bitunion.tools.LogUtils;

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

	public static LoginInfo parseLoginInfo(JSONObject response) {
		LoginInfo result = JSON.parseObject(response.toString(), LoginInfo.class);
		Decoder.decode(result);
		return result;
	}

	public static List<NewPost> parseNewPostList(JSONObject response) {
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

	public static List<Forum> parseForumList(JSONObject json) {
		List<Forum> groupForumList = new ArrayList<Forum>();
		JSONObject forumlist;
		try {
			forumlist = json.getJSONObject("forumslist");
			String[] forumIds = { "13", "129", "166", "16", "2" };
			for (String forumId : forumIds) {
				JSONObject forum = forumlist.getJSONObject(forumId);
				JSONObject group = forum.getJSONObject("main");
				String type = URLDecoder.decode(group.getString("type"));
				String fid = URLDecoder.decode(group.getString("fid"));
				String name = URLDecoder.decode(group.getString("name"));
				Forum groupForum = new Forum(type, fid, name);
				groupForumList.add(groupForum);
				for (int id = 0; forum.has(String.valueOf(id)); id++) {
					JSONObject mainforums = forum.getJSONObject(String.valueOf(id));
					JSONArray mainArray = mainforums.getJSONArray("main");
					JSONObject main = mainArray.getJSONObject(0);
					Forum mainForum = jsonToForum(main);
					groupForum.addSubForum(mainForum);
					if (mainforums.has("sub")) {
						JSONArray subArray = mainforums.getJSONArray("sub");
						for (int i = 0; i < subArray.length(); i++) {
							JSONObject sub = subArray.getJSONObject(i);
							Forum subForum = jsonToForum(sub);
							mainForum.addSubForum(subForum);
							LogUtils.log("responseParser", subForum.getName());
						}
					}

				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return groupForumList;
	}

	private static Forum jsonToForum(JSONObject obj) throws JSONException {
		String type = URLDecoder.decode(obj.getString("type"));
		String fid = URLDecoder.decode(obj.getString("fid"));
		String name = URLDecoder.decode(obj.getString("name"));
		String fup = URLDecoder.decode(obj.getString("fup"));
		String icon = URLDecoder.decode(obj.getString("icon"));
		String description = URLDecoder.decode(obj.getString("description"));
		String moderator = URLDecoder.decode(obj.getString("moderator"));
		String threads = URLDecoder.decode(obj.getString("threads"));
		String posts = URLDecoder.decode(obj.getString("posts"));
		String onlines = URLDecoder.decode(obj.getString("onlines"));
		Forum forum = new Forum(type, fid, name, fup, icon, description, moderator, threads, posts, onlines);
		return forum;
	}

}
