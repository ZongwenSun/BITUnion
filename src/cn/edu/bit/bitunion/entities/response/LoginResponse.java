package cn.edu.bit.bitunion.entities.response;

import cn.edu.bit.bitunion.entities.LoginInfo;

public class LoginResponse extends GeneralResponse {
	private String uid;
	private String username;
	private String session;
	private String status;
	private String credit;
	private String lastactivity;

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getSession() {
		return session;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getLastactivity() {
		return lastactivity;
	}

	public void setLastactivity(String lastactivity) {
		this.lastactivity = lastactivity;
	}

	public LoginInfo toLoginInfo() {
		LoginInfo loginInfo = new LoginInfo(uid, username, session, status, credit, lastactivity);
		return loginInfo;
	}
}
