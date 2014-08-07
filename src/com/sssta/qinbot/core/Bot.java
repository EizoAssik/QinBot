package com.sssta.qinbot.core;

import com.sssta.qinbot.model.VerifyCodeChecker;


public class Bot {
	private String qq;
	private String psw;
	private String loginSig;
	private String vcReqCode; //获取验证码所需要的请求码
	private static Bot bot = new Bot();
	public static Bot getInstance(){
		return bot;
	}
	public String getQQ() {
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
	public void attachChecker(VerifyCodeChecker checker) {
		vcReqCode = checker.getReqCode();
	}
	
	
}
