package org.naike.dtcache.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.naike.dtcache.Cache;
import org.naike.dtcache.callback.CacheItemChangeCallback;
import org.naike.dtcache.config.CacheConfig;
import org.naike.dtcache.daemon.CacheCleanDaemon;
import org.naike.dtcache.daemon.CacheSyncDaemon;
import org.naike.dtcache.util.StringUtil;

/**
 * a key-value object cache implement with use local jvm memory
 * 
 * @author dw_shixiangwen
 * 
 */
public class LocalCache implements Cache {

	private static List<Map<String, CacheItem>> cacheList = null;

	protected static Log logger = LogFactory.getLog(LocalCache.class);

	private static CacheConfig _config = null;

	private static Object initLock = new Object();

	private static boolean inited = false;

	private static boolean opened = false;

	public static void setConfig(CacheConfig config) {
		_config = config;
		init();
	}

	private static void init() {

		synchronized (initLock) {
			_init();
		}

	}

	private static void _init() {

		if (inited) {
			return;
		}
		inited = true;
		cacheList = new ArrayList<Map<String, CacheItem>>(_config.getPoolSize());
		for (int i = 0; i < _config.getPoolSize(); i++) {
			cacheList.add(i, null);
		}

		CacheCleanDaemon cacheCleanDaemon = new CacheCleanDaemon() {
			@Override
			public List<Map<String, CacheItem>> getCache() {
				return cacheList;
			}

			@Override
			public boolean isExpire(CacheItem cacheItem) {
				return _config.getEpireStrategy().isExpire(cacheItem);
			}
		};

		cacheCleanDaemon.start();

		if (_config.getCacheItemListener() != null) {
			CacheSyncDaemon cacheSyncDaemon = new CacheSyncDaemon() {

				@Override
				public void work() {
					_config.getCacheItemListener().listen(new CacheItemChangeCallback() {

						@Override
						public void call(String className, String id) {
							_delete(className, id);
						}
					});
				}
			};

			cacheSyncDaemon.start();
		}

		opened = true;
	}

	private boolean check() {

		if (!inited) {
			throw new RuntimeException("cache not configure yet!!!");
		}

		return opened;

	}

	private static Map<String, CacheItem> getCache(String id) {
		long index = StringUtil.getHashCode(id) % _config.getPoolSize();
		Map<String, CacheItem> cache = cacheList.get((int) index);
		if (cache == null) {
			cache = new ConcurrentHashMap<String, CacheItem>();
			cacheList.add((int) index, cache);
		}
		return cache;
	}

	/**
	 * 
	 * @param cls
	 * @param id
	 * @return
	 */
	private static String getCacheKey(Class<?> cls, String id) {
		return getCacheKey(cls.getSimpleName(), id);
	}

	/**
	 * 
	 * @param cls
	 * @param id
	 * @return
	 */
	private static String getCacheKey(String className, String id) {
		return className + ":" + id;
	}

	/**
	 * delete obj from cache
	 */
	public static void _delete(String className, String id) {
		logger.debug("delete from cache.className:" + className + ",id:" + id);
		getCache(id).remove(getCacheKey(className, id));
	}

	/**
	 * notify all the client that the record had change
	 */
	public void delete(String className, String id) {
		if (_config.getCacheItemListener() == null) {
			_delete(className, id);
		} else {
			_config.getCacheItemListener().onChange(className, id);
		}
	}

	@Override
	public void delete(Class<?> cls, String id) {
		delete(cls.getSimpleName(), id);
	}

	/**
	 * put object to cache
	 * 
	 * @param id
	 * @param obj
	 */
	public void put(String id, Object obj) {
		if (!check()) {
			return;
		}
		if (obj == null) {
			return;
		}

		logger.debug(obj.getClass().getSimpleName() + " store object with key:" + id);

		CacheItem item = new CacheItem(obj);

		getCache(id).put(getCacheKey(obj.getClass(), id), item);
	}

	/**
	 * get obj from cache
	 * 
	 * @param id
	 * @param cls
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T get(String id, Class<T> cls) {
		if (!check()) {
			return null;
		}
		CacheItem item = getCache(id).get(getCacheKey(cls, id));
		if (item != null) {
			logger.debug(cls.getSimpleName() + " cahce hit with key:" + id);
			item.readInc();
			item.setCacheTime(System.currentTimeMillis());
			return (T) item.getValue();
		} else {
			logger.debug(cls.getSimpleName() + " cahce miss with key:" + id);
			return null;
		}

	}

}
