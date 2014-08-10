package com.sssta.qinbot.core;

import java.io.IOException;
import java.net.URLEncoder;

import sun.tools.tree.BitAndExpression;

import com.sssta.qinbot.util.HttpHelper;

public class Sender extends Thread {
	public static final String URL_POLL = "http://d.web2.qq.com/channel/poll2";
	private boolean pause = false;
	private Bot bot;
	public Sender(Bot bot) {
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
					send();
					sleep(4000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (Exception e) {
					//
				}
			}
		}
	}
	

	
	public void send(){
		String resultJson = HttpHelper.send(bot.getSendGrouopReqData());
		System.out.println("send--"+resultJson);

	}
	
}
