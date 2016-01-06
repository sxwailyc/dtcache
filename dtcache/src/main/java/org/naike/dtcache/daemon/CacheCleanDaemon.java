package org.naike.dtcache.daemon;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.naike.dtcache.core.CacheItem;

public abstract class CacheCleanDaemon extends Thread {

	protected static Log logger = LogFactory.getLog(CacheCleanDaemon.class);

	/**
	 * memory use age
	 * 
	 * @return
	 */
	private void memoryUseage() {

		long totalMemory = Runtime.getRuntime().totalMemory();
		long freeMemory = Runtime.getRuntime().freeMemory();
		long maxMemory = Runtime.getRuntime().maxMemory();

		String msg = MessageFormat.format("Max Memory:{0,}MB;Total Memory:{1}MB;Free Memory:{2}MB", maxMemory / 1024 / 1024, totalMemory / 1024 / 1024, freeMemory / 1024 / 1024);
		logger.debug(msg);
	}

	/**
	 * clean expired cache item
	 */
	private void cleanExpired() {

		List<Map<String, CacheItem>> cacheList = getCache();

		int total = 0;
		int clean = 0;
		for (Map<String, CacheItem> m : cacheList) {
			if (m == null) {
				continue;
			}
			total += m.size();
			List<String> expiredKeys = new ArrayList<String>();
			for (Entry<String, CacheItem> entry : m.entrySet()) {
				CacheItem cacheItem = entry.getValue();
				if (isExpire(cacheItem)) {
					expiredKeys.add(entry.getKey());
				}
			}
			for (String key : expiredKeys) {
				clean += 1;
				m.remove(key);
			}
		}

		String msg = MessageFormat.format("Total Cache Item:{0};Expired Item: {1}, New Total Item: {2}", total, clean, total - clean);
		logger.debug(msg);

	}

	public abstract List<Map<String, CacheItem>> getCache();

	public abstract boolean isExpire(CacheItem cacheItem);

	private void work() {

		memoryUseage();
		cleanExpired();
		memoryUseage();

	}

	@Override
	public void run() {

		while (true) {

			try {
				work();
				Thread.sleep(1000 * 30);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
				try {
					Thread.sleep(1000 * 10);
				} catch (InterruptedException ie) {
					logger.error(ie.getMessage(), ie);
				}
			}
		}

	}

}
