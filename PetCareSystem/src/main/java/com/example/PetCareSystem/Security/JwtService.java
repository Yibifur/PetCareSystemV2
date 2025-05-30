package com.example.PetCareSystem.Security;

import com.example.PetCareSystem.Entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class JwtService {

    private static final String SECRET_KEY = "65df662f683adaadac9da410ed99c885ab709835d9311774cd2fd82cca5bafdc";

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // Token'dan email bilgisini çıkartır
    public String extractUsername(String token) {
        String usernameClaim = extractAllClaims(token).get("username", String.class); // Username claim'ini çıkar
        return usernameClaim;
    }

    public List<GrantedAuthority> extractRoles(String token) {
        Claims claims = extractAllClaims(token);
        List<?> roles = claims.get("Authorities", List.class); // Listeyi genel türde okuyun

        if (roles == null) {
            return List.of(); // Roller yoksa boş bir liste döndür
        }

        // LinkedHashMap'i SimpleGrantedAuthority'ye dönüştür
        return roles.stream()
                .map(role -> {
                    if (role instanceof LinkedHashMap) {
                        return new SimpleGrantedAuthority((String) ((LinkedHashMap<?, ?>) role).get("authority"));
                    }
                    return new SimpleGrantedAuthority(role.toString()); // Eğer String formatındaysa direkt al
                })
                .collect(Collectors.toList());
    }




    // Token oluşturma (email'i subject olarak ekler)
    public String generateToken(User userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        claims.put("userID", userDetails.getId());
        claims.put("Authorities", userDetails.getAuthorities().stream()
                .map(authority -> Map.of("authority", authority.getAuthority())) // LinkedHashMap oluştur
                .toList());
        return generateToken(claims, userDetails);
    }



    public String generateToken(Map<String, Object> extraClaims, User userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getEmail()) // Email'i subject olarak kullanıyoruz
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + (SecurityConstants.SESSIONANDJWT_LIFETIME)*1000)) // 15 dakika geçerli
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // Token doğrulama (email ile kontrol)
    public boolean isValidToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public int extractUserId(String token) {
        // Token içerisindeki "userID" claim'ini çıkar
        Claims claims = extractAllClaims(token);
        return claims.get("userID", Integer.class); // "userID" claim'ini al ve Integer olarak döndür
    }
}

