package com.devmatch.service.impl;

import com.devmatch.dto.ResumeDTO;
import com.devmatch.mapper.ResumeMapper;
import com.devmatch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 이력서 서비스 구현체
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ResumeServiceImpl implements ResumeService {
    
    private final ResumeMapper resumeMapper;
    
    @Override
    public ResumeDTO createResume(ResumeDTO resume) {
        resumeMapper.insertResume(resume);
        log.info("이력서 등록: {} (회원: {})", resume.getResumeId(), resume.getMemberId());
        return resume;
    }
    
    @Override
    @Transactional(readOnly = true)
    public ResumeDTO getResumeById(Long resumeId) {
        return resumeMapper.selectResumeById(resumeId);
    }
    
    @Override
    public ResumeDTO getResumeWithView(Long resumeId) {
        resumeMapper.incrementViewCount(resumeId);
        return resumeMapper.selectResumeById(resumeId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ResumeDTO> getMyResumes(Long memberId) {
        return resumeMapper.selectResumesByMemberId(memberId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ResumeDTO> getPublicResumes() {
        return resumeMapper.selectPublicResumes();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ResumeDTO> searchBySkills(String skills) {
        return resumeMapper.searchResumesBySkills(skills);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ResumeDTO> searchResumes(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getPublicResumes();
        }
        return resumeMapper.searchResumes(keyword.trim());
    }
    
    @Override
    public void updateResume(ResumeDTO resume) {
        resumeMapper.updateResume(resume);
        log.info("이력서 수정: {}", resume.getResumeId());
    }
    
    @Override
    public void deleteResume(Long resumeId, Long memberId) {
        if (!isOwner(resumeId, memberId)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }
        resumeMapper.deleteResume(resumeId);
        log.info("이력서 삭제: {}", resumeId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isOwner(Long resumeId, Long memberId) {
        ResumeDTO resume = resumeMapper.selectResumeById(resumeId);
        return resume != null && resume.getMemberId().equals(memberId);
    }
}
