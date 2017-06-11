package com.json.fast;

import org.junit.Test;

public class JsonBiaoZhunToObject {
	/*{
	    "code": "200",
	    "message": "测试数据",
	    "data": {
	        "imgs": [
	            "http://7xw2my.com1.z0.glb.clouddn.com/qiniu/2016-7/0f0b37c0786efa955beec97b56fcda6f.jpg"
	        ],
	        "kk": [
	            {
	                "sdfdsf": "sss"
	            },
	            {
	                "sdfdsf": "ssss"
	            }
	        ]
	    }
	}*/
	/*如果属性对应的是一个字符串或数字，其修饰符使用 String —————– 如（public String code;）
	如果属性对应的是一个 JSONArray，其修饰符使用 ArrayList, 这个里面的 Bean 要根据 JSONArray 里面的 JSONObject 进行编写。 —————– 如（public ArrayList kk;）
	如果属性对应的是一个 JSONObject，其修饰符使用对象的类名—-如：(public static class MyData)
	*/
	@Test
	public void   parse() {
		String string = "{\"code\":\"200\",\"message\":\"测试数据\",\"data\":{\"imgs\":[\"http://7xw2my.com1.z0.glb.clouddn.com/qiniu/2016-7/0f0b37c0786efa955beec97b56fcda6f.jpg\"],\"kk\":[{\"sdfdsf\":\"sss\"},{\"sdfdsf\":\"ssss\"}]}}";
		JsonBean jsonBean=(JsonBean)JsonParse.parse(string, JsonBean.class);
       String[] jsonimStrings=jsonBean.getData().getImgs();
       System.out.print(jsonimStrings);
	}
	
	
}
