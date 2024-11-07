package com.todolistapi.todolistapi.util;

import com.todolistapi.todolistapi.entity.AuthToken;
import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public class JWTManager {

    private static final SecretKey key = Jwts.SIG.HS256.key().build();

    public static AuthToken generateJWT(String content) {
        String jws = Jwts.builder().subject(content).signWith(key).compact();
        return new AuthToken(jws);
    }

    public static Object validateJWT(String jws) {
        try {
            var jwt = Jwts.parser().verifyWith(key).build().parseSignedClaims(jws);
            return jwt.getPayload().get("sub");
        } catch (Exception e) {
            return null;
        }
    }
}
