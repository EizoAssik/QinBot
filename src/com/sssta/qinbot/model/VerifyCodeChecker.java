package com.sssta.qinbot.model;

public class VerifyCodeChecker {
	private boolean need;
	private String reqCode;
	private String qqHex;
	public VerifyCodeChecker(String code, String reqCode,String qqHex) {
		super();
		this.need = code.equals("0")?false:true;
		this.reqCode = reqCode;
		this.qqHex = qqHex;
	}
	
	public String getQqHex() {
		return qqHex;
	}

	public void setQqHex(String qqHex) {
		this.qqHex = qqHex;
	}

	public boolean isNeed() {
		return need;
	}

	public String getReqCode() {
		return reqCode;
	}

}
