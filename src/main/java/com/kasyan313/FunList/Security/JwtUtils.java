package com.kasyan313.FunList.Security;

import com.kasyan313.FunList.Models.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtils {
    @Value(value = "${jwt.secretKey}")
    private String secretKey;

    public String createToken(Map<String, Object> claims, String  subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*48))
                .signWith(SignatureAlgorithm.HS256, secretKey).compact();
    }

    public Claims extractAllClaims(String  token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
    public Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date(System.currentTimeMillis()));
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsTFunction) {
        Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    public Integer extractId(String token) {
        String id = extractClaim(token, Claims::getSubject);
        return Integer.parseInt(id);
    }

    public String generateToken(User user) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, Integer.toString(user.getId()));
    }

    public Boolean validateToken(String token, User user) {
        int id = extractId(token);
        return (id == user.getId() && !isTokenExpired(token));
    }
}
