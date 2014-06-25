package com.my.activity.sq.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.my.util.Tools;

/*
 * 分类对象
 */

public class Pic_ServiceItemBean implements Serializable {

	private static final long serialVersionUID = -2038139373264785584L;

	private String id;
	private String className;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	 
	 
	
	
}
