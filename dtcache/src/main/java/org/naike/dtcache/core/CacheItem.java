package org.naike.dtcache.core;

public class CacheItem {

	private Object value;
	private long cacheTime;
	private int read;

	CacheItem(Object obj) {
		this.value = obj;
		this.cacheTime = System.currentTimeMillis();
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public long getCacheTime() {
		return cacheTime;
	}

	public void setCacheTime(long cacheTime) {
		this.cacheTime = cacheTime;
	}

	public void readInc() {
		this.read += 1;
	}

	public int getRead() {
		return read;
	}
}
