package com.sajan.todo.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import com.sajan.todo.exception.UserForBiddenException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Jwt {
	private static Claims USER_INFO = null;
	public static String SECRET_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
	static {
		//you can read or load your private key from server and assign it
		//Jwt.SECRET_KEY = "your private key from server";
	}

	public static String createJwt (String id, String issuer, String subject, long ttlMillis) {
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET_KEY);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());
        
        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signingKey, signatureAlgorithm);
       
      //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
	}

	public static void decodeJWT(String jwt) {
		if (jwt == "") 
			throw new UserForBiddenException("Token is required");
		
        //This line will throw an exception if it is not a signed JWS (as expected)
		try {
			 Claims claims = Jwts.parser()
		                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
		                .parseClaimsJws(jwt).getBody();
			 
			 Jwt.USER_INFO = claims;
		}catch(JwtException ex) {
			throw new UserForBiddenException("Token is not valid");
		}
       
    }
	
	public static Claims getAuthenticatedUser() {
		return Jwt.USER_INFO;
	}
}
