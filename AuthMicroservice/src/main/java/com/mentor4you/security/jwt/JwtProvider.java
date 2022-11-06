package com.mentor4you.security.jwt;

import com.mentor4you.exception.InvalidTokenRequestException;
import com.mentor4you.model.User;
import com.mentor4you.repository.UserRepository;
import com.mentor4you.security.jwt.cache.TokenCache;
import com.mentor4you.security.jwt.cache.event.OnUserLogoutSuccessEvent;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

    @Autowired
    private TokenCache tokenCache;

    @Autowired
    UserRepository userRepository;

    public JwtProvider(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Value("$(jwt.secret)")
    private String jwtSecret;
    private String header="Authorization";

    public String generateAuthToken(Authentication authentication) {

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        Date now = new Date();
        Date expireDate = new Date(now.getTime()+604800000);
        User user = userRepository.findUserByEmail(customUserDetails.getUsername());
        String id = Integer.toString(user.getId());
        Claims claims = Jwts.claims().setSubject(customUserDetails.getUsername());
        claims.put("role",customUserDetails.getAuthorities());
        claims.put("id",id);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            validateTokenIsNotForALoggedOutDevice(authToken);
            return true;
        } catch (MalformedJwtException | ExpiredJwtException | UnsupportedJwtException | IllegalArgumentException e) {

           return false;
        }

    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        return request.getHeader(header);
    }

    public Date getExpireDateFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getExpiration();
    }

    private void validateTokenIsNotForALoggedOutDevice(String authToken) {
        OnUserLogoutSuccessEvent previouslyLoggedOutEvent = tokenCache.getLogoutEventForToken(authToken);
        if (previouslyLoggedOutEvent != null) {
                String userEmail = previouslyLoggedOutEvent.getEmail();
                Date logoutEventDate = previouslyLoggedOutEvent.getTime();
                String errorMessage = String.format("Token corresponds to an already logged out user [%s] at [%s]. Please login again", userEmail, logoutEventDate);
                throw new InvalidTokenRequestException("JWT", authToken, errorMessage);
        }
    }

}