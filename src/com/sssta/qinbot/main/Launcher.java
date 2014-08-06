package com.sssta.qinbot.main;

import com.sssta.qinbot.util.HttpHelper;

public class Launcher {

	public static void main(String[] args) {
		System.out.println(HttpHelper.getLoginSig());
	}

}
