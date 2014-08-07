package com.sssta.qinbot.core;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.sun.codemodel.internal.JOp;


public class Bot {
	private String qq;
	private String psw;
	private String loginSig;
	private String vcReqCode; //获取验证码所需要的请求码
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
	
	 public boolean login(String vCode){
	    	String psw = getPsw();
	    	String uni = getQqHex();
	    	String vcode =  vCode.equals("")?getVcReqCode():vCode;
	    	
	    	System.out.println();
	    	System.out.println("aaaaa  "+psw+"  "+uni+"  "+vcode);
	    	
	    	String p ="";
         ScriptEngineManager m = new ScriptEngineManager();
         ScriptEngine se = m.getEngineByName("javascript");
         try {
				se.eval(new FileReader(new File("src/com/sssta/qinbot/util/pass.js")));
	            Object t = se.eval("getEncryption(\""+psw+"\",\""+uni+"\",\""+vcode.toUpperCase()+"\");");
	            p = t.toString();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ScriptException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         Bot bot = Bot.getInstance();
         String resultString = HttpHelper.sendGet(String.format(HttpHelper.URL_FORMAT_LOGIN,bot.getQQ(),p,vcode.toUpperCase(),bot.getLoginSig(),bot.getVerifySession()),HttpHelper.URL_REFER_Q);
         System.out.println(p);

         System.out.println(resultString);
         String state = ResponseParser.parseLogin(resultString);
         if (state.contains("登录成功")) {
         	
                bot.setPtwebqq(HttpHelper.map.get("ptwebqq").getValue());  
                bot.setSkey(HttpHelper.map.get("skey").getValue());  
             
             bot.setState(BotState.ONLINE);
             String channelLoginUrl = "http://d.web2.qq.com/channel/login2";  
             String content = "{\"status\":\"\",\"ptwebqq\":\""+ptwebqq+"\",\"passwd_sig\":\"\",\"clientid\":\""+CLIENT_ID+"\"}";  
             content = URLEncoder.encode(content);//urlencode   
             content = "r="+content;//post的数据  
             String res = HttpHelper.sendPost(channelLoginUrl, content,"http://d.web2.qq.com/proxy.html?v=20110331002&callback=1&id=2");//post  
             System.out.println("\n  "+ptwebqq+"   "+res);
             JSONObject rootObject = null;
			try {
				rootObject = new JSONObject(res);
				JSONObject object = rootObject.optJSONObject("result");
		        vfwebqq = object.optString("vfwebqq");
		        psessionid = object.optString("psessionid");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
             
        	return true;
	    }else{
	    	JOptionPane.showMessageDialog(null, state,"警告",JOptionPane.WARNING_MESSAGE);
	    	return false;
	    }
	}
	private void setState(BotState state) {
		this.state = state;
	}
}
