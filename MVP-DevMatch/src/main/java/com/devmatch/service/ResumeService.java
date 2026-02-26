package com.devmatch.service;

import com.devmatch.dto.ResumeDTO;
import java.util.List;

/**
 * 이력서 서비스 인터페이스
 */
public interface ResumeService {
    
    // 이력서 등록
    ResumeDTO createResume(ResumeDTO resume);
    
    // 이력서 조회
    ResumeDTO getResumeById(Long resumeId);
    
    // 이력서 조회 (조회수 증가 포함)
    ResumeDTO getResumeWithView(Long resumeId);
    
    // 내 이력서 목록
    List<ResumeDTO> getMyResumes(Long memberId);
    
    // 공개 이력서 목록 (인재풀)
    List<ResumeDTO> getPublicResumes();
    
    // 이력서 검색 (기술스택)
    List<ResumeDTO> searchBySkills(String skills);
    
    // 이력서 검색 (키워드)
    List<ResumeDTO> searchResumes(String keyword);
    
    // 이력서 수정
    void updateResume(ResumeDTO resume);
    
    // 이력서 삭제
    void deleteResume(Long resumeId, Long memberId);
    
    // 이력서 소유자 확인
    boolean isOwner(Long resumeId, Long memberId);
}
