package com.spring.redis;

import redis.clients.jedis.Jedis;
import redis.clients.util.SafeEncoder;

public class RedisCacheClientImpl implements SimpleCache {

    private JedisTemple jedisTemple;
    ObjectAndByte objectUtil=new ObjectAndByte();

    public RedisCacheClientImpl(JedisTemple jedisTemple) {
        this.jedisTemple = jedisTemple;
    }

    @Override
    public boolean add(final String key,  final Object valueObject) {
        try {
            jedisTemple.execute(new RedisPoolCallback<Boolean>() {
                @Override
                public Boolean doInJedis(Jedis jedis) {
                    jedis.set(SafeEncoder.encode(key), objectUtil.toByteArray(valueObject));
                    return true;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Object get(final String key) {

        return jedisTemple.execute(new RedisPoolCallback<Object>() {
            @Override
            public Object doInJedis(Jedis jedis) {
                byte[] cacheValue = jedis.get(SafeEncoder.encode(key));
                if (cacheValue != null) {
                    return objectUtil.toObject(cacheValue);
                }
                return null;
            }

        });
    }

    @Override
    public long delete(final String key) {
        return jedisTemple.execute(new RedisPoolCallback<Long>() {
            @Override
            public Long doInJedis(Jedis jedis) {
                return jedis.del(key);
            }
        });
    }

    @Override
    public boolean add(final String key, Object value, final int seconds) {
        try {
            this.add(key, value);
            jedisTemple.execute(new RedisPoolCallback<Long>() {
                @Override
                public Long doInJedis(Jedis jedis) {
                    return jedis.expire(key, seconds);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

	 @Override
	    public boolean exists(final String key) {
	        return jedisTemple.execute(new RedisPoolCallback<Boolean>() {
	            @Override
	            public Boolean doInJedis(Jedis jedis) {
	                return jedis.exists(key);
	            }
	        });
	    }
   
}
