package com.sssta.qinbot.core;


public class Bot {
	private String qq;
	private String psw;
	private String loginSig;
	private static Bot bot = new Bot();
	public static Bot getInstance(){
		return bot;
	}
	public String getQq() {
		return qq;
	}
	public void setQq(String qq) {
		this.qq = qq;
	}
	public String getPsw() {
		return psw;
	}
	public void setPsw(String psw) {
		this.psw = psw;
	}
	public String getLoginSig() {
		return loginSig;
	}
	public void setLoginSig(String loginSig) {
		this.loginSig = loginSig;
	}
	
	
}
