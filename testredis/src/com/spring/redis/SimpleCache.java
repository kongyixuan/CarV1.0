package com.spring.redis;

public interface SimpleCache {
	/**
     * @Title: add
     * @Description: ���һ����������
     * @param key �ַ����Ļ���key
     * @param value ����Ļ�������
     * @return
     * @author 
     */
  public  boolean add(final String key,final Object value);
 
    /**
     * @Title: add
     * @Description: ����һ�����ݣ���ָ���������ʱ��
     * @param key
     * @param value
     * @param seconds
     * @return
     * @author 
     */
    boolean add(String key, Object value, int seconds);
 
    /**
     * @Title: get
     * @Description: ����key��ȡ��һֱֵ
     * @param key �ַ����Ļ���key
     * @return
     * @author 
     */
    Object get(String key);
 
    /**
     * @Title: delete
     * @Description: ɾ��һ����������
     * @param key �ַ����Ļ���key
     * @return
     * @author 
     */
    long delete(String key);
 
    /**
     * @Title: exists
     * @Description: �ж�ָ��key�Ƿ��ڻ������Ѿ�����
     * @param key �ַ����Ļ���key
     * @return
     * @author 
     */
    boolean exists(String key);
}
