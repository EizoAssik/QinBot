package com.sssta.qinbot.util;

public class Poller extends Thread {
	private boolean pause = false;
	@Override
	public void destroy() {
		
		super.destroy();
	}

	@Override
	public void run() {
		while (true) {
			if (!pause) {
				
			}
		}
	}
	
}
