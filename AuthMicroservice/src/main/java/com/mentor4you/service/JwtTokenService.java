package com.mentor4you.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import org.springframework.stereotype.Service;

@Service
public class JwtTokenService {

  private final HashMap<Integer, String> usersTokens;

  private final Key key;
  private final SignatureAlgorithm signatureAlgorithm;
  private static final Long EXPIRATION_TIME_MILLIS = 60L * 60L * 1000; // 1 hour

  public JwtTokenService() {
    this.key = MacProvider.generateKey();
    this.signatureAlgorithm = SignatureAlgorithm.HS512;
    this.usersTokens = new HashMap<>();
  }

  public String createToken(Integer userId, String userEmail, String userRole) {
    Date issuedAt = new Date();
    Date expiration = new Date(issuedAt.getTime() + EXPIRATION_TIME_MILLIS);

    final Claims claims = Jwts.claims();
    claims.setSubject(userEmail);
    claims.put("id", userId);
    claims.put("role", userRole);

    final String token =
        Jwts.builder()
            .setClaims(claims)
            .setIssuedAt(issuedAt)
            .setExpiration(expiration)
            .signWith(signatureAlgorithm, key)
            .compact();
    usersTokens.put(userId, token);
    return token;
  }

  public boolean isValidToken(String token) {
    try {
      Jwts.parser().setSigningKey(key).parse(token);
      return true;
    } catch(Exception ex) {
      return false;
    }
  }

  public String getToken(Integer userId) {
    return usersTokens.get(userId);
  }

  public void deleteToken(Integer userId) {
    usersTokens.remove(userId);
  }
}
