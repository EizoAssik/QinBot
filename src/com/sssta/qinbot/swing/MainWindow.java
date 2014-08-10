package com.sssta.qinbot.swing;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import com.sssta.qinbot.core.Bot;
import com.sssta.qinbot.util.HttpHelper;



public class MainWindow extends JFrame  {
	
	
	public static final int GAME_WIDTH = 700;
	public static final int GAME_HEIGHT = 600;
	public static  int SCREEN_WIDTH ;
	public static  int SCREEN_HEIGHT ;
	private LoginDialog loginDialog;
	private JLabel avater;
	
	public void launchFrame() {
		
		initData();
		initFrame();	
		initComponent();
		
		setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		  this.addWindowListener(new WindowAdapter(){
			   public void windowClosing(WindowEvent e) {
			    System.exit(0);
			  }
		 });
	}

	private void initComponent() {
		avater = new JLabel();
		loginDialog = new LoginDialog(this);
		loginDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		loginDialog.addWindowListener(new WindowAdapter(){
			   public void windowClosing(WindowEvent e) {
				   System.out.println("Window closed");
				   System.exit(0);
			  }
		 });
		setLayout(null);
		avater.setBounds(20, 20, 40, 40);
		add(avater);
	}

	private void initFrame() {
		this.setLocation((SCREEN_WIDTH-GAME_WIDTH)/2, (SCREEN_WIDTH-GAME_HEIGHT)/2-150);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);
		this.setTitle("QinBot");
		this.setResizable(false);
		this.setVisible(false);
	}

	private void initData() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();      //得到屏幕的尺寸  
		SCREEN_WIDTH = screenSize.width;
		SCREEN_HEIGHT = screenSize.height;
	}

	public void loadData() {
		//avater.setIcon(new ImageIcon(HttpHelper.getImage(String.format("http://face7.web.qq.com/cgi/svr/face/getface?cache=1&type=11&fid=0&uin=3024181766&vfwebqq=%s&t=%d", Bot.getInstance().getVfwebqq(),System.currentTimeMillis()), null, null)));
	}
}
