package com.json.fast;

import java.io.IOException;  

import org.junit.Test;

import com.alibaba.fastjson.JSON;  
import com.fasterxml.jackson.core.JsonParser;  
import com.fasterxml.jackson.databind.JsonNode;  
import com.fasterxml.jackson.databind.ObjectMapper; 
import com.poi.excel.Student;
public class JSonObject {
	 /** 
     * ���ַ���ת��ʵ���࣬����б�ܵ��ַ��� 
     * jackson.databind jar
     * fastjson jar
     * jackson.core jar
     * 
     */  
    public static <T> T jsonToEntity(String json, Class<T> clazz) throws IOException {  
        ObjectMapper mapper = new ObjectMapper();  
        // ����б�˵��ַ�  
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS,true);  
        return mapper.readValue(json, clazz);  
    }  
      
    /** 
     * ʵ����תJSON�ַ��� 
     */  
    public static String entityToJson(Object entity){  
        return JSON.toJSONString(entity);  
    }  
      
    /** 
     * ���ַ���ת��JsonNode������б�ܵ��ַ��� 
     */  
    public static JsonNode jsonToJsonNode(String json) throws IOException {  
        ObjectMapper mapper = new ObjectMapper();  
        // ����б�˵��ַ�  
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS,true);  
        //��������  
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES,true);  
        return mapper.readValue(json, JsonNode.class);  
    }  
      
    public static <T> String objectToJson(Object object, Class<T> cls)throws Exception {  
        ObjectMapper mapper = new ObjectMapper();  
        mapper.registerSubtypes(cls);  
        String reqJson = mapper.writeValueAsString(object);  
        return reqJson;  
    }  
    @Test
      public void test(){
    	Student stu=new Student();
    	stu.setId(1L);
    	stu.setName("kzz");
    	stu.setSex(false);
    	String strStudent=entityToJson(stu);
   
    	System.out.print(strStudent);
      }
}
