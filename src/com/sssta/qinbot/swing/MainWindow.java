package com.sssta.qinbot.swing;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JFrame;


public class MainWindow extends JFrame  {
	

	public static final int GAME_WIDTH = 700;
	public static final int GAME_HEIGHT = 600;
	public static  int SCREEN_WIDTH ;
	public static  int SCREEN_HEIGHT ;
	private LoginDialog loginDialog;
	
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
		loginDialog = new LoginDialog(this);
		loginDialog.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		loginDialog.addWindowListener(new WindowAdapter(){
			   public void windowClosing(WindowEvent e) {
				   System.out.println("Window closed");
				   System.exit(0);
			  }
		 });
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
}
