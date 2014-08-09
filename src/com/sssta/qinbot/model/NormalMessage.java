package com.sssta.qinbot.model;

import atg.taglib.json.util.JSONObject;

public class NormalMessage extends Message {

	public NormalMessage(JSONObject message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getType() {
		return TYPE_NORMAL;
	}

	@Override
	public void reply(String msg) {
		
	}

	@Override
	public String generateReplyJson() {
		return "";
	}

	
}
