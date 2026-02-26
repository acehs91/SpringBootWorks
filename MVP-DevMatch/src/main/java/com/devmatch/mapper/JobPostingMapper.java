package com.devmatch.mapper;

import com.devmatch.dto.JobPostingDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 채용공고 Mapper 인터페이스
 */
@Mapper
public interface JobPostingMapper {
    
    // 채용공고 등록
    int insertJobPosting(JobPostingDTO posting);
    
    // 채용공고 조회 (ID)
    JobPostingDTO selectJobPostingById(@Param("postingId") Long postingId);
    
    // 채용공고 목록 (의뢰인별)
    List<JobPostingDTO> selectJobPostingsByMemberId(@Param("memberId") Long memberId);
    
    // 채용공고 목록 (전체 공개)
    List<JobPostingDTO> selectOpenJobPostings();
    
    // 채용공고 검색 (기술스택)
    List<JobPostingDTO> searchJobPostingsBySkills(@Param("skills") String skills);
    
    // 채용공고 검색 (키워드)
    List<JobPostingDTO> searchJobPostings(@Param("keyword") String keyword);
    
    // 채용공고 수정
    int updateJobPosting(JobPostingDTO posting);
    
    // 채용공고 상태 변경
    int updateJobPostingStatus(@Param("postingId") Long postingId, 
                                @Param("status") String status);
    
    // 채용공고 삭제
    int deleteJobPosting(@Param("postingId") Long postingId);
    
    // 조회수 증가
    int incrementViewCount(@Param("postingId") Long postingId);
    
    // 채용공고 개수 (의뢰인별)
    int countByMemberId(@Param("memberId") Long memberId);
}
