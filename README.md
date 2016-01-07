##dtcache  is a cache implte with local##



    	CacheItemListener cacheItemListener = new CacheItemListenerRedisImpl() {

			@Override
			public Jedis getRedis() {
				return redis.getResource();
			}

			@Override
			public void closeRedis(Jedis jedis) {
				redis.returnResource(jedis);
			}
		};

		CacheConfig config = new CacheConfig.Builder().cacheItemListener(cacheItemListener).build();
        LocalCache.setConfig(config);
