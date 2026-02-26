package com.devmatch.service.impl;

import com.devmatch.dto.ApplicationDTO;
import com.devmatch.mapper.ApplicationMapper;
import com.devmatch.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 지원내역 서비스 구현체
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationServiceImpl implements ApplicationService {
    
    private final ApplicationMapper applicationMapper;
    
    @Override
    public boolean apply(ApplicationDTO application) {
        // 중복 지원 체크
        if (hasApplied(application.getPostingId(), application.getMemberId())) {
            return false;
        }
        return applicationMapper.insert(application) > 0;
    }
    
    @Override
    @Transactional(readOnly = true)
    public ApplicationDTO getApplication(Long applicationId) {
        return applicationMapper.findById(applicationId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean hasApplied(Long postingId, Long memberId) {
        return applicationMapper.findByPostingAndMember(postingId, memberId) != null;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ApplicationDTO> getMyApplications(Long memberId) {
        return applicationMapper.findByMemberId(memberId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ApplicationDTO> getApplicants(Long postingId) {
        return applicationMapper.findByPostingId(postingId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<ApplicationDTO> getAllApplicantsByClient(Long clientId) {
        return applicationMapper.findByClientId(clientId);
    }
    
    @Override
    public boolean updateStatus(Long applicationId, String status) {
        return applicationMapper.updateStatus(applicationId, status) > 0;
    }
    
    @Override
    public boolean cancel(Long applicationId, Long memberId) {
        // 본인 지원만 취소 가능
        ApplicationDTO app = applicationMapper.findById(applicationId);
        if (app == null || !app.getMemberId().equals(memberId)) {
            return false;
        }
        // PENDING 상태에서만 취소 가능
        if (!"PENDING".equals(app.getStatus())) {
            return false;
        }
        return applicationMapper.delete(applicationId) > 0;
    }
    
    @Override
    @Transactional(readOnly = true)
    public int getApplicantCount(Long postingId) {
        return applicationMapper.countByPostingId(postingId);
    }
}
