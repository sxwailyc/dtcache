#dtcache, a  local cache support data sync for distributed application


----------------

- [dtcache, a  local cache support data sync for distributed application](#dtcache-a-local-cache-support-data-sync-for-distributed-application)
 - [config](#config)
 - [example](#example)
   - [write](#write)
   - [read](#read)
   - [delete](#delete)

##config the cache
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

##example

###write 

```java
Cache cache = new Cache();
User user = new User();
user.setUserId("userId");
user.setUsername("username");
cache.put(user.getUserId(), user);
```
###read

```java
Cache cache = new Cache();
User user = cache.get("userId", User.class);
```

###delete

```java
Cache cache = new Cache();
cache.delete(User.class, "userId);
```
