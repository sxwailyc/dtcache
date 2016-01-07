package org.naike.dtcache;

/**
 * 
 * @author shixiangwen03@gmail.com
 * 
 */
public interface Cache {

	/**
	 * delete one cache item from cache
	 * 
	 * @param cls
	 * @param id
	 */
	public void delete(Class<?> cls, String id);

	/**
	 * delete one cache item from cache
	 * 
	 * @param className
	 * @param id
	 */
	public void delete(String className, String id);

	/**
	 * push one cache item to cache
	 * 
	 * @param id
	 * @param obj
	 */
	public void put(String id, Object obj);

	/**
	 * get one cache item from cache
	 * 
	 * @param id
	 * @param cls
	 * @return
	 */
	public <T> T get(String id, Class<T> cls);

}
