package com.jetchiu.supercanteen.commonservice;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTServiceImp implements JWTService{
    @Value("${keystring}")
    String key;
    @Override
    public String GetClaims(String token, String ClaimKey) {

        token=token.substring(7);
        return (String) Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody().get(ClaimKey);
    }

    @Override
    public String CreateTokens(String account) {
        Claims claims=Jwts.claims();
        claims.put("account",account);
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS256, key).compact();
    }
}
