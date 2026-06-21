package com.furbaby.furbaby.lock;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Collections;
import java.util.UUID;

/**
 * Redis 分布式锁。
 *
 * 加锁：SET key value NX PX ttl，原子操作，仅当 key 不存在时写入，同时设置过期时间防止死锁。
 * 解锁：Lua 脚本校验 value 匹配后才删除，防止误删其他线程的锁。
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DistributedLock {

    private final StringRedisTemplate stringRedisTemplate;

    private static final String LOCK_PREFIX = "lock:";
    private static final long DEFAULT_RETRY_INTERVAL_MS = 50;

    /**
     * Lua 解锁脚本：仅当锁的 value 与当前持有者一致时才删除，防止误删。
     */
    private static final DefaultRedisScript<Long> UNLOCK_SCRIPT;

    static {
        UNLOCK_SCRIPT = new DefaultRedisScript<>();
        UNLOCK_SCRIPT.setScriptText(
                "if redis.call('GET', KEYS[1]) == ARGV[1] then " +
                "    return redis.call('DEL', KEYS[1]) " +
                "else " +
                "    return 0 " +
                "end");
        UNLOCK_SCRIPT.setResultType(Long.class);
    }

    /**
     * 尝试获取锁，获取失败则阻塞重试直到超时。
     *
     * @param resource 锁标识（如 "shop:schedule:3"）
     * @param ttl      锁过期时间（防止死锁）
     * @param timeout  最大等待时间
     * @return lockHolder 解锁凭证，获取失败返回 null
     */
    public String tryLock(String resource, Duration ttl, Duration timeout) {
        String key = LOCK_PREFIX + resource;
        String lockHolder = UUID.randomUUID().toString();
        long deadline = System.currentTimeMillis() + timeout.toMillis();

        while (System.currentTimeMillis() < deadline) {
            Boolean acquired = stringRedisTemplate.opsForValue()
                    .setIfAbsent(key, lockHolder, ttl);
            if (Boolean.TRUE.equals(acquired)) {
                log.debug("Lock acquired: {} holder={}", key, lockHolder.substring(0, 8));
                return lockHolder;
            }
            try {
                Thread.sleep(DEFAULT_RETRY_INTERVAL_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        log.warn("Lock timeout: {} after {}ms", key, timeout.toMillis());
        return null;
    }

    /**
     * 释放锁。仅 lockHolder 与 Redis 中 value 一致时才会删除。
     *
     * @param resource   锁标识
     * @param lockHolder 解锁凭证（由 tryLock 返回）
     */
    public void unlock(String resource, String lockHolder) {
        if (lockHolder == null) {
            return;
        }
        String key = LOCK_PREFIX + resource;
        try {
            Long result = stringRedisTemplate.execute(
                    UNLOCK_SCRIPT,
                    Collections.singletonList(key),
                    lockHolder);
            if (Long.valueOf(1).equals(result)) {
                log.debug("Lock released: {}", key);
            } else {
                log.debug("Lock already expired or held by another: {}", key);
            }
        } catch (Exception e) {
            log.error("Unlock failed: {}. {}", key, e.getMessage());
        }
    }
}
