package org.naike.dtcache.config;

import org.naike.dtcache.listener.CacheItemListener;
import org.naike.dtcache.strategy.DefaultExpireStrategy;
import org.naike.dtcache.strategy.ExpireStrategy;

/**
 * cache config
 * 
 * @author shixiangwen03@gmail.com
 * 
 */
public class CacheConfig {

	/**
	 * the expire strategy, if not config, it will use the @see
	 * DefaultExpireStrategy
	 */
	private ExpireStrategy epireStrategy = null;

	private CacheItemListener cacheItemListener = null;

	private int hashSize;

	private CacheConfig(Builder builder) {
		this.epireStrategy = builder.getEpireStrategy();
		this.hashSize = builder.getHashSize();
		this.cacheItemListener = builder.getCacheItemListener();
		if (epireStrategy == null) {
			epireStrategy = new DefaultExpireStrategy();
		}
	}

	public ExpireStrategy getEpireStrategy() {
		return epireStrategy;
	}

	public int getHashSize() {
		return hashSize;
	}

	public CacheItemListener getCacheItemListener() {
		return cacheItemListener;
	}

	public static class Builder {

		private ExpireStrategy epireStrategy;

		private CacheItemListener cacheItemListener;

		private int hashSize = 128;

		public Builder epireStrategy(ExpireStrategy epireStrategy) {
			this.epireStrategy = epireStrategy;
			return this;
		}

		public Builder hashSize(int hashSize) {
			this.hashSize = hashSize;
			return this;
		}

		public CacheConfig build() {
			return new CacheConfig(this);
		}

		public ExpireStrategy getEpireStrategy() {
			return epireStrategy;
		}

		public int getHashSize() {
			return hashSize;
		}

		public CacheItemListener getCacheItemListener() {
			return cacheItemListener;
		}

		public Builder cacheItemListener(CacheItemListener cacheItemListener) {
			this.cacheItemListener = cacheItemListener;
			return this;
		}

	}

}
