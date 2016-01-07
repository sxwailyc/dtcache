package org.naike.dtcache.core;

/**
 * 
 * @author shixiangwen03@gmail.com
 * 
 */
public class CacheItem {

	/**
	 * the cache object
	 */
	private Object value;

	/**
	 * last read time
	 */
	private long cacheTime;

	/**
	 * read time
	 */
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
