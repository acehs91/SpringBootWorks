package com.devmatch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 채용공고 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JobPostingDTO {
    
    private Long postingId;
    private Long memberId;
    private String title;
    private String companyName;
    private String description;     // 업무 설명
    private String requirements;    // 자격요건
    private String skills;          // 요구 기술스택
    private String location;        // 근무지
    private String workType;        // REMOTE, ONSITE, HYBRID
    private String employmentType;  // FULL_TIME, PART_TIME, CONTRACT, FREELANCE
    private Long salaryMin;
    private Long salaryMax;
    private LocalDate deadline;     // 마감일
    private String status;          // OPEN, CLOSED, DRAFT
    private Long viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 조인용 필드
    private String clientName;      // 의뢰인 이름
    private Long applicationCount;  // 지원자 수
}
