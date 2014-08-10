package com.sssta.qinbot.core;

import static com.sssta.qinbot.util.HttpHelper.PROPERTY_ACCEPT;
import static com.sssta.qinbot.util.HttpHelper.PROPERTY_ACCEPT_ENCODING;
import static com.sssta.qinbot.util.HttpHelper.PROPERTY_ACCEPT_LANGUAGE;
import static com.sssta.qinbot.util.HttpHelper.PROPERTY_CONNECTION;
import static com.sssta.qinbot.util.HttpHelper.PROPERTY_CONTETN_TYPE;
import static com.sssta.qinbot.util.HttpHelper.PROPERTY_HOST;
import static com.sssta.qinbot.util.HttpHelper.PROPERTY_ORIGIN;
import static com.sssta.qinbot.util.HttpHelper.PROPERTY_REFER;
import static com.sssta.qinbot.util.HttpHelper.URL_REFER_POLL;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

import sun.tools.tree.BitAndExpression;

import com.sssta.qinbot.util.HttpHelper;

public class Sender extends Thread {
	private boolean pause = false;
	private Bot bot;
	public Sender(Bot bot) {
		super();
		this.bot = bot;
	}

	@Override
	public void destroy() {
		//TODO 
		super.destroy();
	}

	@Override
	public void run() {
		while (true) {
			if (!pause) {
				try {
					//send();
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {
					//
				}
			}
		}
	}
	
	public void send(){
		if (bot.getGroups().size()>0) {
			HashMap<String, String> properties = new HashMap<String, String>();
			properties.put(PROPERTY_ACCEPT, "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
			properties.put(PROPERTY_REFER, URL_REFER_POLL);
			properties.put(PROPERTY_ACCEPT,"*/*");
			properties.put(PROPERTY_ACCEPT_ENCODING, "gzip,deflate,sdch");
			properties.put(PROPERTY_CONTETN_TYPE, "application/x-www-form-urlencoded");
			properties.put(PROPERTY_CONNECTION,"keep-alive");
			properties.put(PROPERTY_ACCEPT_LANGUAGE, "zh-CN,zh;q=0.8");
			properties.put(PROPERTY_HOST, "d.web2.qq.com");
			properties.put(PROPERTY_ORIGIN, "http://d.web2.qq.com");

			String resultJson = HttpHelper.sendPost(HttpHelper.URL_SEND_GROUP,bot.getSendGrouopReqData((bot.getGroups().get(0).getUni())),properties);
			System.out.println("send--"+resultJson);
		}
		

	}
	
}
