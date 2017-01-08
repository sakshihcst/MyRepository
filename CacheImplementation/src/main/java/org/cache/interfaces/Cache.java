package org.cache.interfaces;

public interface Cache<T> {
	public void put(Object key,T value);
	public Object get(Object key);
}


