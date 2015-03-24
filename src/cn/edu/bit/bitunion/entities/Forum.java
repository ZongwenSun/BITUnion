package cn.edu.bit.bitunion.entities;

import java.util.ArrayList;
import java.util.List;

public class Forum {
	private TYPE type;
	private String fid;
	private String name;
	private String fup;
	private String icon;
	private String descriptioin;
	private String moderator;
	private String threads;
	private String posts;
	private String onlines;
	private List<Forum> subForumList;

	public Forum() {

	}

	public Forum(TYPE type, String fid, String name) {
		setType(type);
		setFid(fid);
		setName(name);
	}

	public TYPE getType() {
		return type;
	}

	public void setType(TYPE type) {
		this.type = type;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFup() {
		return fup;
	}

	public void setFup(String fup) {
		this.fup = fup;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getDescriptioin() {
		return descriptioin;
	}

	public void setDescriptioin(String descriptioin) {
		this.descriptioin = descriptioin;
	}

	public String getModerator() {
		return moderator;
	}

	public void setModerator(String moderator) {
		this.moderator = moderator;
	}

	public String getThreads() {
		return threads;
	}

	public void setThreads(String threads) {
		this.threads = threads;
	}

	public String getPosts() {
		return posts;
	}

	public void setPosts(String posts) {
		this.posts = posts;
	}

	public String getOnlines() {
		return onlines;
	}

	public void setOnlines(String onlines) {
		this.onlines = onlines;
	}

	public List<Forum> getSubForumList() {
		return subForumList;
	}

	public void setSubForumList(List<Forum> subForumList) {
		this.subForumList = subForumList;
	}

	public void addSubForum(Forum sub) {
		if (subForumList == null) {
			subForumList = new ArrayList<Forum>();
		}
		subForumList.add(sub);
	}

	public static enum TYPE {
		GROUP, FORUM, SUBFORUM;
	}
}
