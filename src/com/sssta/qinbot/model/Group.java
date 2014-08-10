package com.sssta.qinbot.model;

import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;

public class Group {
	private String uin;
	private String code;
	private String flag;
	private String name;

	public Group(JSONObject group) {
		flag = group.optString("flag");
		code = group.optString("code");
		uin = group.optString("gid");
		name = group.optString("name");
		System.out.println("Group--"+uin);
	}

	public String getUin() {
		return uin;
	}

	public void setUin(String uni) {
		this.uin = uni;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
