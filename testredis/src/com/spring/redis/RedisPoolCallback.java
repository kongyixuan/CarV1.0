package com.spring.redis;

import redis.clients.jedis.Jedis;

public interface RedisPoolCallback<T> {
	T doInJedis(Jedis jedis);
}
