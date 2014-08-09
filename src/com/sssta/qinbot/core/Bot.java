package com.sssta.qinbot.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URLEncoder;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.JOptionPane;

import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;

import com.sssta.qinbot.model.BotState;
import com.sssta.qinbot.model.VerifyCodeChecker;
import com.sssta.qinbot.util.HttpHelper;
import com.sssta.qinbot.util.ResponseParser;

public class Bot {
	private String qq;
	private String psw;
	private String loginSig;
	private String vcReqCode; // 获取验证码所需要的请求码
	private String verifySession;
	private String qqHex;
	private String nikeName;
	private static Bot bot = new Bot();
	private String ptwebqq;
	private String vfwebqq;
	private BotState state = BotState.OFFLINE;

	private static final String CLIENT_ID = "7776085";

	private String skey;
	private String psessionid;

	public static Bot getInstance() {
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
		qqHex = checker.getQqHex();
	}

	public String getQqHex() {
		return qqHex;
	}

	public String getVcReqCode() {
		return vcReqCode;
	}

	public String getVerifySession() {
		return verifySession;
	}

	public void setVerifySession(String verifySession) {
		this.verifySession = verifySession;
	}

	public String getNikeName() {
		return nikeName;
	}

	public void setNikeName(String nikeName) {
		this.nikeName = nikeName;
	}

	public String getPtwebqq() {
		return ptwebqq;
	}

	public void setPtwebqq(String ptwebqq) {
		this.ptwebqq = ptwebqq;
	}

	public String getVfwebqq() {
		return vfwebqq;
	}

	public void setVfwebqq(String vfwebqq) {
		this.vfwebqq = vfwebqq;
	}

	public String getSkey() {
		return skey;
	}

	public void setSkey(String skey) {
		this.skey = skey;
	}

	public boolean login(String vCode) {
		String psw = getPsw();
		String uni = getQqHex();
		String vcode = vCode.equals("") ? getVcReqCode() : vCode;
		
		//通过pass.js计算出加密后的密码p
		String p = "";
		ScriptEngineManager m = new ScriptEngineManager();
		ScriptEngine se = m.getEngineByName("javascript");
		
		try {
			se.eval(new FileReader(
					new File("src/com/sssta/qinbot/util/pass.js")));
			Object t = se.eval("getEncryption(\"" + psw + "\",\"" + uni
					+ "\",\"" + vcode.toUpperCase() + "\");");
			p = t.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		
		//发起第一次登陆请求
		String resultString = HttpHelper.sendGet(
				String.format(HttpHelper.URL_FORMAT_LOGIN,getQQ(), p,
						vcode.toUpperCase(),getLoginSig(),
						getVerifySession()), HttpHelper.URL_REFER_Q);
		//解析登陆请求返回的信息，若请求成功并且在里面进行第二次登陆回调来获取最后所需要的Cookie；
		String state = ResponseParser.parseLogin(resultString);
		if (state.contains("登录成功")) {
			//获取重要的两个Cookie
			setPtwebqq(HttpHelper.getCookie("ptwebqq"));
			setSkey(HttpHelper.getCookie("skey"));

			setState(BotState.ONLINE);
			//发起第一次Post请求，正式登陆
			String channelLoginUrl = "http://d.web2.qq.com/channel/login2";
			String content = "{\"status\":\"\",\"ptwebqq\":\"" + ptwebqq
					+ "\",\"passwd_sig\":\"\",\"clientid\":\"" + CLIENT_ID
					+ "\"}";
			content = URLEncoder.encode(content);// urlencode
			content = "r=" + content;// post的数据
			String res = HttpHelper
					.sendPost(channelLoginUrl, content,
							"http://d.web2.qq.com/proxy.html?v=20110331002&callback=1&id=2");// post
			//System.out.println("\n  " + ptwebqq + "   " + res);
			JSONObject rootObject = null;
			try {
				//抓取重要的两个值，用于发送信息
				rootObject = new JSONObject(res);
				JSONObject object = rootObject.optJSONObject("result");
				vfwebqq = object.optString("vfwebqq");
				psessionid = object.optString("psessionid");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return true;
		} else {
			JOptionPane.showMessageDialog(null, state, "警告",
					JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}

	private void setState(BotState state) {
		this.state = state;
	}
}
