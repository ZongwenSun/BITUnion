package cn.edu.bit.szw.bitunion.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Forum implements Serializable {
	/**
	 * 
	 */
	public static final long serialVersionUID = -4964662568265048418L;
	public String type;
	public String fid;
	public String name;
	public String fup;
	public String icon;
	public String description;
	public String moderator;
	public String threads;
	public String posts;
	public String onlines;
	public List<Forum> subForumList;

	public void addSubForum(Forum sub) {
		if (subForumList == null) {
			subForumList = new ArrayList<Forum>();
		}
		subForumList.add(sub);
	}
}
