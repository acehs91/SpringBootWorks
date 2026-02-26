package com.devmatch.service;

import com.devmatch.dto.ApplicationDTO;
import java.util.List;

/**
 * 지원내역 서비스 인터페이스
 */
public interface ApplicationService {
    
    // 지원하기
    boolean apply(ApplicationDTO application);
    
    // 지원내역 조회
    ApplicationDTO getApplication(Long applicationId);
    
    // 이미 지원했는지 확인
    boolean hasApplied(Long postingId, Long memberId);
    
    // 내 지원내역 (개발자용)
    List<ApplicationDTO> getMyApplications(Long memberId);
    
    // 채용공고별 지원자 목록 (의뢰인용)
    List<ApplicationDTO> getApplicants(Long postingId);
    
    // 의뢰인의 모든 지원자
    List<ApplicationDTO> getAllApplicantsByClient(Long clientId);
    
    // 지원 상태 변경
    boolean updateStatus(Long applicationId, String status);
    
    // 지원 취소
    boolean cancel(Long applicationId, Long memberId);
    
    // 지원자 수
    int getApplicantCount(Long postingId);
}
