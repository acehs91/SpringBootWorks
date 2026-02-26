package com.devmatch.config;

import com.devmatch.dto.MemberDTO;
import com.devmatch.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Spring Security UserDetailsService 구현
 * 로그인 시 사용자 정보를 조회하는 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    
    private final MemberMapper memberMapper;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.info("로그인 시도: {}", email);
        
        MemberDTO member = memberMapper.selectMemberByEmail(email);
        
        if (member == null) {
            log.warn("사용자를 찾을 수 없음: {}", email);
            throw new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + email);
        }
        
        log.info("로그인 성공: {} ({})", member.getName(), member.getMemberType());
        return new CustomUserDetails(member);
    }
}
