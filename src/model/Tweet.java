package model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Tweet {
	private Date created_at;
	private String text;
	private List<String> tagList = new ArrayList<String>();
	
	public Tweet(String text, Date date, List<String> tagList2) {
		this.text = text;
		this.setDate(date);
		setTagList(tagList2);
	}
	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	public Date getDate() {
		return created_at;
	}
	public void setDate(Date created_at) {
		this.created_at = created_at;
	}
	public List<String> getTagList() {
		return tagList;
	}
	public void setTagList(List<String> tagList) {
		this.tagList = tagList;
	}

}
