package com.sssta.qinbot.core;

import java.io.IOException;
import java.net.URLEncoder;

import sun.tools.tree.BitAndExpression;
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
		
		String resultJson = HttpHelper.poll(bot.getPollReqData());
		//String resultJson = HttpHelper.poll(URL_POLL+"?r="+URLEncoder.encode(bot.getPollReq())+"clientid="+bot.CLIENT_ID+"&psession="+bot.getPsessionid());
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
