package com.sssta.qinbot.swing;

import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GraphicsConfiguration;
import java.awt.TextField;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginDialog extends JDialog {
	private JLabel qqLabel,pswLabel,vcLabel;
	private JTextField qqTextField,vcTextField;
	private JPasswordField pswField;
	private JButton submit,refresh;
	private static final int WIDTH =280;
	private static final int HEIGHT = 210;
	private MainWindow mainWindow;
	private static final int PADDING_LEFT = 20;
	private static final int PADDING_TOP = 20;
	private static final int COMPOMENT_HEIGHT = 30;

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

	}

	private void setListener() {
		qqTextField.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				
			}
			
			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	private void initCompoments() {
		
		this.setLayout(null);
		
		qqLabel = new JLabel("QQ号：");
		pswLabel = new JLabel("密码：");
		vcLabel = new JLabel("验证码：");
		
		submit = new JButton("登录");
		
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
	
		submit.setBounds(100, PADDING_TOP+COMPOMENT_HEIGHT*3+15, 80, COMPOMENT_HEIGHT);
		
		
		add(qqLabel);
		add(qqTextField);
		add(pswLabel);
		add(pswField);
		add(vcLabel);
		add(vcTextField);
		add(submit);
	
	}

	

}
