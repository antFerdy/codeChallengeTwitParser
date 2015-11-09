package model;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {
	private static int count = 0;
	
	
	/**
	 * finding and replaction uncorrent symbols in tweet
	 *  
	 **/
	public static String clean(String twtTxt) throws UnsupportedEncodingException {
		twtTxt.trim();
		byte[] utf8Bytes = twtTxt.getBytes("UTF-8");
		String utf8tweet = new String(utf8Bytes, "UTF-8");
		Pattern unicodeOutliers = Pattern.compile("[^\\x20-\\x7F]",
		        Pattern.UNICODE_CASE | 
		        Pattern.CANON_EQ | 
		        Pattern.CASE_INSENSITIVE);
		Matcher unicodeOutlierMatcher = unicodeOutliers.matcher(utf8tweet);
		
		while(unicodeOutlierMatcher.find()) {
			count++;
		}
		
		utf8tweet = unicodeOutlierMatcher.replaceAll("");
		utf8tweet.replaceAll("\n", " ");
		utf8tweet.replaceAll("\t", " ");
		
		return utf8tweet;
	}

	/**
	 * get count of replaced non-ascii symbols 
	 **/
	public static int getCount() {
		// TODO Auto-generated method stub
		return count;
	}
	
	
	
}
