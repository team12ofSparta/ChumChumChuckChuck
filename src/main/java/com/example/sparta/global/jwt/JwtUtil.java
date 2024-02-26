package com.example.sparta.global.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Base64;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j // try/catch 부분에서 오류 로그 찍을때 씀
public class JwtUtil {
    // JwtUtil을 생성하는 유틸 모아두기


    // Header Key 값
    public final String AUTHORIZATION_HEADER = "Authoriztion";

    // 토큰 식별자 (값이 Bearer  으로 시작해야함)
    public final String BEARER_PREFIX = "Bearer ";


    // 프로퍼티스에 지정한 secret 키
    @Value("${jwt.secret.key}")
    private String secretKey;

    // 키 만들때 쓸 암호화 알고리즘 ( HS256 사용 )
    private final SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;


    // 암호화된 키 값
    private Key key;


    // 키 값을 만들어서 주입
    @PostConstruct // 딱한번만 실행되면 될때 사용
    public void init(){
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }



    // 받아온 토큰이 정상인지 확인하고 토큰의 바디값을 리턴함
    public String resolveToken(HttpServletRequest httpServletRequest){
        // 받아온 Request의 Header 값이 AUTHORIZATION_HEADER 와 같으면
        // Request의 body 값을 bearerToken 에 저장
        String bearerToken = httpServletRequest.getHeader(AUTHORIZATION_HEADER);

        // 만약 받아온 바디값이 있고 && BEARER_PREFIX 으로 시작하면
        if(StringUtils.hasText(bearerToken)&& bearerToken.startsWith(BEARER_PREFIX)){
            // 맨앞 7글자를 지움 (BEARER_PREFIX 만큼이 딱 7글자)
            return bearerToken.substring(7);
        }
        // 받아온 바디값이 이상하면 null 반환
        return null;
    }


    // 토큰 유효성 체크
    public boolean validateToken(String token){
        try{
            // Jwts.parseBuilder메서드를 이용해서 JwtParseBuilder인스턴스를 생성
            // JWS 서명 검증을 위해 SecretKey 지정
            // 안전한 JwtPaser를 리턴하기 위해서 JwtPaserBuilder의 build() 메서드 호출
            // 원본 JWS 를 생성하는 jws 를 가지고 parseClaimsJws() 메서드 호출
            // 파싱, 서명검증 오류 경우 try/catch 구문으로 감쌈
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            //토큰이 정상으로 유효하면 true 반환
            return true;

        // 각종 문제로 인해서 토큰이 유효하지 않으면 어떤 이유로 유효하지 않은지 log 를찍고 catch 후 false 리턴
        } catch (SecurityException | MalformedJwtException e) {
            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }


    // 유저의 정보를 토큰에서 받아옴
    public Claims getUserInfoFromToken(String token){
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }



    // 토큰을 새로 만들때
    public String createToken (String username){
        Date date = new Date();

        // 토큰 만료시간 1시간
        long TOKEN_TIME = 60 * 60 * 1000;


        return BEARER_PREFIX + Jwts.builder()
            // 이후 JWT로 인증할 식별자를 username 으로 지정
            .setSubject(username)
            // 현재 시간으로 부터 TOKEN_TIME (1시간) 만큼의 만료시간 지정
            .setExpiration(new Date(date.getTime()+TOKEN_TIME))
            // JWT를 발급한 시간
            .setIssuedAt(date)
            // 개인 키를 가지고 HS256 암호화 알고리즘으로 Header 와 Signature 생성
            .signWith(key,signatureAlgorithm)
            .compact();
    }



}
