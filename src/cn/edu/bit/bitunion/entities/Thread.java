package cn.edu.bit.bitunion.entities;

public class Thread {
	private String tid;
	private String author;
	private String authorid;
	private String subject;
	private String dateline;
	private String lastpost;
	private String lastposter;
	private String views;
	private String replies;
	public String getTid() {
		return tid;
	}
	public void setTid(String tid) {
		this.tid = tid;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getAuthorid() {
		return authorid;
	}
	public void setAuthorid(String authorid) {
		this.authorid = authorid;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getDateline() {
		return dateline;
	}
	public void setDateline(String dateline) {
		this.dateline = dateline;
	}
	public String getLastpost() {
		return lastpost;
	}
	public void setLastpost(String lastpost) {
		this.lastpost = lastpost;
	}
	public String getLastposter() {
		return lastposter;
	}
	public void setLastposter(String lastposter) {
		this.lastposter = lastposter;
	}
	public String getViews() {
		return views;
	}
	public void setViews(String views) {
		this.views = views;
	}
	public String getReplies() {
		return replies;
	}
	public void setReplies(String replies) {
		this.replies = replies;
	}
}
