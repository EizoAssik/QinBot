package com.sssta.qinbot.model;

public class Message {
	public enum MessageType{
		GROUP_MESSAGE("group_message"),MESSAGE("message");
		
		private String name;
		private MessageType(String name){
			this.name = name;
		}
		@Override
		public String toString() {
			return name;
		}
	}
	
	//基础信息
	public MessageType type;
	public String content;
	public long time;
	public String from;
	public String to;
	public String msgId;
	public String msgId2;
	public int msgType;
	public String replyIp;
	
	//群组信息的uni字段
	public String groupCode;
	public String infoSeq;
	public String sender;//即为from
	public String seq;
	
	
	public Message(String json){
		
	}
	
	
	public MessageType getType() {
		return type;
	}
	public void setType(MessageType type) {
		this.type = type;
	}
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
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public String getInfoSeq() {
		return infoSeq;
	}
	public void setInfoSeq(String infoSeq) {
		this.infoSeq = infoSeq;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
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
