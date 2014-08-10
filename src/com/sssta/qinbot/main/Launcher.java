package com.sssta.qinbot.main;

import javax.swing.SwingUtilities;

import com.sssta.qinbot.swing.MainWindow;
import com.sssta.qinbot.util.FunnyHash;
import com.sssta.qinbot.util.HttpHelper;

public class Launcher {
	public static void main(String[] args) {
		//System.out.println(HttpHelper.getLoginSig());
		SwingUtilities.invokeLater(new Runnable(){    
            public void run(){    
            //update the GUI   
        		MainWindow window = new MainWindow();
        		window.launchFrame();
            }   
		});
		//System.out.println(FunnyHash.getNewbiHash("31307755b8c95c1d2a74abaca3055ddf82a7c881048a3c4bbfafb6c093ebef52","3024181766"));//"\\x00\\x00\\x00\\x00\\xb4\\x41\\x5a\\x06"));
	}
	

}
