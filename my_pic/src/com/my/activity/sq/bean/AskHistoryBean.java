package com.my.activity.sq.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.my.util.Tools;

/*
 * 咨询记录对象
 */

public class AskHistoryBean implements Serializable {

	private static final long serialVersionUID = -2038139373264785584L;

	private String id;
	private String remark;
	private String doctorAvatar;
	private String consultDuration;
	private String consultTime;
	private String doctorId;
	private String userId;
	private String userName;
	private String userAvatar;
	private String doctorName;
	private String totalNum;
	private String totalTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDoctorAvatar() {
		return doctorAvatar;
	}

	public void setDoctorAvatar(String doctorAvatar) {
		this.doctorAvatar = doctorAvatar;
	}

	public String getConsultDuration() {
		return consultDuration;
	}

	public void setConsultDuration(String consultDuration) {
		this.consultDuration = consultDuration;
	}

	public String getConsultTime() {
		return consultTime;
	}

	public void setConsultTime(String consultTime) {
		this.consultTime = consultTime;
	}

	public String getDoctorId() {
		return doctorId;
	}

	public void setDoctorId(String doctorId) {
		this.doctorId = doctorId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserAvatar() {
		return userAvatar;
	}

	public void setUserAvatar(String userAvatar) {
		this.userAvatar = userAvatar;
	}

	public String getDoctorName() {
		return doctorName;
	}

	public void setDoctorName(String doctorName) {
		this.doctorName = doctorName;
	}

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public String getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}

	public static List<AskHistoryBean> getAskHistory(String content)
			throws JSONException {

		if (Tools.isNull(content)) {
			return null;
		}

		JSONObject jsonobject1 = new JSONObject(content);

		if (jsonobject1 != null) {

			String code = jsonobject1.optString("code");
			if ("-1".equals(code)) {

				return null;
			} else if ("0".equals(code)) {

				String total = jsonobject1.optString("total");
				String totalTime = jsonobject1.optString("totalTime");
				JSONArray marray = jsonobject1.optJSONArray("list");
				if (marray != null) {
					List<AskHistoryBean> mInfoList = new ArrayList<AskHistoryBean>();

					int length = marray.length();
					for (int i = 0; i < length; i++) {
						JSONObject mobject = (JSONObject) marray.get(i);

						AskHistoryBean minfo = new AskHistoryBean();

						minfo.setTotalNum(total);
						minfo.setTotalTime(totalTime);
						minfo.setId(Tools.JSONString(mobject, "id"));

						minfo.setRemark(Tools.JSONString(mobject, "remark"));
						String doctorAvatar = Tools.JSONString(mobject,
								"doctorAvatar");
						if (!Tools.isNull(doctorAvatar)) {
							minfo.setDoctorAvatar(Tools.getUrl(doctorAvatar));
						}

						minfo.setConsultDuration(Tools.JSONString(mobject,
								"consultDuration"));
						minfo.setConsultTime(Tools.JSONString(mobject,
								"consultTime"));
						minfo.setDoctorId(Tools.JSONString(mobject, "doctorId"));
						minfo.setUserId(Tools.JSONString(mobject, "userId"));
						minfo.setUserName(Tools.JSONString(mobject, "userName"));

						String userAvatar = Tools.JSONString(mobject,
								"userAvatar");
						if (!Tools.isNull(userAvatar)) {
							minfo.setUserAvatar(Tools.getUrl(userAvatar));
						}

						minfo.setDoctorName(Tools.JSONString(mobject,
								"doctorName"));

						mInfoList.add(minfo);
					}
					return mInfoList;
				}

				return null;
			}

		}

		return null;
	}
}
