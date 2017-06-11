package com.spring.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

public class JedisTemple {
	 /** 缓存客户端 **/
    private JedisPool jedisPool;// 非切片连接池

    public JedisTemple(JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    /**
     * @Title: execute
     * @Description: 执行{@link RedisPoolCallback#doInJedis(Jedis)}的方法
     * @param action
     * @return
     * @author 徐飞
     */
    public <T> T execute(RedisPoolCallback<T> action) {
        T value = null;
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return action.doInJedis(jedis);
        } catch (Exception e) {
            // 释放redis对象
            jedisPool.returnBrokenResource(jedis);
            e.printStackTrace();
        } finally {
            // 返还到连接池
            returnResource(jedisPool, jedis);
        }

        return value;
    }

    /** 
    * 返还到连接池 
    * @param pool  
    * @param redis 
    */
    private void returnResource(JedisPool pool, Jedis redis) {
        // 如果redis为空不返回
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
