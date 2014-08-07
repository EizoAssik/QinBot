package com.sssta.qinbot.model;

public class VerifyCodeChecker {
	private boolean need;
	private String reqCode;
	public VerifyCodeChecker(String code, String reqCode) {
		super();
		this.need = code.equals("0")?false:true;
		this.reqCode = reqCode;
	}
	public boolean isNeed() {
		return need;
	}

	public String getReqCode() {
		return isNeed()?reqCode:"!MPG";
	}

}
