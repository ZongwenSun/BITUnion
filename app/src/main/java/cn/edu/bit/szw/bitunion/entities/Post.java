package cn.edu.bit.szw.bitunion.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.edu.bit.szw.bitunion.global.GlobalUrls;

public class Post {
	public String pid;
	public String fid;
	public String tid;
	public String aid;
	public String icon;
	public String author;
	public String authorid;
	public String subject;
	public String dateline;
	public String message;
	public String usesig;
	public String bbcodeoff;
	public String smileyoff;
	public String parseurloff;
	public String score;
	public String rate;
	public String ratetimes;
	public String pstatus;
	public String lastedit;
	public String postsource;
	public String aaid;
	public String creditsrequire;
	public String filetype;
	public String filename;
	public String attachment;
	public String filesize;
	public String downloads;
	public String uid;
	public String username;
	public String avatar;
	public String epid;
	public String maskpost;

	public List<Quote> quotes;

	public void parseMessage() {
		message = parseEmbbedImages2(message);
		message = parseQuotes(message);
		message = parseParagraph(message);
	}

	// 去掉嵌在文本中的图片和表情
	public String parseEmbbedImages(String message) {
		Pattern pattern = Pattern.compile("<img src=\"([^>]+)\"[^>]*>");
		// <img src="../images/smilies/25.gif"
		Matcher matcher = pattern.matcher(message);
		while (matcher.find()) {
			String src = matcher.group(0);
			message = message.replace(src, "...");
		}
		return message;
	}

	// 将嵌在文本中的图片和表情转为绝对路径
	public String parseEmbbedImages2(String content) {
		// Pattern pattern = Pattern.compile("<img src=\"(\\.\\./[^\"]*)\" ");
		// // <img src="../images/smilies/25.gif"
		// Matcher matcher = pattern.matcher(content);
		// while (matcher.find()) {
		// String src = matcher.group(1);
		// content = content.replace("../", Constants.ROOT_URL);
		// }
		content = content.replace("../", GlobalUrls.BASE_URL);
		return content;
	}

	// 去除段前的换行符
	public String parseParagraph(String content) {
		content = content.trim();
		while (content.startsWith("<br>")) {
			content = content.substring(4).trim();
		}
		while (content.startsWith("<br />")) {
			content = content.substring(6).trim();
		}
		while (content.endsWith("<br />")) {
			content = content.substring(0, content.length() - 6).trim();
		}
		return content;
	}

	// 解析帖子的引用部分
	public String parseQuotes(String message) {
		if (quotes == null) {
			quotes = new ArrayList<>();
		}
		else {
			quotes.clear();
		}
		Pattern p = Pattern
				.compile(
						"<br><br><center><table border=\"0\" width=\"90%\".*?bgcolor=\"ALTBG2\"><b>(.*?)</b> (\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2})<br />(.*?)</td></tr></table></td></tr></table></center><br>",
						Pattern.DOTALL);
		Matcher m = p.matcher(message);
		while (m.find()) {
			// 1: author; 2:time; 3:content
			quotes.add(new Quote(m.group(1), m.group(2), parseParagraph(m
					.group(3))));
			message = message.replace(m.group(0), "");
		}
		return message;
	}
}

