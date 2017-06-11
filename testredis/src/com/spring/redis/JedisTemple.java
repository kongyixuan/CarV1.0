package com.spring.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisTemple {
	 /** ����ͻ��� **/
    private JedisPool jedisPool;// ����Ƭ���ӳ�

    public JedisTemple(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * @Title: execute
     * @Description: ִ��{@link RedisPoolCallback#doInJedis(Jedis)}�ķ���
     * @param action
     * @return
     * @author ���
     */
    public <T> T execute(RedisPoolCallback<T> action) {
        T value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return action.doInJedis(jedis);
        } catch (Exception e) {
            // �ͷ�redis����
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // ���������ӳ�
            returnResource(jedisPool, jedis);
        }

        return value;
    }

    /** 
    * ���������ӳ� 
    * @param pool  
    * @param redis 
    */
    private void returnResource(JedisPool pool, Jedis redis) {
        // ���redisΪ�ղ�����
        if (redis != null) {
            pool.returnResource(redis);
        }
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public void setJedisPool(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

}
