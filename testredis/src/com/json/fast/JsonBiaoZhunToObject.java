package com.json.fast;

import org.junit.Test;

public class JsonBiaoZhunToObject {
	/*{
	    "code": "200",
	    "message": "��������",
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
	/*������Զ�Ӧ����һ���ַ��������֣������η�ʹ�� String �����������C �磨public String code;��
	������Զ�Ӧ����һ�� JSONArray�������η�ʹ�� ArrayList, �������� Bean Ҫ���� JSONArray ����� JSONObject ���б�д�� �����������C �磨public ArrayList kk;��
	������Զ�Ӧ����һ�� JSONObject�������η�ʹ�ö����������-�磺(public static class MyData)
	*/
	@Test
	public void   parse() {
		String string = "{\"code\":\"200\",\"message\":\"��������\",\"data\":{\"imgs\":[\"http://7xw2my.com1.z0.glb.clouddn.com/qiniu/2016-7/0f0b37c0786efa955beec97b56fcda6f.jpg\"],\"kk\":[{\"sdfdsf\":\"sss\"},{\"sdfdsf\":\"ssss\"}]}}";
		JsonBean jsonBean=(JsonBean)JsonParse.parse(string, JsonBean.class);
       String[] jsonimStrings=jsonBean.getData().getImgs();
       System.out.print(jsonimStrings);
	}
	
	
}
