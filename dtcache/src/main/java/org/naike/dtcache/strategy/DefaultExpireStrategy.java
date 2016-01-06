package org.naike.dtcache.strategy;

import org.naike.dtcache.core.CacheItem;

/**
 * default expire strategy implement
 * 
 * @author dw_shixiangwen
 *
 */
public class DefaultExpireStrategy implements ExpireStrategy {

	/**
	 * if the cache item is expired
	 * 
	 * @param cacheItem
	 * @return
	 */
	public boolean isExpire(CacheItem cacheItem) {
		long now = System.currentTimeMillis();
		int read = cacheItem.getRead();
		long expiredPeriod = (read > 60 ? 60 : read) * 1000 * 60 + 1000 * 60 * 5;
		if (now - cacheItem.getCacheTime() > expiredPeriod) {
			return true;
		}
		return false;
	}

}
