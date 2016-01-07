#dtcache, a  local cache support data sync for distributed application


----------------
[TOC]



##1. config the cache
```java
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
```

##2. example

###2.1  write 

```java
Cache cache = new Cache();
User user = new User();
user.setUserId("userId");
user.setUsername("username");
cache.put(user.getUserId(), user);
```
###2.2 read

```java
Cache cache = new Cache();
User user = cache.get("userId", User.class);
```

###2.3 delete

```java
Cache cache = new Cache();
cache.delete(User.class, "userId);
```