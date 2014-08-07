package com.sssta.qinbot.util;

import java.text.ParseException;
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
		Pattern pattern = Pattern.compile("ptuiCB\\('(.*?)', *'(.*?)', *'(.*?)', *'(.*?)', *'(.*?)', *'(.*?)'\\);");
    	Matcher matcher = pattern.matcher(response);
    	if (matcher.find()) {

			Bot.getInstance().setNikeName(matcher.group(6));
			HttpHelper.sendGet(matcher.group(3),"");
			return matcher.group(5);
		}
    	else{
    		return "登录失败";
    	}
    
	}
}
