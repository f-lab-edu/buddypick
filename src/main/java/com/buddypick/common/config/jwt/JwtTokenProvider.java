package com.buddypick.common.config.jwt;

import com.buddypick.user.dto.MemberDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${security.jwt.secret-key}")
    private String JWT_SECRET;

    // 토큰 유효시간
    @Value("${security.jwt.access-expired-time}")
    private Long EXPIRATION_TIME;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(JWT_SECRET));
    }

    public String createAccessToken(MemberDto member, List<String> roles){
        return createJwtToken(member, roles);
    }

    public String createJwtToken(MemberDto member, List<String> roles) {
        return Jwts.builder()
                .issuer("issuer@gmail.com")
                .signWith(getSigningKey())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .claim("id", member.getId())
                .claim("email", member.getEmail())
                .claim("roles", roles)
                .claim("nickName", member.getNickName())
                .compact();
    }

    public List<String> getRoles(String jwt){
        Claims  claims = Jwts.parser().verifyWith(getSigningKey()).build().parseClaimsJws(jwt).getPayload();
        return (List<String>) claims.get("roles");
    }

    public String getEmail(String jwt){
        Claims claims = Jwts.parser().verifyWith(getSigningKey()).build().parseClaimsJws(jwt).getPayload();
        return claims.get("email").toString();
    }

    public Authentication getAuthentication(String jwt){
        List<GrantedAuthority> roles = getRoles(jwt).stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
/*
        //ROLE_ 없이 저장했을 때, hasRole()통과하는 지 테스트
       List<SimpleGrantedAuthority> roles2 = getRoles(jwt).stream()
                .map(role -> new SimpleGrantedAuthority(role))
                .collect(Collectors.toList());
 */
        return new UsernamePasswordAuthenticationToken(getEmail(jwt),null, roles);
    }

    public boolean verifyToken(String jwt) {
        try {
            Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(jwt);
            return true;
        }
        catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty.: {}", e.getMessage());
        }
        return false;
    }
}
