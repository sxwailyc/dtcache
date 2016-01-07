package org.naike.dtcache.listener;

import org.naike.dtcache.callback.CacheItemChangeCallback;

/**
 * 
 * @author shixiangwen03@gmail.com
 * 
 */
public interface CacheItemListener {

	/**
	 * when cache item modify, this method will be call
	 * 
	 * @param className
	 * @param id
	 */
	public void onChange(String className, String id);

	/**
	 * listen on other server's cache item modify, and call the callback
	 * interface to notify local server
	 * 
	 * @param callback
	 */
	public void listen(CacheItemChangeCallback callback);

}
