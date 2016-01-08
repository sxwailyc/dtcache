package org.naike.dtcache.demo;

import org.naike.dtcache.Cache;
import org.naike.dtcache.core.LocalCache;

import redis.clients.jedis.Jedis;

public class UserDao {

	private Cache cache = new LocalCache();

	public User getUser(String userId) {

		User user = cache.get(userId, User.class);
		if (user == null) {
			Jedis jedis = new Jedis(Config.REDIS_HOST, Config.REDIS_PORT);
			try {
				String json = jedis.hget("user_cache", userId);
				if (json != null) {
					user = Json.toObject(json, User.class);
					cache.put(userId, user);
				}
			} finally {
				jedis.close();
			}
			System.out.println("read from redis");
		} else {
			System.out.println("read from local cache");
		}

		return user;
	}

	public void add(User user) {

		Jedis jedis = new Jedis(Config.REDIS_HOST, Config.REDIS_PORT);
		try {
			String json = Json.toJson(user);
			jedis.hset("user_cache", user.getUserId(), json);
		} finally {
			jedis.close();
		}

	}

	public void update(User user) {

		Jedis jedis = new Jedis(Config.REDIS_HOST, Config.REDIS_PORT);
		try {
			String json = Json.toJson(user);
			jedis.hset("user_cache", user.getUserId(), json);
			cache.delete(User.class, user.getUserId());
		} finally {
			jedis.close();
		}

	}
}
