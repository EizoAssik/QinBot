package com.sssta.qinbot.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HttpHelper {
	
	public static final  String LOGIN_SIG_URL = "https://ui.ptlogin2.qq.com/cgi-bin/login?daid=164&target=self&style=5&mibao_css=m_webqq&appid=1003903&enable_qlogin=0&no_verifyimg=1&s_url=http%3A%2F%2Fweb2.qq.com%2Floginproxy.html&f_url=loginerroralert&strong_login=1&login_state=10&t=20140612002";
	public static final  String refer = "http://web2.qq.com/webqq.html";  
    
    private static  String cookie = ""; 
	
	 public static  String sendPost(String url, String contents){  
	        try{   
	            System.out.println("post>>>"+url);  
	               
	            URL serverUrl = new URL(url);  
	            HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();   
	            conn.setRequestMethod("POST");//"POST" ,"GET"   
	             
	            if(refer != null){  
	                conn.addRequestProperty("Referer", refer);  
	            }  
	            conn.addRequestProperty("Cookie", cookie);  
	            conn.addRequestProperty("Accept-Charset", "UTF-8;");//GB2312,  
	            conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36");  
	            conn.setDoOutput(true);   
	            conn.connect();  
	              
	            conn.getOutputStream().write(contents.getBytes());  
	              
	            if(conn.getHeaderFields().get("Set-Cookie") != null){  
	                for(String s:conn.getHeaderFields().get("Set-Cookie")){  
	                    cookie += s;  
	                }  
	            }  
	              
	            InputStream ins =  conn.getInputStream();  
	              
	            String charset = "UTF-8";   
	            InputStreamReader inr = new InputStreamReader(ins, charset);  
	            BufferedReader bfr = new BufferedReader(inr);  
	             
	            String line = "";  
	            StringBuffer res = new StringBuffer();   
	            do{  
	                res.append(line);  
	                line = bfr.readLine();  
	               //System.out.println(line);  
	            }while(line != null);  
	            
	            System.out.println(">>>==="+res);  
	              
	            return res.toString();  
	        }catch(Exception e){  
	            e.printStackTrace();  
	            return null;  
	        }  
	    }  
	      
	      
	   
	    public static String sendGet(String url){  
	        try{   
	            System.out.println("get>>>"+url);  
	               
	            URL serverUrl = new URL(url);  
	            HttpURLConnection conn = (HttpURLConnection) serverUrl.openConnection();   
	            conn.setRequestMethod("GET");//"POST" ,"GET"  
	           // conn.setDoOutput(true);   
	            if(refer != null){  
	                conn.addRequestProperty("Referer", refer);  
	            }  
	            conn.addRequestProperty("Cookie", cookie);  
	            conn.addRequestProperty("Accept-Charset", "UTF-8;");//GB2312,  
	            conn.addRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.125 Safari/537.36");
	            conn.connect();  
	             
	            if(conn.getHeaderFields().get("Set-Cookie") != null){  
	                for(String s:conn.getHeaderFields().get("Set-Cookie")){  
	                    cookie += s;  
	                }  
	            }  
	            InputStream ins =  conn.getInputStream();  
	              
	            String charset = "UTF-8";   
	            InputStreamReader inr = new InputStreamReader(ins, charset);  
	            BufferedReader bfr = new BufferedReader(inr);  
	             
	            String line = "";  
	            StringBuffer res = new StringBuffer();   
	            do{  
	                res.append(line);  
	                line = bfr.readLine();  
	               //System.out.println(line);  
	            }while(line != null);  
	            
	            System.out.println(">>>==="+res);  
	              
	            return res.toString();  
	        }catch(Exception e){  
	            e.printStackTrace();  
	            return null;  
	        }  
	    }  
	    
	    public static String getLoginSig(){
	    	String responseString = sendGet(LOGIN_SIG_URL);
	    	Pattern pattern = Pattern.compile("g_login_sig=encodeURIComponent\\(\"(.*?)\"\\);");
	    	Matcher mat = pattern.matcher(responseString);
	    	if(mat.find())
	    	 return mat.group(1);
	    	else return "";
	    }
	    
	    public static boolean checkLogin(){
	    	return false;
	    }
	    
}