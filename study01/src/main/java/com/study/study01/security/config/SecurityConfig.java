package com.study.study01.security.config;

import com.study.study01.security.handler.CustomAuthenticationFailureHandler;
import com.study.study01.security.handler.CustomAuthenticationSuccessHandler;
import com.study.study01.security.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
// @EnableWebSecurity
// 스프링 시큐리티의 웹 보안 기능을 활성화 시킨다.
// 내부에 @Import가 있으며 이 어노테이션은 다른 @Configuration이 붙은 클래스 또는
// ImportSelector 인터페이스를 구현한 클래스를 Import해오기 위해 사용된다.
// -> 설정 클래스에서 다른 설정 클래스를 불러오기 위해 사용한다.
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;


    // PasswordEncoder는 비밀번호를 암호화 시키는 기능과 받은 비밀번호를 암호화 시켜 비밀번호를 대조하는 기능이 있다.
    // PasswordEncoder를 재정의 함으로써 비밀번호를 어떠한 방식으로 암호화 시킬 것인지, 아니면 암호화 시키지 않을 것인지 결정 할 수 있다.
    @Bean
    public static PasswordEncoder passwordEncoder() {
        // return new Argon2PasswordEncoder();
        // return NoOpPasswordEncoder.getInstance();
         return new BCryptPasswordEncoder();
    }

    // 이미지, css, js파일들과 같은 정적 메소드들은 대부분의 경우에 인증이 필요하지 않다.
    // 이런 경우를 위해 WebSecurityCustomizer를 재정의함으로써 보안을 새롭게 설정할 수 있다.
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    // SecurityFilterChain은 스프링 시큐리티의 필터 체인을 구성하는 중요 요소이다.
    // 서버에 들어오는 모든 요청은 Controller에 접근하기전 반드시 이 필터체인을 통해 검증되고 보안처리된다.
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        // HttpSecurity는 인증/인가 설정을 하기 위한 객체이다.
        http
                // Cross Site Request Forgery : 사이트간 위조 요청
                // 공식문서에 따르면 RestAPI 방식을 통한 서버 요청은 클라이언트 측에서 직접적으로 요청하기 때문에
                // 이 요청에 대해 자연스럽게 검증이 되므로 복잡한 인증 절차를 거칠 이유가 없으므로 csrf를 disable 설정해도 괜찮다.
                // 그러나 RestAPI 방식이 아닌 경우에는 CSRF공격에 취약할 수 있으므로 이런 경우 csrf 토큰을 고려해야 될 수 있다.
                //
                // 당장에는 공부하지 않을 예정이므로 disable처리만 해놓는다.
                .csrf(AbstractHttpConfigurer::disable)

                .formLogin((form) -> form
                        .loginPage("/user/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .loginProcessingUrl("/user/login")
                        .successHandler(customAuthenticationSuccessHandler)
                        .failureHandler(customAuthenticationFailureHandler)
                )

                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/user/login")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                )

                .authorizeHttpRequests((req) -> req
                        .requestMatchers("/").permitAll()
                        .requestMatchers("/user/*").permitAll()
                        .anyRequest().authenticated()
                )

                .userDetailsService(customUserDetailsService)
        ;

        return http.build();
        // 반환한 설정객체를 바탕으로 SecurityFilterChain 객체를 빈등록하며
        // Spring Security 필터 체인으로 등록된다.
        // -> 쉽게 말하면 보안처리용 필터가 생성 및 등록된다.

        // 이 후부턴 요청이 들어오게될 시 컨트롤러에 도달하기 전에 필터 체인을 거치게되므로 보안처리가 된다.
    }
}
