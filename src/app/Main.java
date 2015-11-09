package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import model.Graph;
import model.Tweet;
import model.Util;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;


public class Main {

	private static BufferedReader reader;
	private static Graph graph = new Graph();

	public static void main(String[] args) throws Exception 	{
		File fileIn = new File("tweet_input\\tweets.txt");
		FileInputStream in = new FileInputStream(fileIn);
		JsonReader reader = new JsonReader(new InputStreamReader(in, "UTF-8"));
        reader.setLenient(true);
		extract(reader);	
	}


	private static void extract(JsonReader reader) throws IOException, ParseException {
		File f = new File("tweet_output\\ft1.txt");
		if(!f.exists())
			f.mkdir();
		PrintWriter writer = new PrintWriter(f, "UTF-8");
        while(true) {
        	if(reader.peek().equals(JsonToken.BEGIN_OBJECT)) {
        		reader.beginObject();
        	}
        		
        	String created_at = null;
    		String text = null;
    		List<String> tagList = null;
    		while (reader.hasNext()) {
            	String name = reader.nextName();
            	if(name.equalsIgnoreCase("created_at")) {
            		created_at = reader.nextString();
            	} else if(name.equalsIgnoreCase("text")) {
            		text = Util.clean(reader.nextString());
            	} else if(name.equalsIgnoreCase("entities")) {
                	tagList = getHashTags(reader);
            	} else {
            		reader.skipValue();
            	}
            }
    		//FEATURE 1. format string and save to file
    		if(text != null && created_at != null) {
    			String formatedStr = text + " (timestamp " + created_at + ")" ;
        		writer.println(formatedStr);
    		} 
    		
    		//FEATURE 2. Add tweets to graph and build it on the go
    		if(tagList != null && tagList.size() > 0) {
        		parseTweet(text, created_at, tagList);
    		}
    		
            reader.endObject();
            
            if(reader.peek().equals(JsonToken.BEGIN_OBJECT))
            	continue;
            
            reader.close();
            writer.println(Util.getCount() + " tweets contained unicode");
            writer.flush();
            writer.close();
            graph.close();
            return;
        }
	}
	

	private static List<String> getHashTags(JsonReader reader) throws IOException {
		List<String> tagList = new ArrayList<String>();
		reader.beginObject();
		while(reader.hasNext()) {
			String entityName = reader.nextName();
			if(entityName.equalsIgnoreCase("hashtags")) {
				reader.beginArray();
				
				while(reader.hasNext()) {
					reader.beginObject();
					
					while(reader.hasNext()) {
						String tagName = reader.nextName();
						if(tagName.equalsIgnoreCase("text")) {
							tagList.add(reader.nextString());
						} else {
							reader.skipValue();
						}
					}
					reader.endObject();
				}
				reader.endArray();
			} else {
				reader.skipValue();
			}
			
		}
		reader.endObject();
		return tagList;
	}


	private static void parseTweet(String utf8tweet, String createdDateStr, List<String> tagList) 
			throws ParseException, FileNotFoundException, UnsupportedEncodingException {		
		//parse date
		DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);
		Date date = format.parse(createdDateStr);
		
		Tweet newTwt = new Tweet(utf8tweet, date, tagList);
		graph.add(newTwt);
	}
}
