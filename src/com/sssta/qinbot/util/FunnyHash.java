package com.sssta.qinbot.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;


public class FunnyHash {
	public static String getPswHash(String psw, String uni, String vcode) {
		// 通过pass.js计算出加密后的密码p
		String p = null;
		ScriptEngineManager m = new ScriptEngineManager();
		ScriptEngine se = m.getEngineByName("javascript");

		try {
			se.eval(new FileReader(
					new File("src/com/sssta/qinbot/util/pass.js")));
			Object t = se.eval("getEncryption(\"" + psw + "\",\"" + uni
					+ "\",\"" + vcode + "\");");
			p = t.toString();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		return p;
	}
	
	public static  String getNewbiHash(String ptwebqq,String uni){
		StringBuffer sBuffer = new StringBuffer();
		String n = ptwebqq+"password error";
		while (sBuffer.length() < n.length()) {
			sBuffer.append(uni);
		}
		
		StringBuffer hash  = new StringBuffer();
		for (int i = 0; i < n.length(); i++) {
			hash.append(String.format("%02X", ((int)sBuffer.charAt(i))^((int)n.charAt(i))));
		}
		
		System.out.println(hash.toString());
		return hash.toString();
	}
}
