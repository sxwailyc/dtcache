package org.naike.dtcache;

public interface Cache {

	public void delete(Class<?> cls, String id);

	public void delete(String className, String id);

	public void put(String id, Object obj);

	public <T> T get(String id, Class<T> cls);

}
