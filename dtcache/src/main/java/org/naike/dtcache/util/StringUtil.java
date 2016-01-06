package org.naike.dtcache.util;

public class StringUtil {

	public static long getHashCode(String str) {
		int h = 0;
		char val[] = str.toCharArray();
		for (int i = 0; i < val.length; i++) {
			h = 31 * h + val[i];
		}
		return Math.abs((long) h);
	}
}
