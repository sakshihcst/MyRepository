package org.cache.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.cache.impl.ThreadSafeSingletonCache;
import org.cache.interfaces.Cache;
import org.junit.Test;

public class TestCacheJUnit {

		Cache cache = ThreadSafeSingletonCache.getInstance();
		
		/* test data */
		String val1 = "one";
		String val2 = "two"; 
		
		@Before
		public void testPutMethod(){
			cache.put("key1", val1);
			cache.put("key2", val2);
			cache.put("key3", val1);
		}
        
		@Test
		public void testGetMethod(){
			assertNotNull(cache.get("key1"));
		}
		
		@Test
		public void testCacheIsNotNull() {
	        assertNotNull(cache);
	    }
		
		@Test
		public void testGetPutMethod() {
	        assertFalse(cache.get("key1").equals(cache.get("key2")));
	        assertTrue(cache.get("key1").equals(cache.get("key3")));
	        assertEquals(cache.get("key1"),cache.get("key3"));
	    }
		
		@Test
		public void testInvalidKey() {
	        assertNull(cache.get("key4"));
	    }
		
	}

