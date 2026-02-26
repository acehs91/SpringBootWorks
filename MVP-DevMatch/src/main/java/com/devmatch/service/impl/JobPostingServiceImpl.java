package com.devmatch.service.impl;

import com.devmatch.dto.JobPostingDTO;
import com.devmatch.mapper.JobPostingMapper;
import com.devmatch.service.JobPostingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 채용공고 서비스 구현체
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class JobPostingServiceImpl implements JobPostingService {
    
    private final JobPostingMapper jobPostingMapper;
    
    @Override
    public JobPostingDTO createJobPosting(JobPostingDTO posting) {
        if (posting.getStatus() == null) {
            posting.setStatus("OPEN");
        }
        jobPostingMapper.insertJobPosting(posting);
        log.info("채용공고 등록: {} (의뢰인: {})", posting.getPostingId(), posting.getMemberId());
        return posting;
    }
    
    @Override
    @Transactional(readOnly = true)
    public JobPostingDTO getJobPostingById(Long postingId) {
        return jobPostingMapper.selectJobPostingById(postingId);
    }
    
    @Override
    public JobPostingDTO getJobPostingWithView(Long postingId) {
        jobPostingMapper.incrementViewCount(postingId);
        return jobPostingMapper.selectJobPostingById(postingId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<JobPostingDTO> getMyJobPostings(Long memberId) {
        return jobPostingMapper.selectJobPostingsByMemberId(memberId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<JobPostingDTO> getOpenJobPostings() {
        return jobPostingMapper.selectOpenJobPostings();
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<JobPostingDTO> searchBySkills(String skills) {
        return jobPostingMapper.searchJobPostingsBySkills(skills);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<JobPostingDTO> searchJobPostings(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getOpenJobPostings();
        }
        return jobPostingMapper.searchJobPostings(keyword.trim());
    }
    
    @Override
    public void updateJobPosting(JobPostingDTO posting) {
        jobPostingMapper.updateJobPosting(posting);
        log.info("채용공고 수정: {}", posting.getPostingId());
    }
    
    @Override
    public void updateJobPostingStatus(Long postingId, String status) {
        jobPostingMapper.updateJobPostingStatus(postingId, status);
        log.info("채용공고 상태 변경: {} -> {}", postingId, status);
    }
    
    @Override
    public void deleteJobPosting(Long postingId, Long memberId) {
        if (!isOwner(postingId, memberId)) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }
        jobPostingMapper.deleteJobPosting(postingId);
        log.info("채용공고 삭제: {}", postingId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isOwner(Long postingId, Long memberId) {
        JobPostingDTO posting = jobPostingMapper.selectJobPostingById(postingId);
        return posting != null && posting.getMemberId().equals(memberId);
    }
}
