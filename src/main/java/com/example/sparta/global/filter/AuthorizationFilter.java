package com.example.sparta.global.filter;

import com.example.sparta.global.dto.ExceptionDto;
import com.example.sparta.global.dto.ResponseDto;
import com.example.sparta.global.impl.UserDetailsService;
import com.example.sparta.global.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class AuthorizationFilter extends OncePerRequestFilter { // OncePerRequestFilter : 요청 한번마다 필터 돌리기

    private final JwtUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final UserDetailsService userDetailsService;

    @Override
    protected  void doFilterInternal(
        HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse,
        FilterChain filterChain)
        throws ServletException, IOException{

        //토큰 받기
        String token = jwtUtil.resolveToken(httpServletRequest);

        if(Objects.nonNull(token)){ // 만약 토큰이 있으면
            if(jwtUtil.validateToken(token)){
                // 토큰에서 유저정보를 받아와서 info에 넣음
                Claims info = jwtUtil.getUserInfoFromToken(token);

                // info 에서 유저네임을 뽑아와서 저장
                String username= info.getSubject();

                SecurityContext context = SecurityContextHolder.createEmptyContext();
                // Authentication (인증된 정보) 를 담고 있는 Holder
                // SecurityContextHolder 가 가진 값을 통해 인증이 되었나 확인할 수있음.


                UserDetails userDetails = userDetailsService.getUserDetails(username);
                //userDetails 에 유저의 상세 정보를 넣음

                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                //인증 확인을 하여 아직 인증이 완료되었다는 정보를 보냄

                context.setAuthentication(authentication);
                // 해당 인증정보를 ContextHolder 에 저장

                SecurityContextHolder.setContext(context);
                // 여기까지 하면 @AuthenticationPrincipal 로 해당 내용들을 조회할 수 있음
            } else{
                // 인증정보가  존재하지 않을때
                ExceptionDto statusResponseDto = ExceptionDto.builder()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .state(HttpStatus.BAD_REQUEST)
                    .message("토큰이 유효하지 않습니다.")
                    .build();
                httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                httpServletResponse.setContentType("application/json; charset=UTF-8");
                httpServletResponse.getWriter().write(objectMapper.writeValueAsString(statusResponseDto));
                // write 타입은 스트링 이므로 ,objectMapper 를 통해 맵핑 해서 String 으로변환
                return;
                // 더 필터를 타지 않고 에러응답을 리턴시킴
            }
        }


    }



}
