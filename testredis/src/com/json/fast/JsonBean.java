package com.json.fast;

import java.util.ArrayList;

public class JsonBean {
	 private String code;
	    private String message;
	    private MyData data; 
	    public String getMessage() {
	        return message;
	    }
	    public void setMessage(String message) {
	        this.message = message;
	    }
	    public static class Data2 {
	        private String sdfdsf;
	 
	        public String getSdfdsf() {
	            return sdfdsf;
	        }
	 
	        public void setSdfdsf(String sdfdsf) {
	            this.sdfdsf = sdfdsf;
	        }
	 
	    }
	 
	    public static class MyData {
	        private ArrayList kk;
	        private String[] imgs;
	        public ArrayList getKk() {
	            return kk;
	        }
	        public void setKk(ArrayList kk) {
	            this.kk = kk;
	        }
	        public String[] getImgs() {
	            return imgs;
	        }
	        public void setImgs(String[] imgs) {
	            this.imgs = imgs;
	        }
	    }
	    public MyData getData() {
	        return data;
	    }
	    public void setData(MyData data) {
	        this.data = data;
	    }
	    public String getCode() {
	        return code;
	    }
	    public void setCode(String code) {
	        this.code = code;
	    }
	
}
