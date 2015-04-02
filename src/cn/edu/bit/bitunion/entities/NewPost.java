package cn.edu.bit.bitunion.entities;

public class NewPost {
	private String pname;
	private String fname;
	private String author;
	private String tid;
	private String tid_sum;
	private String fid;
	private String fid_sum;
	private LastReply lastReply;

	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getTid() {
		return tid;
	}

	public void setTid(String tid) {
		this.tid = tid;
	}

	public String getTid_sum() {
		return tid_sum;
	}

	public void setTid_sum(String tid_sum) {
		this.tid_sum = tid_sum;
	}

	public String getFid() {
		return fid;
	}

	public void setFid(String fid) {
		this.fid = fid;
	}

	public String getFid_sum() {
		return fid_sum;
	}

	public void setFid_sum(String fid_sum) {
		this.fid_sum = fid_sum;
	}

	public LastReply getLastReply() {
		return lastReply;
	}

	public void setLastReply(LastReply lastReply) {
		this.lastReply = lastReply;
	}

	class LastReply {
		private String when;
		private String who;
		private String what;

		public String getWhen() {
			return when;
		}

		public void setWhen(String when) {
			this.when = when;
		}

		public String getWho() {
			return who;
		}

		public void setWho(String who) {
			this.who = who;
		}

		public String getWhat() {
			return what;
		}

		public void setWhat(String what) {
			this.what = what;
		}

	}

}
