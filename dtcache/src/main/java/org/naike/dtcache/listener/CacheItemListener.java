package org.naike.dtcache.listener;

import org.naike.dtcache.callback.CacheItemChangeCallback;

public interface CacheItemListener {

	public void onChange(String className, String id);

	public void listen(CacheItemChangeCallback callback);

}
