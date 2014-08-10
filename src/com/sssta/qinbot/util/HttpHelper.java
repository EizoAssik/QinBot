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
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import sun.net.httpserver.DefaultHttpServerProvider;
import sun.net.www.http.HttpClient;
import atg.taglib.json.util.Cookie;

import com.sssta.qinbot.core.Bot;
import com.sssta.qinbot.core.Poller;
import com.sssta.qinbot.event.EventCallback;
import com.sssta.qinbot.model.BotCookie;
import com.sssta.qinbot.model.VerifyCodeChecker;
import com.sun.jndi.url.ldaps.ldapsURLContextFactory;



public class HttpHelper {
	public static final String PROPERTY_REFER = "Referer";
	public static final String PROPERTY_ACCEPT = "Accept";
	public static final String PROPERTY_ACCEPT_CHARSET = "Accept-Charset";
	public static final String PROPERTY_ACCEPT_ENCODING = "Accept-Encoding";
	public static final String PROPERTY_ACCEPT_LANGUAGE = "Accept-Language";
	public static final String PROPERTY_HOST = "Host";
	public static final String PROPERTY_ORIGIN = "Origin";
	public static final String PROPERTY_CONNECTION = "Connection";
	public static final String PROPERTY_CONTETN_TYPE = "Content-Type";

	
	
	public static final String URL_POLL = "http://d.web2.qq.com/channel/poll2";
	public static final String URL_GET_INFO_GROUP = "http://s.web2.qq.com/api/get_group_name_list_mask2";
	public static final String URL_GET_FRIENDS = "http://s.web2.qq.com/api/get_user_friends2";
	public static final String URL_SEND_GROUP = "http://d.web2.qq.com/channel/send_qun_msg2";

	public static final String URL_REFER_LOGIN_1 ="http://d.web2.qq.com/proxy.html?v=20110331002&callback=1&id=2";
	public static final String URL_REFER = "http://web2.qq.com/webqq.html";  
	public static final String URL_REFER_Q = "https://ui.ptlogin2.qq.com/cgi-bin/login?daid=164&target=self&style=5&mibao_css=m_webqq&appid=1003903&enable_qlogin=0&no_verifyimg=1&s_url=http%3A%2F%2Fweb2.qq.com%2Floginproxy.html&f_url=loginerroralert&strong_login=1&login_state=10&t=20140612002";
	public static final String URL_REFER_POLL = "http://d.web2.qq.com/proxy.html?v=20110331002&callback=1&id=2";
	public static final String URL_REFER_GET_INFO = "http://s.web2.qq.com/proxy.html?v=20110412001&callback=1&id=3";
	//uni QQ号   login_sig 通过getLoginSig获得    r 一个随机数
    public static final String URL_FORMAT_CHECK = "https://ssl.ptlogin2.qq.com/check?uin=%s&appid=1003903&js_ver=10087&js_type=0&login_sig=%s&u1=http%%3A%%2F%%2Fweb2.qq.com%%2Floginproxy.html&r=%f";
      //u qq号 p 加密码  verifycode   login_sig    verifysession
    public static final String URL_FORMAT_LOGIN = "https://ssl.ptlogin2.qq.com/login?u=%s&p=%s&verifycode=%s&webqq_type=10&remember_uin=1&login2qq=1&aid=1003903&u1=http%%3A%%2F%%2Fweb2.qq.com%%2Floginproxy.html%%3Flogin2qq%%3D1%%26webqq_type%%3D10&h=1&ptredirect=0&ptlang=2052&daid=164&from_ui=1&pttype=1&dumy=&fp=loginerroralert&action=6-31-678356&mibao_css=m_webqq&t=1&g=1&js_type=0&js_ver=10088&login_sig=%s&pt_uistyle=5&pt_vcode_v1=0&pt_verifysession_v1=%s";
    
    //uni 临时号  verifysession  就叫这个名字  vfwebqq 同上  t 时间
    public static final String URL_FORMAT_GET_FRIEND_QQ = "http://s.web2.qq.com/api/get_friend_uin2?tuin=%s&verifysession=%s&type=1&code=&vfwebqq=%s&t=%d";
    
    private static HashMap<String, BotCookie> cookieMap = new HashMap<String, BotCookie>();
	private static StringBuilder cookieCache = new StringBuilder();

	
	 public static  String sendPost(String url, String contents,HashMap<String, String> propertyMap){  
		 InputStreamReader inr = null;
	        try{   
	            System.out.println("post>>>"+url);  
	            URL serverUrl = new URL(url);  
	            HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();   
	            conn.setRequestMethod("POST");//"POST" ,"GET"   
	             
	            if(propertyMap != null){  
	            	Set<String> keys = propertyMap.keySet();
			    	Iterator<String> iterator = keys.iterator();
			    	while (iterator.hasNext()) {
			    		String key = iterator.next();
			            conn.addRequestProperty(key, propertyMap.get(key));  
			    	}
	            }
	            
	            conn.addRequestProperty("Cookie", getCookie());  
	            conn.addRequestProperty("Accept-Charset", "UTF-8;");//GB2312,  
	            conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36");
	            conn.setDoInput(true);
	            conn.setDoOutput(true);   
	            conn.connect();  
	              
	            if (contents!=null) {
		            conn.getOutputStream().write(contents.getBytes());  
				}
	            if(conn.getHeaderFields().get("Set-Cookie") != null){  
	                for(String s:conn.getHeaderFields().get("Set-Cookie")){  
	                    addCookie(new BotCookie(s));
	                }  
	            }  
	              
	            InputStream ins =  conn.getInputStream();  
	              
	            inr = new InputStreamReader(ins, "UTF-8");  
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
	      
	    public static String sendGet(String url,HashMap<String, String> propertyMap){ 
	    	InputStreamReader inr = null;
	        try{   
	            System.out.println("get>>>"+url);  
	               
	            URL serverUrl = new URL(url);  
	            HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();   
	            conn.setRequestMethod("GET");//"POST" ,"GET"  
	            conn.setDoOutput(true);   
	            if(propertyMap != null){  
	            	Set<String> keys = propertyMap.keySet();
			    	Iterator<String> iterator = keys.iterator();
			    	while (iterator.hasNext()) {
			    		String key = iterator.next();
			            conn.addRequestProperty(key, propertyMap.get(key));  
			    	}
	            }
	            conn.addRequestProperty("Cookie", getCookie());  
	            conn.addRequestProperty("Accept-Charset", "UTF-8;");//GB2312,  
	            conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36");
	            HttpURLConnection.setFollowRedirects(false);
	            conn.connect();  
	             
	            if(conn.getHeaderFields().get("Set-Cookie") != null){  
	                for(String s:conn.getHeaderFields().get("Set-Cookie")){  
	                    addCookie(new BotCookie(s));
	                }  
	            }  
	            InputStream ins =  conn.getInputStream();  
	            inr = new InputStreamReader(ins, "UTF-8");  
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
	    
	    public static BufferedImage getImage(String url,String refer,EventCallback callback){
	    	BufferedImage image = null;
	    	HttpURLConnection conn = null;
	    	InputStream is = null;
	        try {
	        	URL serverUrl = new URL(url);  
	            conn = (HttpURLConnection) serverUrl.openConnection();   
	            conn.setRequestMethod("GET");//"POST" ,"GET"  
	           // conn.setDoOutput(true);   
	            if (refer!=null) {
	            	conn.addRequestProperty("Referer",refer);  
				}else {
		            conn.addRequestProperty("Referer", URL_REFER_Q);  
				}
	            conn.addRequestProperty("Cookie", getCookie());  
	            conn.addRequestProperty("Accept-Charset", "UTF-8;");//GB2312, 
	            conn.addRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
	            conn.addRequestProperty("Connection", "keep-alive");
	            conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36");
	            conn.connect();  
	             
	            if(conn.getHeaderFields().get("Set-Cookie") != null){  
	                for(String s:conn.getHeaderFields().get("Set-Cookie")){  
	                    addCookie(new BotCookie(s));
	                }  
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
	        String verifyString = HttpHelper.getCookie("verifysession");
	        if (verifyString!=null) {
		        Bot.getInstance().setVerifySession(verifyString);
		        HttpHelper.addCookie(new BotCookie("ptvfsession",verifyString));
			}
	        if (callback!=null) {
				callback.exec(true);
			}
	        return image;
	    }
	    
	    public static String getCookie(){
//	    	if (cookieCache==null) {
//				refreshCookieCache();
//			}
	    	refreshCookieCache();
	    	return cookieCache.toString();
	    }
	    
	    public static String getCookie(boolean focusRefresh){
	    	if (focusRefresh) {
	    		refreshCookieCache();
			}
	    	return cookieCache.toString();
	    }
	    
	    public static void addCookie(BotCookie cookie){
    		cookieCache.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
	    	cookieMap.put(cookie.getName(), cookie);
	    }
	   
	    public static String getCookie(String key){
	    	BotCookie cookie = cookieMap.get(key);
	    	if (cookie!=null) {
	    		return cookie.getValue();
			}else {
				return null;
			}
	    }
	    
	    public static String getCookies(String[] keys){
	    	StringBuilder cookiesBuilder = new StringBuilder();
	    	for (String key:keys) {
	    		BotCookie cookie = cookieMap.get(key);
	    		cookiesBuilder.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
			}
	    	return cookiesBuilder.toString();
	    }



		public static void refreshCookieCache() {
	    		cookieCache = new StringBuilder();
	    		Set<String> keys = cookieMap.keySet();
		    	Iterator<String> iterator = keys.iterator();
		    	while (iterator.hasNext()) {
		    		BotCookie cookie = cookieMap.get(iterator.next());
		    		cookieCache.append(cookie.getName()).append("=").append(cookie.getValue()).append(";");
				}
		}
		
		public static void clearCookieCache(){
			cookieCache = null;
		}
}