package org.naike.dtcache.strategy;

import org.naike.dtcache.core.CacheItem;

/**
 * default expire strategy implement
 * 
 * @author shixiangwen03@gmail.com
 * 
 */
public class DefaultExpireStrategy implements ExpireStrategy {

	/**
	 * default cache item expire strategy, the expire time is read times *
	 * minute , max expire time is 60 minute, if the one cache item last read
	 * time is more than expire time from now, it will be mark as expire
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
