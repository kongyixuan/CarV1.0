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
        String sql = "{CALL pro_num_user(?,?)}"; //���ô洢���� 
        CallableStatement cstm = connection.prepareCall(sql); //ʵ��������cstm 
        cstm.setString(1, "������"); //�洢����������� 
       //cstm.setInt(2, 2); // �洢����������� 
        cstm.registerOutParameter(2, Types.INTEGER); // ���÷���ֵ���� ������ֵ 
        cstm.execute(); // ִ�д洢���� 
        System.out.println(cstm.getInt(2)); 
        cstm.close(); 
        connection.close();
    	} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
    }
}
