package com.fl.mfs.pojo;

public class Info {
	public String topic;
	public String title;
	public String url;
	public int oldContentLength;
	public int subscribeCount;
	@Override
	public String toString() {
		return "{\"topic\":\"" + topic + "\", \"title\":\"" + title + "\", \"url\":\"" + url + "\", \"oldContentLength\":\"" + oldContentLength
				+ "\", \"subscribeCount\":\"" + subscribeCount + "\"}";
	}
	
	
}
