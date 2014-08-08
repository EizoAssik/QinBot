package com.sssta.qinbot.util;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import sun.net.www.http.HttpClient;

import com.sssta.qinbot.Event.EventCallback;
import com.sssta.qinbot.core.Bot;
import com.sssta.qinbot.model.Cookie;
import com.sssta.qinbot.model.VerifyCodeChecker;



public class HttpHelper {
	
	public static final String URL_REFER = "http://web2.qq.com/webqq.html";  
	public static final String URL_REFER_Q = "https://ui.ptlogin2.qq.com/cgi-bin/login?daid=164&target=self&style=5&mibao_css=m_webqq&appid=1003903&enable_qlogin=0&no_verifyimg=1&s_url=http%3A%2F%2Fweb2.qq.com%2Floginproxy.html&f_url=loginerroralert&strong_login=1&login_state=10&t=20140612002";
	
	//uni QQ号   login_sig 通过getLoginSig获得    r 一个随机数
    public static final String URL_FORMAT_CHECK = "https://ssl.ptlogin2.qq.com/check?uin=%s&appid=1003903&js_ver=10087&js_type=0&login_sig=%s&u1=http%%3A%%2F%%2Fweb2.qq.com%%2Floginproxy.html&r=%f";
      //u qq号 p 加密码  verifycode   login_sig    verifysession
    public static final String URL_FORMAT_LOGIN = "https://ssl.ptlogin2.qq.com/login?u=%s&p=%s&verifycode=%s&webqq_type=10&remember_uin=1&login2qq=1&aid=1003903&u1=http%%3A%%2F%%2Fweb2.qq.com%%2Floginproxy.html%%3Flogin2qq%%3D1%%26webqq_type%%3D10&h=1&ptredirect=0&ptlang=2052&daid=164&from_ui=1&pttype=1&dumy=&fp=loginerroralert&action=6-31-678356&mibao_css=m_webqq&t=1&g=1&js_type=0&js_ver=10088&login_sig=%s&pt_uistyle=5&pt_vcode_v1=0&pt_verifysession_v1=%s";
    
    private static HashMap<String, Cookie> cookieMap = new HashMap<String, Cookie>();
	private static StringBuilder cookieCache = new StringBuilder();

	
	 public static  String sendPost(String url, String contents,String refer){  
		 InputStreamReader inr = null;
	        try{   
	            System.out.println("post>>>"+url);  
	               
	            URL serverUrl = new URL(url);  
	            HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();   
	            conn.setRequestMethod("POST");//"POST" ,"GET"   
	             
	            if(refer != null){  
	                conn.addRequestProperty("Referer", refer);  
	            }else{
	            	conn.addRequestProperty("Referer", URL_REFER);  
	            }
	            conn.addRequestProperty("Cookie", getCookie());  
	            conn.addRequestProperty("Accept-Charset", "UTF-8;");//GB2312,  
	            conn.addRequestProperty("Connection", "keep-alive");
	            conn.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	            conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36");  
	            conn.setDoOutput(true);   
	            conn.connect();  
	              
	            conn.getOutputStream().write(contents.getBytes());  
	              
	            if(conn.getHeaderFields().get("Set-Cookie") != null){  
	                for(String s:conn.getHeaderFields().get("Set-Cookie")){  
	                    addCookie(new Cookie(s));
	                }  
	            }  
	              
	            InputStream ins =  conn.getInputStream();  
	              
	            String charset = "UTF-8";   
	            inr = new InputStreamReader(ins, charset);  
	            BufferedReader bfr = new BufferedReader(inr);  
	             
	            String line = "";  
	            StringBuffer res = new StringBuffer();   
	            do{  
	                res.append(line);  
	                line = bfr.readLine();  
	               //System.out.println(line);  
	            }while(line != null);  
	            
	         //   System.out.println(">>>==="+res);  
	              
	            return res.toString();  
	        }catch(Exception e){  
	            e.printStackTrace();  
	            return null;  
	        }   finally{
	        	if(inr!=null){
	        		try {
	        			inr.close();
					} catch (IOException e) {
						e.printStackTrace();
					}finally{
						inr =null;
					}
	        		
	        	}
	 
	        }
	    }  
	      
	      
	   
	    public static String sendGet(String url,String refer){ 
	    	InputStreamReader inr = null;
	        try{   
	            System.out.println("get>>>"+url);  
	               
	            URL serverUrl = new URL(url);  
	            HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();   
	            conn.setRequestMethod("GET");//"POST" ,"GET"  
	            conn.setDoOutput(true);   
	            if(refer != null){  
	                conn.addRequestProperty("Referer", refer);  
	            }else{
	            	conn.addRequestProperty("Referer", URL_REFER);  
	            }
	            System.out.println("跳转前——"+conn.getURL().getPath());
	            conn.addRequestProperty("Cookie", getCookie());  
	            conn.addRequestProperty("Accept-Charset", "UTF-8;");//GB2312,  
	            conn.addRequestProperty("Connection", "keep-alive");
	            conn.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	            conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36");
	            conn.setFollowRedirects(false);
	            conn.connect();  
	             
	            if(conn.getHeaderFields().get("Set-Cookie") != null){  
	                for(String s:conn.getHeaderFields().get("Set-Cookie")){  
	                    addCookie(new Cookie(s));
	                }  
	            }  
	            System.out.println("跳转后——"+conn.getURL().getPath());
	            InputStream ins =  conn.getInputStream();  
	              
	            String charset = "UTF-8";   
	            inr = new InputStreamReader(ins, charset);  
	            BufferedReader bfr = new BufferedReader(inr);  
	             
	            String line = "";  
	            StringBuffer res = new StringBuffer();   
	            do{  
	                res.append(line);  
	                line = bfr.readLine();  
	               //System.out.println(line);  
	            }while(line != null);  
	            
	       //   System.out.println(">>>==="+res);  
	              
	            return res.toString();  
	        }catch(Exception e){  
	            e.printStackTrace();  
	            return null;  
	        }  finally{
	        	if(inr!=null){
	        		try {
	        			inr.close();
					} catch (IOException e) {
						e.printStackTrace();
					}finally{
						inr =null;
					}
	        		
	        	}
	 
	        }
	    }  
	    
	    public static String getLoginSig(){
	    	String responseString = sendGet(URL_REFER_Q,null);
	    	Pattern pattern = Pattern.compile("g_login_sig=encodeURIComponent\\(\"(.*?)\"\\);");
	    	Matcher mat = pattern.matcher(responseString);
	    	if(mat.find())
	    	 return mat.group(1);
	    	else return "";
	    }
	    
	    public static void checkLogin(EventCallback event){
	    	String responseString = sendGet(String.format(URL_FORMAT_CHECK,
	    			Bot.getInstance().getQQ()
	    			,Bot.getInstance().getLoginSig()
	    			,new Random().nextDouble()),URL_REFER_Q);
	    	try {
				VerifyCodeChecker checker = ResponseParser.parseVC(responseString);
					if(event!=null){
						event.exec(checker.isNeed());
						Bot.getInstance().attachChecker(checker);
					}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    
	    public static BufferedImage getVerifyImage(EventCallback callback){
	    	BufferedImage image = null;
	    	HttpURLConnection conn = null;
	    	InputStream is = null;
	        try {
	        	Bot bot = Bot.getInstance();
	        	URL serverUrl = new URL(String.format("https://ssl.captcha.qq.com/getimage?&uin=%s&aid=1003903&%f&cap_cd=%s",bot.getQQ(),new Random().nextDouble(),bot.getLoginSig()));  
	            conn = (HttpURLConnection) serverUrl.openConnection();   
	            conn.setRequestMethod("GET");//"POST" ,"GET"  
	           // conn.setDoOutput(true);   
	           
	            conn.addRequestProperty("Referer", URL_REFER_Q);  
	            conn.addRequestProperty("Cookie", getCookie());  
	            conn.addRequestProperty("Accept-Charset", "UTF-8;");//GB2312, 
	            conn.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	            conn.addRequestProperty("Connection", "keep-alive");
	            conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36");
	            conn.connect();  
	             
	            if(conn.getHeaderFields().get("Set-Cookie") != null){  
	                for(String s:conn.getHeaderFields().get("Set-Cookie")){  
	                    addCookie(new Cookie(s));
	                }  
	                
	   	           	Bot.getInstance().setVerifySession(cookieMap.get("verifysession").getValue());
	             
	            }  
	            is =  conn.getInputStream();  
	            image = ImageIO.read(is);
	        } catch (Exception e) {
	           if (callback!=null) {
	        	   callback.exec(false);
	           }
			}finally{
	        	if(is!=null){
	        		try {
						is.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally{
						is  =null;
					}
	        		
	        	}
	 
	        }
	        if (callback!=null) {
				callback.exec(true);
			}
	        return image;
	    }
	    
	    public static String getCookie(){
	    	return cookieCache.toString();
	    }
	    
	    public static String getCookie(boolean focusRefresh){
	    	if (focusRefresh) {
	    		cookieCache = new StringBuilder();
	    		Set<String> keys = cookieMap.keySet();
		    	Iterator<String> iterator = keys.iterator();
		    	while (iterator.hasNext()) {
					addCookie(cookieMap.get(iterator.next()));
				}
			}
	    	return cookieCache.toString();
	    }
	    
	    public static void addCookie(Cookie cookie){
	    	cookieMap.put(cookie.getName(), cookie);
    		cookieCache.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
	    }
	   
	    public static String getCookie(String key){
	    	return cookieMap.get(key).getValue();
	    }
}