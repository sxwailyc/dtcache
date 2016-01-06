package org.naike.dtcache.config;

import org.naike.dtcache.listener.CacheItemListener;
import org.naike.dtcache.strategy.DefaultExpireStrategy;
import org.naike.dtcache.strategy.ExpireStrategy;

public class CacheConfig {

	private ExpireStrategy epireStrategy = null;

	private CacheItemListener cacheItemListener = null;

	private int poolSize;

	private CacheConfig(Builder builder) {
		this.epireStrategy = builder.getEpireStrategy();
		this.poolSize = builder.getPoolSize();
		this.cacheItemListener = builder.getCacheItemListener();
		if (epireStrategy == null) {
			epireStrategy = new DefaultExpireStrategy();
		}
	}

	public ExpireStrategy getEpireStrategy() {
		return epireStrategy;
	}

	public int getPoolSize() {
		return poolSize;
	}

	public CacheItemListener getCacheItemListener() {
		return cacheItemListener;
	}

	public static class Builder {

		private ExpireStrategy epireStrategy;

		private CacheItemListener cacheItemListener;

		private int poolSize = 128;

		public Builder epireStrategy(ExpireStrategy epireStrategy) {
			this.epireStrategy = epireStrategy;
			return this;
		}

		public Builder poolSize(int poolSize) {
			this.poolSize = poolSize;
			return this;
		}

		public CacheConfig build() {
			return new CacheConfig(this);
		}

		public ExpireStrategy getEpireStrategy() {
			return epireStrategy;
		}

		public int getPoolSize() {
			return poolSize;
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
