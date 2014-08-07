package com.sssta.qinbot.util;

import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sssta.qinbot.model.VerifyCodeChecker;

public class ResponseParser {
	//解析验证码字符串
	public static VerifyCodeChecker parseVC(String respones) throws ParseException{
		System.out.println();
		System.out.println(respones);
		Pattern pattern  = Pattern.compile("ptui_checkVC\\('(.)','(.*?)'(.[^\\)])*\\);");
		Matcher matcher = pattern.matcher(respones);
		if(matcher.find()){
			return new VerifyCodeChecker(matcher.group(1), matcher.group(2));
		}else{
			throw new ParseException("验证返回值解析错误", 0);
		}
	}
}
