package org.naike.dtcache.demo;

import org.naike.dtcache.config.CacheConfig;
import org.naike.dtcache.core.LocalCache;
import org.naike.dtcache.listener.CacheItemListener;
import org.naike.dtcache.listener.CacheItemListenerRedisImpl;

import redis.clients.jedis.Jedis;

/**
 * Hello world!
 * 
 */
public class App2 {

	public static void main(String[] args) {

		CacheItemListener cacheItemListener = new CacheItemListenerRedisImpl() {

			@Override
			public Jedis getRedis() {
				return new Jedis(Config.REDIS_HOST, Config.REDIS_PORT);
			}

			@Override
			public void closeRedis(Jedis jedis) {
				jedis.close();
			}
		};

		CacheConfig config = new CacheConfig.Builder().cacheItemListener(cacheItemListener).build();

		LocalCache.start(config);

		UserDao userDao = new UserDao();
		User user = userDao.getUser("1");
		if (user != null) {
			user.setUsername("new username");
			userDao.update(user);
		}

	}
}
