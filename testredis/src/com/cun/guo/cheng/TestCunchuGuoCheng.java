package com.cun.guo.cheng;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Types;

import org.junit.Test;

public class TestCunchuGuoCheng {
	public static final String DRIVER_CLASS = "com.mysql.jdbc.Driver"; 
    public static final String URL = "jdbc:mysql://127.0.0.1:3306/db_zsl"; 
    public static final String USERNAME = "root"; 
    public static final String PASSWORD = "123456";
    @Test
    public void TestMysql(){
    	try {
	    Class.forName(DRIVER_CLASS);
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD); 
        String sql = "{CALL pro_num_user(?,?)}"; //调用存储过程 
        CallableStatement cstm = connection.prepareCall(sql); //实例化对象cstm 
        cstm.setString(1, "孔艺轩"); //存储过程输入参数 
       //cstm.setInt(2, 2); // 存储过程输入参数 
        cstm.registerOutParameter(2, Types.INTEGER); // 设置返回值类型 即返回值 
        cstm.execute(); // 执行存储过程 
        System.out.println(cstm.getInt(2)); 
        cstm.close(); 
        connection.close();
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
}
