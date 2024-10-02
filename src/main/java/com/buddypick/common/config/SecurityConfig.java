package com.buddypick.common.config;


import com.buddypick.common.config.jwt.JwtAuthorizationFilter;
import com.buddypick.common.config.jwt.JwtTokenProvider;
import com.buddypick.common.config.jwt.LoginFilter;
import com.buddypick.common.exception.CustomAccessDeniedEntryPoint;
import com.buddypick.common.exception.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        // 기본적으로 bcrypt 암호화 알고리즘의 BCryptPasswordEncoder 객체를 생성하고 사용한다.
        return new BCryptPasswordEncoder();
    }

    //TODO : 왜 securityFilterChain안에서 만들지 않고 CustomSecurityFilterManager로 따로 뺐는지
    public class CustomSecurityFilterManager extends AbstractHttpConfigurer<CustomSecurityFilterManager, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            AuthenticationManager authenticationManager = builder.getSharedObject(AuthenticationManager.class);
            LoginFilter loginFilter = new LoginFilter(authenticationManager, jwtTokenProvider);
            loginFilter.setFilterProcessesUrl("/member/login");
            builder.addFilter(loginFilter);
            super.configure(builder);
        }
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests( auth -> auth
                        .requestMatchers("/member/admin").hasRole("ADMIN")  // 테스트용
                        .requestMatchers("/member/user").hasRole("USER")    // 테스트용
                        .anyRequest().permitAll()
                )
                .formLogin(AbstractHttpConfigurer::disable)
                // jSessionId를 서버쪽에서 관리하지 않겠다. (jwt와 같이 세션을 사용하지 않는 경우 사용) = 무상태성
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(AbstractHttpConfigurer::disable) // 기본인증 팝업창 사용을 하지 않겠다.
                .csrf(AbstractHttpConfigurer::disable)  //csrf 비활성화
                .cors().configurationSource(configurationSource());
        // 커스텀 필터 적용 (시큐리티 필터 교환)
        http.apply(new CustomSecurityFilterManager());
        http.addFilterBefore(new JwtAuthorizationFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class);
        // 인증되지 않은 사용자(로그인안한 사용자) 접근시 exception
        http.exceptionHandling().authenticationEntryPoint(new CustomAuthenticationEntryPoint());
        // 인증된 사용자가 권한이 없는 곳에 접근시 exception
        http.exceptionHandling().accessDeniedHandler(new CustomAccessDeniedEntryPoint());
        return http.build();
    }

    public CorsConfigurationSource configurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");    // GET, POST, PUT, DELETE (자바스크립트 요청허용)
        configuration.addAllowedOriginPattern("*"); // 모든 IP 주소 허용 (ex. 프론트엔드 쪽 IP만 허용)
        configuration.setAllowCredentials(true);    // 클라이언트에서 쿠키 요청 허용

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
