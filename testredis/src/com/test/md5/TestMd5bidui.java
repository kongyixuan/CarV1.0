package com.test.md5;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

public class TestMd5bidui {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5",
        "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

/**
 * 转换字节数组为16进制字串
 * 
 * @param b
 *            字节数组
 * @return 16进制字串
 */
public static String byteArrayToHexString(byte[] b) {
    StringBuffer resultSb = new StringBuffer();
    for (int i = 0; i < b.length; i++) {
        resultSb.append(byteToHexString(b[i]));
    }
    return resultSb.toString();
}

private static String byteToHexString(byte b) {
    int n = b;
    if (n < 0)
        n = 256 + n;
    int d1 = n / 16;
    int d2 = n % 16;
    return hexDigits[d1] + hexDigits[d2];
}

public static String MD5Encode(String origin) {
    String resultString = null;
    try {
        resultString = new String(origin);
        MessageDigest md = MessageDigest.getInstance("MD5");
        resultString = byteArrayToHexString(md.digest(resultString
                .getBytes()));
    } catch (Exception ex) {
    }
    return resultString;
}

public static boolean isValidate(String input,String output){
    
    boolean status = false;
    
    if(TestMd5bidui.MD5Encode(input).equals(output)){
        status = true;
    }else{
        status = false;
    }
    
    return status;
}
// MD5加码。32位  
public static String MD5(String inStr) {  
 MessageDigest md5 = null;  
 try {  
  md5 = MessageDigest.getInstance("MD5");  
 } catch (Exception e) {  
  System.out.println(e.toString());  
  e.printStackTrace();  
  return "";  
 }  
 char[] charArray = inStr.toCharArray();  
 byte[] byteArray = new byte[charArray.length];  
 
 for (int i = 0; i < charArray.length; i++)  
  byteArray[i] = (byte) charArray[i];  
 
 byte[] md5Bytes = md5.digest(byteArray);  
 
 StringBuffer hexValue = new StringBuffer();  
 
 for (int i = 0; i < md5Bytes.length; i++) {  
  int val = ((int) md5Bytes[i]) & 0xff;  
  if (val < 16)  
   hexValue.append("0");  
  hexValue.append(Integer.toHexString(val));  
 }  
 
 return hexValue.toString();  
}  
// 可逆的加密算法  
public static String KL(String inStr) {  
 // String s = new String(inStr);  
 char[] a = inStr.toCharArray();  
 for (int i = 0; i < a.length; i++) {  
  a[i] = (char) (a[i] ^ 't');  
 }  
 String s = new String(a);
 try {
     s=s.getBytes("UTF-8").toString();
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
 return s;  
}   
//加密后解密  
public static String JM(String inStr) {  
char[] a = inStr.toCharArray();  
for (int i = 0; i < a.length; i++) {  
a[i] = (char) (a[i] ^ 't');  
}  
String k = new String(a);  
try {
	k=k.getBytes("UTF-8").toString();
} catch (UnsupportedEncodingException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
return k;  
}  
public static void main(String[] args) {
    
    System.out.println(MD5Encode("123"));
    System.out.println(KL(MD5("123")));
    String str=JM(KL(MD5("123")));
     System.out.println(str);
     System.out.println(MD5("bvhb"));
    boolean b = TestMd5bidui.isValidate("123", "202cb962ac59075b964b07152d234b70");
    System.out.println(b);
    
}
}
