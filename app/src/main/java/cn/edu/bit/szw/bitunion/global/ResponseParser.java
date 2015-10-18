package cn.edu.bit.szw.bitunion.global;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.edu.bit.szw.bitunion.entities.Decoder;
import cn.edu.bit.szw.bitunion.entities.Forum;
import cn.edu.bit.szw.bitunion.entities.LoginInfo;
import cn.edu.bit.szw.bitunion.entities.NewPost;
import cn.edu.bit.szw.bitunion.entities.Post;
import cn.edu.bit.szw.bitunion.tools.LogUtils;

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
		LoginInfo result = JSON.parseObject(response.toString(),
				LoginInfo.class);
		Decoder.decode(result);
		return result;
	}

	public static List<NewPost> parseNewPostList(JSONObject response) {
		List<NewPost> result = new ArrayList<NewPost>();
		JSONArray array;
		try {
			array = response.getJSONArray("newlist");
			for (int i = 0; i < array.length(); i++) {
				NewPost post = JSON.parseObject(array.getJSONObject(i)
						.toString(), NewPost.class);
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
				Forum groupForum = new Forum();
				groupForum.type = URLDecoder.decode(group.getString("type"));
				groupForum.fid = URLDecoder.decode(group.getString("fid"));
				groupForum.name = URLDecoder.decode(group.getString("name"));
				groupForumList.add(groupForum);
				for (int id = 0; forum.has(String.valueOf(id)); id++) {
					JSONObject mainforums = forum.getJSONObject(String
							.valueOf(id));
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
							LogUtils.log("responseParser", subForum.name);
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
		Forum forum = new Forum();
		
		forum.type = URLDecoder.decode(obj.getString("type"));
		forum.fid = URLDecoder.decode(obj.getString("fid"));
		forum.name = URLDecoder.decode(obj.getString("name"));
		forum.fup = URLDecoder.decode(obj.getString("fup"));
		forum.icon = URLDecoder.decode(obj.getString("icon"));
		forum.description = URLDecoder.decode(obj.getString("description"));
		forum.moderator = URLDecoder.decode(obj.getString("moderator"));
		forum.threads = URLDecoder.decode(obj.getString("threads"));
		forum.posts = URLDecoder.decode(obj.getString("posts"));
		forum.onlines = URLDecoder.decode(obj.getString("onlines"));
		return forum;
	}

	public static List<Post> parsePostResponse(JSONObject response) {
		List<Post> result = new ArrayList<Post>();
		JSONArray array;
		try {
			array = response.getJSONArray("postlist");
			for (int i = 0; i < 20; i++) {
				Post post = JSON.parseObject(array.getJSONObject(i).toString(),
						Post.class);
				Decoder.decode(post);
				post.parseMessage();
				result.add(post);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
}
