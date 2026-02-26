package com.devmatch.mapper;

import com.devmatch.dto.ResumeDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 이력서 Mapper 인터페이스
 */
@Mapper
public interface ResumeMapper {
    
    // 이력서 등록
    int insertResume(ResumeDTO resume);
    
    // 이력서 조회 (ID)
    ResumeDTO selectResumeById(@Param("resumeId") Long resumeId);
    
    // 이력서 목록 (회원별)
    List<ResumeDTO> selectResumesByMemberId(@Param("memberId") Long memberId);
    
    // 이력서 목록 (전체 공개)
    List<ResumeDTO> selectPublicResumes();
    
    // 이력서 검색 (기술스택)
    List<ResumeDTO> searchResumesBySkills(@Param("skills") String skills);
    
    // 이력서 검색 (키워드)
    List<ResumeDTO> searchResumes(@Param("keyword") String keyword);
    
    // 이력서 수정
    int updateResume(ResumeDTO resume);
    
    // 이력서 삭제
    int deleteResume(@Param("resumeId") Long resumeId);
    
    // 조회수 증가
    int incrementViewCount(@Param("resumeId") Long resumeId);
    
    // 이력서 개수 (회원별)
    int countByMemberId(@Param("memberId") Long memberId);
}
