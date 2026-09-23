package foxder.app.demo.service;

import foxder.app.demo.model.UserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@FieldDefaults(level = AccessLevel.PRIVATE)
public class JwtService {
    @Value("${spring.application.secret_key}")
    String secretKey;

    public String generateToken(UserPrincipal userPrincipal) {
        Map<String, Object> claims = new HashMap<>();

        long now = System.currentTimeMillis();
        long thirtyHoursMs = 1000L * 60 * 60 * 30; // ms * sec * min * hr

        /**
         * 1000L   → 1000 ms in a second
         * × 60    → 60 seconds in a minute  →  60,000 ms in a minute
         * × 60    → 60 minutes in an hour   →  3,600,000 ms in an hour
         * × 30    → 30 hours                →  108,000,000 ms total
         */

        return Jwts.builder()
                .claims(claims)
                .subject(userPrincipal.getUsername())
                .issuedAt(new Date(now))
                .expiration(new Date(now + thirtyHoursMs))
                .signWith(getKey())
                .compact();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUserEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String email = extractUserEmail(token);
        return (email.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
