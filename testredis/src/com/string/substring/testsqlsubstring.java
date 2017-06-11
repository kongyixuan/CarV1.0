package com.string.substring;

import org.junit.Test;

public class testsqlsubstring {
@Test
	public void testSql(){
		StringBuffer sb = new StringBuffer();
		sb = sb.append("from Admin where 1=1 and isDelete=0");
		String sql=sb.toString();
		System.out.print(sql.indexOf("from"));
		System.out.print(sql.substring(sql.indexOf("from")));
	}
}
