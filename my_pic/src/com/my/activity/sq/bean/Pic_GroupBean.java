package com.my.activity.sq.bean;

import java.io.Serializable;
import java.util.List;

/*
 * 2级分組对象
 */

public class Pic_GroupBean implements Serializable {

	private static final long serialVersionUID = -2038139373264785584L;

	private String id;
	private String title;
	private String introduce;
 
	private String consultTime;
 
	private String userName;
	private List<Pic_GroupItemBean>  listItem;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getIntroduce() {
		return introduce;
	}
	public void setIntroduce(String introduce) {
		this.introduce = introduce;
	}
	public String getConsultTime() {
		return consultTime;
	}
	public void setConsultTime(String consultTime) {
		this.consultTime = consultTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public List<Pic_GroupItemBean> getListItem() {
		return listItem;
	}
	public void setListItem(List<Pic_GroupItemBean> listItem) {
		this.listItem = listItem;
	}

	 
}
