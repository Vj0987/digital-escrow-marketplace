package org.cdac.digital_escrow_marketplace.users.security;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {
	@Value("${jwt.secret}")
	private String SECRET ;

	private Key getSigningKey() {
		byte[] keyBytes = SECRET.getBytes();
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET));
	}

	public boolean validToken(String token, String email) {
		final String tokenEmail = extractEmail(token);
		return (tokenEmail.equals(email) && !isTokenExpired(token));
	}

	public String generateToken(String email) {
		Date now = new Date();
		Date expiry = new Date(now.getTime() + 3600000);
		Key key = getSigningKey();
		return Jwts.builder().subject(email).issuedAt(now).expiration(expiry).signWith(key).compact();
	}

	public String extractEmail(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = Jwts.parser().verifyWith((SecretKey) getSigningKey()).build().parseSignedClaims(token)
				.getPayload();
		return claimResolver.apply(claims);
	}

	private boolean isTokenExpired(String token) {
		final Date expiration = extractExpiration(token);
		return expiration.before(new Date());
	}

}
