package cn.edu.bit.szw.bitunion.global;

public class GlobalUrls {
	// public static final String BASE_
	public static final String BASE_URL_IN_SCHOOL = "http://www.bitunion.org/";
	public static final String BASE_URL_OUT_SCHOOL = "http://out.bitunion.org/";
	private static String BASE_API =BASE_URL_OUT_SCHOOL + "open_api/";
	private static String BASE_URL;

	public static void setInSchool(boolean isInSchool) {
		//if (isInSchool) {
		//	BASE_URL = BASE_URL_IN_SCHOOL;
		//} else {
			BASE_URL = BASE_URL_OUT_SCHOOL;
		//}
		BASE_API = BASE_URL + "open_api/";
	}

	public static String getLoginUrl() {
		return BASE_API + "bu_logging.php";
	}

	public static String getLogoutUrl() {
		return BASE_API + "bu_logging.php";
	}

	public static String getForumListUrl() {
		return BASE_API + "bu_forum.php";
	}

	public static String getThreadListUrl() {
		return BASE_API + "bu_thread.php";
	}

	public static String getPostListUrl() {
		return BASE_API + "bu_post.php";
	}

	public static String getNewPostsUrl() {
		return BASE_API + "bu_home.php";
	}

	public static String getImageUrl(String url) {

		return BASE_URL_OUT_SCHOOL + url;
	}
}
