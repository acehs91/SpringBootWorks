package com.devmatch.service;

import com.devmatch.dto.MemberDTO;
import java.util.List;

/**
 * 회원 서비스 인터페이스
 */
public interface MemberService {
    
    // 회원가입
    MemberDTO register(MemberDTO member);
    
    // 회원 조회
    MemberDTO getMemberById(Long memberId);
    MemberDTO getMemberByEmail(String email);
    
    // 회원 목록
    List<MemberDTO> getAllMembers();
    List<MemberDTO> getMembersByType(String memberType);
    
    // 회원 정보 수정
    void updateMember(MemberDTO member);
    
    // 회원 상태 변경
    void updateMemberStatus(Long memberId, String status);
    
    // 회원 삭제
    void deleteMember(Long memberId);
    
    // 이메일 중복 확인
    boolean isEmailExists(String email);
}
