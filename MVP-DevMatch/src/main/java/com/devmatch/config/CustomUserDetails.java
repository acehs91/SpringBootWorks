package com.devmatch.config;

import com.devmatch.dto.MemberDTO;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.util.Collection;
import java.util.Collections;

/**
 * Spring Security UserDetails 구현
 * 로그인한 사용자 정보를 담는 클래스
 */
@Getter
public class CustomUserDetails implements UserDetails {
    
    private final MemberDTO member;
    
    public CustomUserDetails(MemberDTO member) {
        this.member = member;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 회원 유형에 따른 권한 부여
        String role = "ROLE_" + member.getMemberType(); // ROLE_DEVELOPER, ROLE_CLIENT
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
    
    @Override
    public String getPassword() {
        return member.getPassword();
    }
    
    @Override
    public String getUsername() {
        return member.getEmail();
    }
    
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked() {
        return !"BANNED".equals(member.getStatus());
    }
    
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    
    @Override
    public boolean isEnabled() {
        return "ACTIVE".equals(member.getStatus());
    }
    
    // 편의 메서드
    public Long getMemberId() {
        return member.getMemberId();
    }
    
    public String getName() {
        return member.getName();
    }
    
    public String getMemberType() {
        return member.getMemberType();
    }
}
