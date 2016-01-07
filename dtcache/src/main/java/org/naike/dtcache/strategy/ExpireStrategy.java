package org.naike.dtcache.strategy;

import org.naike.dtcache.core.CacheItem;

/**
 * the strategy to define one cache item whether expire
 * 
 * @author shixiangwen03@gmail.com
 * 
 */
public interface ExpireStrategy {

	/**
	 * check the cache item whether expire, if return true, the cache server
	 * will remove current cache item from cache
	 * 
	 * @param cacheItem
	 * @return
	 */
	boolean isExpire(CacheItem cacheItem);
}
