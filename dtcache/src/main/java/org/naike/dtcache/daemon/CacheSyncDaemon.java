package org.naike.dtcache.daemon;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public abstract class CacheSyncDaemon extends Thread {

	protected static Log logger = LogFactory.getLog(CacheSyncDaemon.class);

	public abstract void work();

	private boolean started = false;

	@Override
	public void run() {

		while (true) {
			try {
				if (!started) {
					work();
					started = true;
				}
				Thread.sleep(1000 * 60);
			} catch (Exception e) {
				started = false;
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
