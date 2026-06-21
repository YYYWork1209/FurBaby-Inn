package com.furbaby.furbaby.cache;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Set;
import java.util.function.Supplier;

/**
 * Cache-Aside 模式工具类。
 * 读：先查缓存，命中直接返回；未命中则查数据库，回填缓存后返回。
 * 写：先更新数据库，再删除缓存（延迟双删可根据业务需要添加）。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CacheHelper {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Cache-Aside 读取。
     *
     * @param key       缓存键
     * @param type      返回值类型
     * @param dbFetcher 数据库查询回调（缓存未命中时执行）
     * @param ttl       过期时间
     * @param <T>       返回值泛型
     * @return 缓存或数据库中的数据，数据库返回 null 时不缓存（防止缓存穿透）
     */
    @SuppressWarnings("unchecked")
    public <T> T getOrFetch(String key, Class<T> type, Supplier<T> dbFetcher, Duration ttl) {
        try {
            Object cached = redisTemplate.opsForValue().get(key);
            if (cached != null) {
                log.debug("Cache hit: {}", key);
                return (T) cached;
            }
        } catch (Exception e) {
            log.warn("Redis read failed for key: {}, fallback to DB. {}", key, e.getMessage());
        }

        T data = dbFetcher.get();
        if (data != null) {
            try {
                redisTemplate.opsForValue().set(key, data, ttl);
                log.debug("Cache set: {} (ttl={})", key, ttl);
            } catch (Exception e) {
                log.warn("Redis write failed for key: {}. {}", key, e.getMessage());
            }
        }
        return data;
    }

    /**
     * 删除单个缓存键。
     */
    public void evict(String key) {
        try {
            redisTemplate.delete(key);
            log.debug("Cache evicted: {}", key);
        } catch (Exception e) {
            log.warn("Redis evict failed for key: {}. {}", key, e.getMessage());
        }
    }

    /**
     * 按通配符模式批量删除缓存键。
     * 例如 evictPattern("shop:list:*") 删除所有商家列表缓存。
     * 注意：生产环境中 KEYS 命令在大数据量下可能阻塞 Redis，若键数量极大应改用 SCAN 迭代。
     */
    public void evictPattern(String pattern) {
        try {
            Set<String> keys = redisTemplate.keys(pattern);
            if (keys != null && !keys.isEmpty()) {
                redisTemplate.delete(keys);
                log.debug("Cache evicted pattern: {} ({} keys)", pattern, keys.size());
            }
        } catch (Exception e) {
            log.warn("Redis evictPattern failed for pattern: {}. {}", pattern, e.getMessage());
        }
    }
}
