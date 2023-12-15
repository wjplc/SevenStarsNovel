package com.lyn.novel.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtils {

    // 生成 JWT
    public static String createToken(String subject, String secretKey, long ttlMillis) {
        // 指定签名的时候使用的签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        //生成jet过期时间
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date expiration = new Date(expMillis);
        //设置jwt的body
        return Jwts.builder()
                .setSubject(subject)
                .setExpiration(expiration)
                .signWith(signatureAlgorithm, secretKey.getBytes(StandardCharsets.UTF_8))
                .setExpiration(expiration)
                .compact();
    }

    // 解析 JWT
    public static Claims parseToken(String secret, String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secret.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
        return claims;
    }

//    // 验证 JWT 是否过期
//    public static boolean isTokenExpired(String token) {
//        Date expiration = Jwts.parserBuilder()
//                .setSigningKey(SECRET_KEY)
//                .build()
//                .parseClaimsJws(token)
//                .getBody()
//                .getExpiration();
//
//        return expiration.before(new Date());
//    }
//
}
