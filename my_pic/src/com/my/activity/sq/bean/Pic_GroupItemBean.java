package com.my.activity.sq.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.my.util.Tools;

/*
 * 3级对象
 */

public class Pic_GroupItemBean implements Serializable {

	private static final long serialVersionUID = -2038139373264785584L;

	private String id;
	private String name;
	 
	private List<String> urls;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	};

 
}
