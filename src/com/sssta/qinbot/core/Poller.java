package com.sssta.qinbot.core;

import static com.sssta.qinbot.util.HttpHelper.*;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;

import atg.taglib.json.util.JSONException;
import atg.taglib.json.util.JSONObject;

import com.sssta.qinbot.util.HttpHelper;

public class Poller extends Thread {
	private boolean pause = false;
	private Bot bot;
	public Poller(Bot bot) {
		super();
		this.bot = bot;
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void run() {
		while (true) {
			if (!pause) {
				try {
					poll();
					sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {
					//
				}
			}
		}
	}
	
	public void  poll() throws IOException{
		
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
		
		String resultJson = HttpHelper.sendPost(URL_POLL,bot.getPollReqData(),properties);
		System.out.println("poll--"+resultJson);
		try {
			JSONObject base = new JSONObject(resultJson);
			int retcode = base.optInt("retcode");
			if ( retcode == 0 ||  retcode == 102) {
				//TODO
            }else if (retcode == 100) {
            	//TODO
            }else if (retcode == 120){
            	//TODO
            }else if (retcode == 121){
            	//TODO
            }else if (retcode == 116){
            	bot.setPtwebqq(base.optString("p"));
            }
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
