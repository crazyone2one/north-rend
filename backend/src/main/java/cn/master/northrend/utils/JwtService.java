package cn.master.northrend.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

/**
 * @author : 11's papa
 * @since : 2026/6/29, 星期一
 **/
@Service
public class JwtService {
    @Value("${jwt.ttl}")
    private long ttl;
    @Value("${jwt.secret}")
    private String secret;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(Authentication authentication) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + ttl * 1000); // 15分钟有效期
        return Jwts.builder()
                .id(UUID.randomUUID().toString())
                .signWith(getSigningKey())
                .subject(authentication.getName())
                .issuedAt(now)
                .expiration(expiryDate)
                .compact(); // Replace with actual token generation logic
    }

    public Claims parseToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            // Token 已过期
            // 注意：JWT 过期是常规的业务判断点，不是系统错误，不要打 ERROR 级别日志
            throw new RuntimeException("Token已过期，请重新登录", e);
        } catch (MalformedJwtException e) {
            // Token 格式错误（如缺少必要的点号分隔符）
            throw new RuntimeException("Token格式错误", e);
        } catch (UnsupportedJwtException e) {
            // 不支持的 JWT 格式或算法
            throw new RuntimeException("不支持的Token类型", e);
        } catch (IllegalArgumentException e) {
            // Token 为空或字符串参数非法
            throw new RuntimeException("Token不能为空", e);
        }
    }
}
