package blade.test;

import org.junit.Assert;
import org.junit.Test;

import blade.cache.Cache;
import blade.cache.CacheManager;

public class CacheTest {
	
	@Test
	public void testLRU2(){
		CacheManager cm = CacheManager.getInstance();
		
		Cache<String, Object> cache = cm.newLRUCache();
		cache.set("name:1", "jack");
		cache.set("name:2", "jack2");
		
		Assert.assertEquals("jack", cache.get("name:1"));
		Assert.assertEquals("jack2", cache.get("name:2"));
	}
	
	@Test
	public void testAutoClean(){
		CacheManager cm = CacheManager.getInstance();
		cm.setCleanInterval(1000);
		
		Cache<String, Object> cache = cm.newLRUCache();
		cache.set("name:1", "jack");
		cache.set("name:2", "jack2");
		
		Assert.assertEquals("jack2", cache.get("name:2"));
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(null, cache.get("name:2"));
	}
	
	@Test
	public void testHashCache(){
		CacheManager cm = CacheManager.getInstance();
		
		Cache<String, Object> cache = cm.newLRUCache();
		cache.hset("user:list", "a1", "123");
		cache.hset("user:list", "a2", "456");
		cache.hset("user:list", "a3", "789");
		
		Assert.assertEquals("123", cache.hget("user:list", "a1"));
		Assert.assertEquals("456", cache.hget("user:list", "a2"));
		Assert.assertEquals("789", cache.hget("user:list", "a3"));
	}
	
}
