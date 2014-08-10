package com.sssta.qinbot.model;

import atg.taglib.json.util.JSONObject;

public class GroupMessage extends Message {
	
	public String groupCode;
	public String infoSeq;
	public String sendUin;//即为from
	public String seq;
	
	private static final String GROUP_CODE = "group_code";
	private static final String INFO_SEQ = "info_seq";
	private static final String SEND_UIN = "send_uin";
	private static final String SEQ = "seq";
	
	public GroupMessage(JSONObject message) {
		super(message);
		groupCode = message.optString(GROUP_CODE);
		infoSeq = message.optString(INFO_SEQ);
		sendUin = message.optString(SEND_UIN);
		seq = message.optString(SEQ);
	}
	
	@Override
	public String getType() {
		return TYPE_GROUP;
	}
	
	@Override
	public void reply(String msg) {
		
	}

	@Override
	public String generateReplyJson() {
		// TODO Auto-generated method stub
		return null;
	}

	
}
