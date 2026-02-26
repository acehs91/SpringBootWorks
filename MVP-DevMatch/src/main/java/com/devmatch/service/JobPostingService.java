package com.devmatch.service;

import com.devmatch.dto.JobPostingDTO;
import java.util.List;

/**
 * 채용공고 서비스 인터페이스
 */
public interface JobPostingService {
    
    // 채용공고 등록
    JobPostingDTO createJobPosting(JobPostingDTO posting);
    
    // 채용공고 조회
    JobPostingDTO getJobPostingById(Long postingId);
    
    // 채용공고 조회 (조회수 증가 포함)
    JobPostingDTO getJobPostingWithView(Long postingId);
    
    // 내 채용공고 목록
    List<JobPostingDTO> getMyJobPostings(Long memberId);
    
    // 공개 채용공고 목록
    List<JobPostingDTO> getOpenJobPostings();
    
    // 채용공고 검색 (기술스택)
    List<JobPostingDTO> searchBySkills(String skills);
    
    // 채용공고 검색 (키워드)
    List<JobPostingDTO> searchJobPostings(String keyword);
    
    // 채용공고 수정
    void updateJobPosting(JobPostingDTO posting);
    
    // 채용공고 상태 변경
    void updateJobPostingStatus(Long postingId, String status);
    
    // 채용공고 삭제
    void deleteJobPosting(Long postingId, Long memberId);
    
    // 채용공고 소유자 확인
    boolean isOwner(Long postingId, Long memberId);
}
