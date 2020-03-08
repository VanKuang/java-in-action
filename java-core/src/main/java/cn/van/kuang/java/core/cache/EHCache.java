package cn.van.kuang.java.core.cache;

import org.ehcache.Cache;
import org.ehcache.PersistentCacheManager;
import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.config.builders.UserManagedCacheBuilder;
import org.ehcache.config.units.EntryUnit;
import org.ehcache.config.units.MemoryUnit;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class EHCache {

    public void cacheByEHCache(int sum) {
        final UserManagedCache<Long, String> userManagedCache = UserManagedCacheBuilder.newUserManagedCacheBuilder(Long.class, String.class).build(true);

        userManagedCache.put(1L, "KB");
        userManagedCache.put(2L, "MJ");
        userManagedCache.put(3L, "LBJ");
        userManagedCache.put(4L, "DW");
        userManagedCache.put(5L, "KD");
        userManagedCache.put(6L, "SC");

        int count = 0;

        long start = System.currentTimeMillis();
        while (count < sum) {
            count++;
            userManagedCache.put(1L, "Kobe");
            userManagedCache.get(1L);
        }
        System.out.println(System.currentTimeMillis() - start);

        userManagedCache.put(1L, "Kobe");
        System.out.println(userManagedCache.get(1L));
    }

    public void cacheByHashMap(int sum) {
        Map<Long, String> map = new HashMap<>();
        map.put(1L, "KB");
        map.put(2L, "MJ");
        map.put(3L, "LBJ");
        map.put(4L, "DW");
        map.put(5L, "KD");
        map.put(6L, "SC");

        int count = 0;
        long start = System.currentTimeMillis();
        while (count < sum) {
            count++;
            map.put(1L, "Kobe");
            map.get(1L);
        }
        System.out.println(System.currentTimeMillis() - start);

        System.out.println(map.get(1L));
    }

    public void createPersistentCache(final String cacheName) {
        final PersistentCacheManager persistentCacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .with(CacheManagerBuilder.persistence(new File("/Users/VanKuang/tmp", "ehcache")))
                .withCache(cacheName,
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(
                                Long.class,
                                String.class,
                                ResourcePoolsBuilder.newResourcePoolsBuilder()
                                        .heap(10, EntryUnit.ENTRIES)
                                        .offheap(1, MemoryUnit.MB)
                                        .disk(20, MemoryUnit.MB, true)
                        )
                )
                .build(true);

        final Cache<Long, String> cache = persistentCacheManager.getCache(cacheName, Long.class, String.class);

        System.out.println(cache.get(1L));

        cache.put(1L, "stillAvailableAfterRestart");

        persistentCacheManager.close();
    }

    public static void main(String[] args) {
        int sum = 1000 * 1000;

        EHCache EHCache = new EHCache();
        EHCache.cacheByEHCache(sum);
        EHCache.cacheByHashMap(sum);
        EHCache.createPersistentCache("playerCache");
    }

}
