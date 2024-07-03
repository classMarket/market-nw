package market_nw.market_nw.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.List;

@Service
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private final String secretKey;

    @Value("${jwt.expiration}")
    private final long validityInMilliseconds;

    //@RequiredArgsConstructor이 왠지모르게 안먹힘.실제 서비스에서는 환경변수로 설정해야함.
    public JwtTokenProvider(@Value("${jwt.secret}") String secretKey, @Value("${jwt.expiration}") long validityInMilliseconds) {
        this.secretKey = secretKey;
        this.validityInMilliseconds = validityInMilliseconds;
    }

    //토큰 생성용
    public String createToken(String email, String roles) {

        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", roles); //권한

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds); //유효 시간

        return Jwts.builder()
                .setClaims(claims) //권한,식별 정보등
                .setIssuedAt(now) //발급 시간
                .setExpiration(validity) //만료 시간
                .signWith(SignatureAlgorithm.HS256, secretKey) //무결성 보장용
                .compact(); //생성
    }

    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    public String getEmail(String token) {
        return getClaims(token).getSubject();
    }

    public List<String> getRoles(String token) {
        return getClaims(token).get("roles", List.class);
    }

    //유효성검증(true or false)
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    //유효성검증,페이로드 반환
    public Claims resolveToken(String token) {
        try {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            return null;
        }
    }
}
