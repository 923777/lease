package com.atguigu.lease.common.Utils;

import ch.qos.logback.classic.spi.EventArgUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.CompressionCodec;
import io.jsonwebtoken.CompressionCodecs;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {
    //设置token过期时间
    private static Long tokenExpiration = 60 * 60 * 1000L;
    private static SecretKey tokenSignKey = Keys.hmacShaKeyFor("M0PKKI6pYGVWWfDZw90a0lTpGYX1d4AQ".getBytes());


    public static String createToken(Long userId, String username) {

        String token = Jwts.builder().setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))
                .setSubject("login_token")
                .claim("userId", userId).claim("username", username)
                .signWith(tokenSignKey)
                .compressWith(CompressionCodecs.GZIP).compact();
        return token;
    }

    public static Claims parseToken(String token) {
        return Jwts.parserBuilder().setSigningKey(tokenSignKey).build().parseClaimsJws(token).getBody();


    }
}
