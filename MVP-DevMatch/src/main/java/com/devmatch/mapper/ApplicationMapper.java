package com.devmatch.mapper;

import com.devmatch.dto.ApplicationDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 지원내역 Mapper
 */
@Mapper
public interface ApplicationMapper {
    
    // 지원하기
    int insert(ApplicationDTO application);
    
    // 지원내역 조회 (단건)
    ApplicationDTO findById(Long applicationId);
    
    // 중복 지원 체크
    ApplicationDTO findByPostingAndMember(@Param("postingId") Long postingId, 
                                          @Param("memberId") Long memberId);
    
    // 내 지원내역 (개발자용)
    List<ApplicationDTO> findByMemberId(Long memberId);
    
    // 채용공고별 지원자 목록 (의뢰인용)
    List<ApplicationDTO> findByPostingId(Long postingId);
    
    // 의뢰인의 모든 공고에 대한 지원자 목록
    List<ApplicationDTO> findByClientId(Long clientId);
    
    // 지원 상태 변경
    int updateStatus(@Param("applicationId") Long applicationId, 
                     @Param("status") String status);
    
    // 지원 취소 (삭제)
    int delete(Long applicationId);
    
    // 채용공고별 지원자 수
    int countByPostingId(Long postingId);
}
