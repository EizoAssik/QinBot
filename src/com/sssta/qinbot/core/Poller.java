package com.sssta.qinbot.core;

import java.io.IOException;
import java.net.URLEncoder;

import sun.tools.tree.BitAndExpression;

import com.sssta.qinbot.util.HttpHelper;

public class Poller extends Thread {
	public static final String URL_POLL = "http://d.web2.qq.com/channel/poll2";
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
					sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {
					//
				}
			}
		}
	}
	
	public void  poll() throws IOException{
		
		String resultJson = HttpHelper.poll(bot.getPollReq());
		//String resultJson = HttpHelper.poll(URL_POLL+"?r="+URLEncoder.encode(bot.getPollReq())+"clientid="+bot.CLIENT_ID+"&psession="+bot.getPsessionid());
		
		System.out.println("poll--"+resultJson);
	}
}
