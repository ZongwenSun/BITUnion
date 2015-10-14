package cn.edu.bit.szw.bitunion.entities;

public class NewPost {
	public String pname;
	public String fname;
	public String author;
	public String tid;
	public String tid_sum;
	public String fid;
	public String fid_sum;
	public LastReply lastReply;

	public static class LastReply {
		public String when;
		public String who;
		public String what;
	}

}
