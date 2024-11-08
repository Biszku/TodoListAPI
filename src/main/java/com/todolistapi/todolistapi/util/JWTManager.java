package com.todolistapi.todolistapi.util;

import com.todolistapi.todolistapi.entity.AuthToken;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

public class JWTManager {

    private static final SecretKey key;

    static {
        String base64Key = System.getenv("JWT_SECRET");
        if (base64Key == null || base64Key.isEmpty()) {
            throw new IllegalStateException("JWT_SECRET environment variable is not set");
        }
        byte[] keyBytes = Base64.getDecoder().decode(base64Key);
        key = Keys.hmacShaKeyFor(keyBytes);
    }
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24;

    public static AuthToken generateJWT(String content) {
        String jws = Jwts.builder()
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .subject(content)
                .signWith(key)
                .compact();
        return new AuthToken(jws);
    }

    public static String readJWT(String jws) throws MalformedJwtException {
            return Jwts.parser().verifyWith(key).build().parseSignedClaims(jws)
                    .getPayload()
                    .get("sub")
                    .toString();
    }
}
