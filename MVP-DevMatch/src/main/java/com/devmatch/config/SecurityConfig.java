package com.devmatch.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security 설정
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // CSRF 설정 (개발 중 비활성화, 운영 시 활성화 필요)
            .csrf(csrf -> csrf.disable())
            
            // 접근 권한 설정
            .authorizeHttpRequests(auth -> auth
                // 공개 페이지
                .requestMatchers("/", "/home", "/about").permitAll()
                .requestMatchers("/auth/**").permitAll()
                .requestMatchers("/jobs", "/jobs/view/**").permitAll()
                .requestMatchers("/resumes", "/resumes/view/**").permitAll()
                // 정적 리소스
                .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
                // 나머지는 로그인 필요
                .anyRequest().authenticated()
            )
            
            // 폼 로그인 설정
            .formLogin(form -> form
                .loginPage("/auth/login")
                .loginProcessingUrl("/auth/login")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/auth/login?error=true")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll()
            )
            
            // 로그아웃 설정
            .logout(logout -> logout
                .logoutUrl("/auth/logout")
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            );
        
        return http.build();
    }
}
