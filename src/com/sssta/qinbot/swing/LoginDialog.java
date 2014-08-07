package com.sssta.qinbot.swing;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import com.sssta.qinbot.Event.EventCallback;
import com.sssta.qinbot.core.Bot;
import com.sssta.qinbot.util.HttpHelper;

public class LoginDialog extends JDialog {
	private JLabel qqLabel,pswLabel,vcLabel,imgLabel;
	private JTextField qqTextField,vcTextField;
	private JPasswordField pswField;
	private JButton submit,refresh;
	private ImageIcon verifyImage;
	
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
		setTitle("登陆");
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
	
	private void checkLogin(){
		
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				if(!qqTextField.getText().trim().equals("") && loginSig!=null){
					imgLabel.setText("正在加载");
					Bot.getInstance().setQq(qqTextField.getText().trim());
					HttpHelper.checkLogin(new EventCallback() {

						@Override
						public void exec(boolean result) {
							if (result) {
								vcLabel.setVisible(true);
								vcTextField.setVisible(true);
								imgLabel.setVisible(true);
								vcTextField.setEditable(true);
								
								try {
									imgLabel.setIcon(new ImageIcon(HttpHelper.getVerifyImage(new EventCallback() {
										
										@Override
										public void exec(boolean result) {
											if (!result) {
												imgLabel.setText("加载失败，请重试");
											}else{
												imgLabel.setText("");
											}
										}
									})));
								} catch (NullPointerException e) {
									imgLabel.setText("加载失败，请重试");
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
					}
		});
		
	}

	private void setListener() {
		qqTextField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
						checkLogin();
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		imgLabel.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				checkLogin();
			}
		});
		
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
	
		imgLabel.setBounds(PADDING_LEFT+130,  PADDING_TOP+COMPOMENT_HEIGHT*2, 110, COMPOMENT_HEIGHT);
		
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
