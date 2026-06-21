package com.furbaby.furbaby.security;

import com.furbaby.furbaby.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.HexFormat;

/**
 * JWT Token 黑名单。
 *
 * JWT 无状态，签发后无法主动收回。将需作废的 Token 存入 Redis 黑名单，
 * TTL 设为 Token 剩余有效期，过期自动清理，不占持久内存。
 *
 * 入黑名单场景：
 * - 用户主动登出
 * - 修改密码后强制旧 Token 失效
 * - 管理员封禁用户
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TokenBlacklist {

    private final StringRedisTemplate stringRedisTemplate;
    private final JWTUtils jwtUtils;

    private static final String BLACKLIST_PREFIX = "blacklist:token:";

    /**
     * 将 Token 加入黑名单。
     * TTL 自动设为 Token 剩余有效期，Token 过期后黑名单记录自动清除。
     */
    public void blacklist(String token) {
        try {
            Claims claims = jwtUtils.parseToken(token);
            long remainingMs = claims.getExpiration().getTime() - System.currentTimeMillis();
            if (remainingMs <= 0) {
                return;
            }
            String key = BLACKLIST_PREFIX + hashToken(token);
            stringRedisTemplate.opsForValue().set(key, "1", Duration.ofMillis(remainingMs));
            log.debug("Token blacklisted, expires in {}s", remainingMs / 1000);
        } catch (Exception e) {
            log.warn("Token blacklist failed: {}", e.getMessage());
        }
    }

    /**
     * 检查 Token 是否在黑名单中。
     */
    public boolean isBlacklisted(String token) {
        try {
            String key = BLACKLIST_PREFIX + hashToken(token);
            return Boolean.TRUE.equals(stringRedisTemplate.hasKey(key));
        } catch (Exception e) {
            log.warn("Token blacklist check failed, allowing token: {}", e.getMessage());
            return false; // Redis 不可用时放过，优先保证可用性
        }
    }

    /**
     * SHA-256 哈希 Token，取前 32 位十六进制作为缓存键。
     * 避免明文 JWT 直接存储在 Redis 中。
     */
    private String hashToken(String token) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(token.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hash).substring(0, 32);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 algorithm not available", e);
        }
    }
}
