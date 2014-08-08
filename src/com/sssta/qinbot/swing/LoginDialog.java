package com.sssta.qinbot.swing;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.sssta.qinbot.Event.EventCallback;
import com.sssta.qinbot.core.Bot;
import com.sssta.qinbot.util.HttpHelper;

public class LoginDialog extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7716152343854581435L;
	
	private JLabel qqLabel,pswLabel,vcLabel,imgLabel;
	private JTextField qqTextField,vcTextField;
	private JPasswordField pswField;
	private JButton submit,refresh;
	
	private static final int WIDTH =280;
	private static final int HEIGHT = 210;
	private MainWindow mainWindow;
	private static final int PADDING_LEFT = 20;
	private static final int PADDING_TOP = 20;
	private static final int COMPOMENT_HEIGHT = 30;
	private String loginSig;
	private boolean isChecked = false;
	

	public LoginDialog(MainWindow mainWindow) {
		super(mainWindow);
		this.mainWindow = mainWindow;
		setTitle("登录");
		setSize(WIDTH, HEIGHT);
		setLocation((mainWindow.SCREEN_WIDTH-WIDTH)/2, (mainWindow.SCREEN_HEIGHT-HEIGHT)/2);
		setResizable(false);
		
		initCompoments();
		setListener();
		setVisible(true);
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				loginSig = HttpHelper.getLoginSig();
				Bot.getInstance().setLoginSig(loginSig);
			}
		});

	}
	
	
	//检测登陆状态已经判断是否需要验证码
	private void checkLogin(){
		if(!qqTextField.getText().trim().equals("") && loginSig!=null){
			submit.setEnabled(true);
			submit.setText("登陆");
			imgLabel.setText("正在加载");
			//开新线程来获取验证码
			new Thread(new Runnable() {
				@Override
				public void run() {
						Bot.getInstance().setQq(qqTextField.getText().trim());
						HttpHelper.checkLogin(new EventCallback() {
							@Override
							public void exec(boolean succeed) {
								if (succeed) {
									vcLabel.setVisible(true);
									vcTextField.setVisible(true);
									imgLabel.setVisible(true);
									vcTextField.setEditable(true);
									
									try {
										Bot bot = Bot.getInstance();
										imgLabel.setIcon(new ImageIcon(HttpHelper.getImage(String.format("https://ssl.captcha.qq.com/getimage?&uin=%s&aid=1003903&%f&cap_cd=%s",bot.getQQ(),new Random().nextDouble(),bot.getLoginSig())
												,HttpHelper.URL_REFER_Q
												,new EventCallback() {
											
											@Override
											public void exec(boolean result) {
												if (!result) {
													imgLabel.setText("加载失败，请重试");
													imgLabel.setIcon(null);
												}else{
													imgLabel.setText("");
												}
											}
										})));
									} catch (NullPointerException e) {
										imgLabel.setText("加载失败，请重试");
										imgLabel.setIcon(null);
									}
									
									
								}else {
									vcLabel.setVisible(false);
									vcTextField.setVisible(false);
									imgLabel.setVisible(false);
									vcTextField.setEditable(false);
								}
							}
						});
						}
			}).start();
		}		

	}

	private void setListener() {
		qqTextField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
						checkLogin();
			}
			@Override
			public void focusGained(FocusEvent e) {
			}
		});
		
		imgLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				checkLogin();
			}
		});
		
		
		submit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				login();
				
			}

		});
		
	}

	private void login() {
		
		if (pswField.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "密码不能为空", "警告", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
				
		if (qqTextField.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "账号不能为空", "警告", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
				
		if (vcTextField.isVisible()&&vcTextField.getText().trim().equals("")) {
			JOptionPane.showMessageDialog(null, "密码不能为空", "警告", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		submit.setEnabled(false);
		submit.setText("正在登陆");
		
		Bot.getInstance().setPsw(pswField.getText().trim());
		
		boolean result;
		if (vcTextField.isVisible()) {
			result = Bot.getInstance().login(vcTextField.getText().trim());
		}else{
			result =Bot.getInstance().login("");
		}
		
		if(!result){
			//登陆失败，重新检测登陆状态和获取新验证码
			checkLogin();
		}else {
			//登陆成功，显示主窗口；
			dispose();
			mainWindow.setVisible(true);
		}
				
	}
	private void initCompoments() {
		
		this.setLayout(null);
		
		qqLabel = new JLabel("QQ号：");
		pswLabel = new JLabel("密码：");
		vcLabel = new JLabel("验证码：");
		
		submit = new JButton("登录");
		
		imgLabel = new JLabel("");
		
		
		
		qqTextField = new JTextField("3024181766");
		pswField = new JPasswordField();
		vcTextField = new JTextField();
		
		vcTextField.setEditable(false);
		
		qqLabel.setBounds(PADDING_LEFT, PADDING_TOP, 60, COMPOMENT_HEIGHT);
		pswLabel.setBounds(PADDING_LEFT, PADDING_TOP+COMPOMENT_HEIGHT, 60, COMPOMENT_HEIGHT);
		
		qqTextField.setBounds(PADDING_LEFT+65, PADDING_TOP, 160, COMPOMENT_HEIGHT);
		pswField.setBounds(PADDING_LEFT+65, PADDING_TOP+COMPOMENT_HEIGHT,160, COMPOMENT_HEIGHT);
	
		vcLabel.setBounds(PADDING_LEFT, PADDING_TOP+COMPOMENT_HEIGHT*2, 80, COMPOMENT_HEIGHT);
		vcTextField.setBounds(PADDING_LEFT+65,  PADDING_TOP+COMPOMENT_HEIGHT*2, 60, COMPOMENT_HEIGHT);
	
		imgLabel.setBounds(PADDING_LEFT+130,  PADDING_TOP+COMPOMENT_HEIGHT*2, 120, (int)(COMPOMENT_HEIGHT*1.3));
		
		submit.setBounds(100, PADDING_TOP+COMPOMENT_HEIGHT*3+15, 80, COMPOMENT_HEIGHT);
		
		
		add(qqLabel);
		add(qqTextField);
		add(pswLabel);
		add(pswField);
		add(vcLabel);
		add(vcTextField);
		add(submit);
		add(imgLabel);
		
		
		vcLabel.setVisible(false);
		vcTextField.setVisible(false);
		imgLabel.setVisible(false);
	
	}

	

}
