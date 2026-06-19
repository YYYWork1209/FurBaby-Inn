package com.furbaby.furbaby.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 * 用于生成JWT、验证JWT、解析JWT
 */
@Component
public class JWTUtils {

    // 1. 从 application.yml 中读取配置（安全密钥和过期时间）
    @Value("${jwt.secret}")
    private String secretKeyString; // 例如: "mySecretKeyForJwtGeneration2026!@#$"

    @Value("${jwt.expiration}")
    private Long expiration; // 例如: 86400000 (24小时，单位毫秒)

    // 2. 将字符串密钥转换为安全的 SecretKey 对象（因为 JWT 要求特定格式）

    /**
     * 获取安全的密钥对象
     * 用于 JWT 签名和验证
     * @return SecretKey 对象，用于 JWT 签名和验证
     */
    private SecretKey getSecretKey() {
        // 将字符串密钥转换为安全的 SecretKey 对象
        // 注意：这里使用 HMAC-SHA256 算法，根据需要可以调整为其他算法（如 AES）
        return Keys.hmacShaKeyFor(secretKeyString.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 生成 JWT Token（带默认过期时间）
     * @param userId 用户ID（或其他唯一标识）
     * @return JWT 字符串
     */
    public String generateToken(String userId) {
        // 可选：将额外的用户信息放入 Claims（比如用户名、角色）
        Map<String, Object> claims = new HashMap<>();
//        claims.put("userId", userId); //subject即可标明用户ID，claims可放置其他信息，不能是敏感信息

        // 如果你还想放个昵称，可以 claims.put("nickname", "张三");

        return Jwts.builder()
                .claims(claims)                // 放入私有声明
                .subject(userId)               // 标明Jwt令牌是属于谁的（一般是用户ID），
                .issuedAt(new Date())          // 签发时间
                .expiration(new Date(System.currentTimeMillis() + expiration)) // 过期时间
                .signWith(getSecretKey())      // 签名（加密），使用 HMAC-SHA256 算法（根据需要可以调整为其他算法，如 AES）
                .compact();                    // 压缩成字符串
    }

    /**
     * 解析 JWT Token，获取其中的 Claims（载荷）
     * @param token JWT 字符串
     * @return Claims 对象（包含 userId 等所有信息）
     * @throws JwtException 如果 Token 过期、格式错误、签名失败等，会抛出此异常
     */
    public Claims parseToken(String token) throws JwtException {
        return Jwts.parser()
                .verifyWith(getSecretKey())    // 使用密钥验证签名
                .build()    // 构建解析器
                .parseSignedClaims(token)      // 解析并验证签名
                .getPayload();                 // 获取载荷体
    }

    /**
     * 从 Token 中提取用户 ID（工具方法，方便调用）
     */
    public String getUserIdFromToken(String token) {
        Claims claims = parseToken(token);
//        return claims.get("userId", String.class); // 从 Claims 中取 userId
        // 或者直接取主题：return claims.getSubject();
        return claims.getSubject();
    }

    /**
     * 验证 Token 是否有效（是否过期、签名是否正确）
     * 实际上 parseToken 如果失败就会抛异常，可以用这个方法包装一下
     */
    public boolean validateToken(String token) {
        try {
            parseToken(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // 签名错误、过期、空 token 等都会进这里
            return false;
        }
    }
}
