package com.test.idcard;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class IDCardAuth {
	public static String request(String httpUrl, String httpArg) {
	    BufferedReader reader = null;
	    String result = null;
	    StringBuffer sbf = new StringBuffer();
	    httpUrl = httpUrl + "?" + httpArg;

	    try {
	        URL url = new URL(httpUrl);
	        HttpURLConnection connection = (HttpURLConnection) url
	                .openConnection();
	        connection.setRequestMethod("GET");
			
	        // 填入apix-key到HTTP header
	        connection.setRequestProperty("apix-key", "asd56asdsadas******************");
	        connection.connect();
	        InputStream is = connection.getInputStream();
	        reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
	        String strRead = null;
	        while ((strRead = reader.readLine()) != null) {
	            sbf.append(strRead);
	            sbf.append("\r\n");
	        }
	        reader.close();
	        result = sbf.toString();
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return result;
	}

	public static void main(String[] args) {
		//发送 GET 请求
				String httpUrl = "http://v.apix.cn/apixcredit/idcardauth/idcardauth";
				String httpArg = "name=张澜&bankcardno=5218995918250497";
				String jsonResult = request(httpUrl, httpArg);
				System.out.println(jsonResult);

	}

}
