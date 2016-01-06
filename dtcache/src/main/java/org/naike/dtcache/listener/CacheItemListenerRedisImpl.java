package org.naike.dtcache.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.naike.dtcache.callback.CacheItemChangeCallback;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

public abstract class CacheItemListenerRedisImpl implements CacheItemListener {

	protected static Log logger = LogFactory.getLog(CacheItemListenerRedisImpl.class);

	public final static String MEMORY_CACHE_RECORD_CHANGE_CHANNEL = "memory-cache-record-change-channel";

	public abstract Jedis getRedis();

	public abstract void closeRedis(Jedis jedis);

	@Override
	public void onChange(String className, String id) {
		Jedis jedis = getRedis();
		try {
			String message = className + ":" + id;
			logger.debug("publish message:" + message);
			jedis.publish(MEMORY_CACHE_RECORD_CHANGE_CHANNEL, message);
		} finally {
			closeRedis(jedis);
		}
	}

	@Override
	public void listen(final CacheItemChangeCallback callback) {

		Jedis jedis = getRedis();

		try {
			jedis.subscribe(new JedisPubSubImpl() {

				@Override
				public void onMessage(String channel, String message) {
					logger.debug("message:" + message);
					String[] datas = message.split(":");
					callback.call(datas[0], datas[1]);
				}
			}, MEMORY_CACHE_RECORD_CHANGE_CHANNEL);
		} finally {
			closeRedis(jedis);
		}

	}

	abstract class JedisPubSubImpl extends JedisPubSub {

		@Override
		public void onPMessage(String pattern, String channel, String message) {

		}

		@Override
		public void onSubscribe(String channel, int subscribedChannels) {

		}

		@Override
		public void onUnsubscribe(String channel, int subscribedChannels) {

		}

		@Override
		public void onPUnsubscribe(String pattern, int subscribedChannels) {

		}

		@Override
		public void onPSubscribe(String pattern, int subscribedChannels) {

		}

	}

}
