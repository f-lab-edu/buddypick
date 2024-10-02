package com.buddypick.common.config.jwt;


import com.buddypick.common.dto.CommonResponse;
import com.buddypick.user.dto.LoginMemberDetails;
import com.buddypick.user.dto.LoginRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    // Post의 /login 호출 시 동작
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            LoginRequest loginRequest = mapper.readValue(request.getInputStream(), LoginRequest.class);

            // 강제 로그인 (토큰 생성해서 강제 로그인)
            // jwt를 쓴다고 해도, 컨트롤러에 진입했을 때, 시큐리티의 권한체크/인증체크의 도움을 받기위해 세션을 만들기 위한 것
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword());
            // 시큐리티 컨텍스트 내부 세션에 담음 (UserDetailsService의 loadUserByUsername을 호출)
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            return authentication;
        } catch (Exception e){
            //unsuccessfulAuthentication를 호출
            throw new InternalAuthenticationServiceException(e.getMessage());
        }
    }

    //위에서 return authentication가 잘 작동하면 실행된다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException {
        LoginMemberDetails loginMemberDetails = (LoginMemberDetails) authResult.getPrincipal();

        List<String> roles = loginMemberDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        //String jwtToken = JwtTokenProvider.createJwtToken(loginMemberDetails.getMemberDto(), roles);
        String jwtToken = jwtTokenProvider.createAccessToken(loginMemberDetails.getMemberDto(), roles);

        //response.addHeader("Authorization", "Bearer " + jwtToken);

        Map<String, Object> tokens = Map.of(
                "accessToken", jwtToken
                // "expiredTime", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(expiredTime)
        );
        CommonResponse.successResponse(response, "로그인성공", tokens);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        CommonResponse.errorResponse(response, "로그인실패");
    }
}