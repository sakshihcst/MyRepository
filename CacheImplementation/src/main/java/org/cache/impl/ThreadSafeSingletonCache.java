package org.cache.impl;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.cache.interfaces.Cache;

public class ThreadSafeSingletonCache<T> implements Cache<T>, Cloneable {
	  
    /* To ensure type safety Object class is passed as a parameter */
    private Map<Object, T> map;  
  
    /* Private constructor to restrict instantiation from outside the class */
    private ThreadSafeSingletonCache() { 
		// ConcurrentHashMap to ensure thread safety
        map = new ConcurrentHashMap<Object, T>();  
    }
	
	/* private static variable to ensure this is the only instance of the class */
    private static ThreadSafeSingletonCache singletonInstance = null;  
      
    /* public static method to provide a global access point to get the instance of the singleton class */ 
    public static ThreadSafeSingletonCache getInstance()   
    {  
		/* null check to ensure that instance is not already present in the JVM */
        if(singletonInstance == null){
			singletonInstance = new ThreadSafeSingletonCache();  
		}
        return singletonInstance;
    }  
    
    /* To put objects in the cache */
    public void put(Object key, T value)   
    {  
        map.put(key, value);  
    }  
  
    /* To get objects from the cache */
    public Object get(Object key)   
    {  
        return map.get(key);  
    } 
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        /* Override clone method to avoid object cloning */
        throw new CloneNotSupportedException();
    }
}   
  
