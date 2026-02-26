package com.devmatch.service.impl;

import com.devmatch.dto.MemberDTO;
import com.devmatch.mapper.MemberMapper;
import com.devmatch.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 회원 서비스 구현체
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberServiceImpl implements MemberService {
    
    private final MemberMapper memberMapper;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public MemberDTO register(MemberDTO member) {
        // 이메일 중복 확인
        if (isEmailExists(member.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        
        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodedPassword);
        
        // 기본 상태 설정
        if (member.getStatus() == null) {
            member.setStatus("ACTIVE");
        }
        
        // 회원 등록
        memberMapper.insertMember(member);
        log.info("회원가입 완료: {}", member.getEmail());
        
        return member;
    }
    
    @Override
    @Transactional(readOnly = true)
    public MemberDTO getMemberById(Long memberId) {
        return memberMapper.selectMemberById(memberId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public MemberDTO getMemberByEmail(String email) {
        return memberMapper.selectMemberByEmail(email);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<MemberDTO> getAllMembers() {
        return memberMapper.selectAllMembers();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<MemberDTO> getMembersByType(String memberType) {
        return memberMapper.selectMembersByType(memberType);
    }
    
    @Override
    public void updateMember(MemberDTO member) {
        memberMapper.updateMember(member);
        log.info("회원정보 수정: {}", member.getMemberId());
    }
    
    @Override
    public void updateMemberStatus(Long memberId, String status) {
        memberMapper.updateMemberStatus(memberId, status);
        log.info("회원상태 변경: {} -> {}", memberId, status);
    }
    
    @Override
    public void deleteMember(Long memberId) {
        memberMapper.deleteMember(memberId);
        log.info("회원 삭제: {}", memberId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isEmailExists(String email) {
        return memberMapper.countByEmail(email) > 0;
    }
}
