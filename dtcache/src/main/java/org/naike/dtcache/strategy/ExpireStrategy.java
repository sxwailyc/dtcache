package org.naike.dtcache.strategy;

import org.naike.dtcache.core.CacheItem;

public interface ExpireStrategy {

	boolean isExpire(CacheItem cacheItem);
}
