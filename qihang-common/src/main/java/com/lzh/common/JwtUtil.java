package com.lzh.common;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;
@Component
public class JwtUtil {

    // 秘钥
    @Value("${jwt.SECRET_KEY}")
    private  String SECRET_KEY;
    // 令牌有效期（12小时）
    @Value("${jwt.EXPIRATION_TIME}")
    private  long EXPIRATION_TIME;

    /**
     * 生成JWT令牌
     *
     * @param claims 自定义声明信息
     * @return 生成的JWT令牌
     */
    public String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY.getBytes())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME*24))
                .compact();
    }

    /**
     * 解析JWT令牌
     *
     * @param token JWT令牌
     * @return 解析后的Claims对象
     */
    public Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }
}
