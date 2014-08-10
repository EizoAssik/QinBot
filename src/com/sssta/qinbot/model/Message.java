package com.sssta.qinbot.model;

import java.util.LinkedList;
import java.util.List;

import com.sssta.qinbot.exception.MessageErrorException;

import atg.taglib.json.util.JSONArray;
import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;

public abstract class Message {
	
	private static final String POLL_TYPE = "poll_type";
	private static final String RETURN_CODE = "retcode";
	private static final String RESULT = "result";
	public static final String TYPE_GROUP = "group_message";
	public static final String TYPE_NORMAL = "message";
	private static final String CONTENT = "content";
	private static final String TIME = "time";
	private static final String FROM = "from_uin";
	private static final String TO = "to_uin";
	private static final String MSG_ID = "msg_id";
	private static final String MSG_ID2 = "msg_id2";
	private static final String MSG_TYPE = "msg_type";
	private static final String REPLY_IP = "reply_ip";
	
	public String content;
	public long time;
	public String from;
	public String to;
	public String msgId;
	public String msgId2;
	public int msgType;
	public String replyIp;
	
	public Message(String reply){
		
	}
	
	public Message(JSONObject message){
		content = message.optJSONArray(CONTENT).optString(1);
		time = message.optLong(TIME);
		from = message.optString(FROM);
		to = message.optString(TO);
		msgId = message.optString(MSG_ID);
		msgId2 = message.optString(MSG_ID2);
		msgType = message.optInt(MSG_TYPE);
		replyIp = message.optString(REPLY_IP);
	}

	
	public static List<Message> generateMessages(String json) throws JSONException, MessageErrorException{
		List<Message> messages = new LinkedList<Message>();
		JSONObject base = new JSONObject(json);
		int retCode = base.optInt(RETURN_CODE,-1);
		if (retCode != 0 ) {
			throw new MessageErrorException(retCode);
		}else {
			JSONArray results = base.optJSONArray(RESULT);
			for (int i = 0; i < results.length(); i++) {
				JSONObject message = results.optJSONObject(i);
				String type = message.optString(POLL_TYPE);
				if (type.equals(TYPE_NORMAL)) {
					messages.add(new NormalMessage(message));
				}else{
					messages.add(new GroupMessage(message));
				}
			}
		}
		return messages;
	}
	
	
	public abstract String getType();
	public abstract void reply(String msg);
	public abstract String generateReplyJson();

	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public long getTime() {
		return time;
	}


	public void setTime(long time) {
		this.time = time;
	}


	public String getFrom() {
		return from;
	}


	public void setFrom(String from) {
		this.from = from;
	}


	public String getTo() {
		return to;
	}


	public void setTo(String to) {
		this.to = to;
	}


	public String getMsgId() {
		return msgId;
	}


	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}


	public String getMsgId2() {
		return msgId2;
	}


	public void setMsgId2(String msgId2) {
		this.msgId2 = msgId2;
	}


	public int getMsgType() {
		return msgType;
	}


	public void setMsgType(int msgType) {
		this.msgType = msgType;
	}


	public String getReplyIp() {
		return replyIp;
	}


	public void setReplyIp(String replyIp) {
		this.replyIp = replyIp;
	}

	
	
	
	/*信息格式
	 
	 {
    "result": [
        {
            "poll_type": "message",
            "value": {
                "content": [
                    [
                        "font",
                        {
                            "color": "004faa",
                            "name": "STKaiti",
                            "size": 13,
                            "style": [
                                0,
                                0,
                                0
                            ]
                        }
                    ],
                    "hii "
                ],
                "from_uin": 2440652742,
                "msg_id": 24225,
                "msg_id2": 950054,
                "msg_type": 9,
                "reply_ip": 176498455,
                "time": 1388368696,
                "to_uin": 2769546520
            }
        }
    ],
    "retcode": 0
}

{
    "result": [
        {
            "poll_type": "group_message",
            "value": {
                "content": [
                    [
                        "font",
                        {
                            "color": "004faa",
                            "name": "STKaiti",
                            "size": 13,
                            "style": [
                                0,
                                0,
                                0
                            ]
                        }
                    ],
                    "ooo "
                ],
                "from_uin": 2559225925,
                "group_code": 3483368056,
                "info_seq": 346167134,
                "msg_id": 13663,
                "msg_id2": 886022,
                "msg_type": 43,
                "reply_ip": 176756826,
                "send_uin": 2440652742,
                "seq": 27,
                "time": 1388368698,
                "to_uin": 2769546520
            }
        }
    ],
    "retcode": 0
} 
	 */
	
	
}	
