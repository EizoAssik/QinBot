package com.sssta.qinbot.util;

import static com.sssta.qinbot.util.HttpHelper.PROPERTY_ACCEPT;
import static com.sssta.qinbot.util.HttpHelper.PROPERTY_ACCEPT_CHARSET;
import static com.sssta.qinbot.util.HttpHelper.PROPERTY_COOKIE;
import static com.sssta.qinbot.util.HttpHelper.PROPERTY_CONNECTION;

import static com.sssta.qinbot.util.HttpHelper.getCookie;

import java.text.ParseException;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sssta.qinbot.core.Bot;
import com.sssta.qinbot.model.VerifyCodeChecker;

public class ResponseParser {
	//解析验证码字符串
	public static VerifyCodeChecker parseVC(String respones) throws ParseException{
		System.out.println();
		System.out.println(respones);
		Pattern pattern  = Pattern.compile("ptui_checkVC\\('([01])','(.*)','(.*)', *'(.*)'\\);");
		Matcher matcher = pattern.matcher(respones);
		if(matcher.find()){
			return new VerifyCodeChecker(matcher.group(1), matcher.group(2),matcher.group(3));
		}else{
			throw new ParseException("验证返回值解析错误", 0);
		}
	}
	
	                     
	public static String parseLogin(String response){
		System.out.println(response);
		Pattern pattern = Pattern.compile("ptuiCB\\('(.*?)', *'(.*?)', *'(.*?)', *'(.*?)', *'(.*?)', *'(.*?)'\\);");
    	Matcher matcher = pattern.matcher(response);
    	if (matcher.find()) {
			Bot.getInstance().setNikeName(matcher.group(6));
			//第二次登陆回调
			if (!matcher.group(3).equals("")) {
				HashMap<String, String> properties = new HashMap<String, String>();
				properties.put(PROPERTY_ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
				properties.put(PROPERTY_COOKIE,getCookie());
				properties.put(PROPERTY_ACCEPT_CHARSET, "UTF-8;");
				properties.put(PROPERTY_CONNECTION, "keep-alive");
				HttpHelper.sendGet(matcher.group(3),properties);
			}
			return matcher.group(5);
		}
    	else{
    		return "登录失败";
    	}
    
	}
}
