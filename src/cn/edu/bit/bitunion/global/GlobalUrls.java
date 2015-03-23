package cn.edu.bit.bitunion.global;

public class GlobalUrls {
	public static final String BASE_URL_IN_SCHOOL = "http://www.bitunion.org/open_api/";
	public static final String BASE_URL_OUT_SCHOOL = "http://out.bitunion.org/open_api/";
	private static String BASE_URL;
	
	public static void setInSchool(boolean isInSchool){
		if (isInSchool) {
			BASE_URL = BASE_URL_IN_SCHOOL;
		}
		else {
			BASE_URL = BASE_URL_OUT_SCHOOL;
		}
	}
	public static String getLoginUrl(){
		return BASE_URL + "bu_logging.php";
	}
	
	public static String getLogoutUrl(){
		return BASE_URL + "bu_logging.php";
	}
	
	public static String getForumListUrl(){
		return BASE_URL + "bu_forum.php";
	}
	
	public static String getThreadListUrl(){
		return BASE_URL + "bu_thread.php";
	}
	
	public static String getPostListUrl(){
		return BASE_URL + "bu_post.php";
	}
}
