package com.devmatch.mapper;

import com.devmatch.dto.MemberDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 회원 Mapper 인터페이스
 */
@Mapper
public interface MemberMapper {
    
    // 회원 등록
    int insertMember(MemberDTO member);
    
    // 회원 조회 (ID)
    MemberDTO selectMemberById(@Param("memberId") Long memberId);
    
    // 회원 조회 (이메일)
    MemberDTO selectMemberByEmail(@Param("email") String email);
    
    // 회원 조회 (소셜로그인)
    MemberDTO selectMemberByProvider(@Param("provider") String provider, 
                                      @Param("providerId") String providerId);
    
    // 회원 목록 (전체)
    List<MemberDTO> selectAllMembers();
    
    // 회원 목록 (유형별)
    List<MemberDTO> selectMembersByType(@Param("memberType") String memberType);
    
    // 회원 정보 수정
    int updateMember(MemberDTO member);
    
    // 회원 상태 변경
    int updateMemberStatus(@Param("memberId") Long memberId, 
                           @Param("status") String status);
    
    // 회원 삭제
    int deleteMember(@Param("memberId") Long memberId);
    
    // 이메일 중복 확인
    int countByEmail(@Param("email") String email);
}
