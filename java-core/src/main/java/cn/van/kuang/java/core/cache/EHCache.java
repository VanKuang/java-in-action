package cn.van.kuang.java.core.cache;

import org.ehcache.UserManagedCache;
import org.ehcache.config.builders.UserManagedCacheBuilder;

import java.util.HashMap;
import java.util.Map;

public class EHCache {

    public void cacheByEHCache(int sum) {
        UserManagedCache<Long, String> userManagedCache = UserManagedCacheBuilder.newUserManagedCacheBuilder(
                Long.class, String.class).build(true);

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

    public static void main(String[] args) {
        int sum = 1000 * 1000;

        EHCache ehCache = new EHCache();
        ehCache.cacheByEHCache(sum);
        ehCache.cacheByHashMap(sum);
    }

}
