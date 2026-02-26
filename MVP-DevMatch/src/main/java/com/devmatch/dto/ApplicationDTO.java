package com.devmatch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 지원내역 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationDTO {
    
    private Long applicationId;
    private Long postingId;
    private Long memberId;
    private Long resumeId;
    private String coverLetter;     // 자기소개서
    private String status;          // PENDING, REVIEWED, ACCEPTED, REJECTED
    private Double matchScore;      // 매칭 점수 (%)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 조인용 필드
    private String postingTitle;    // 공고 제목
    private String companyName;     // 회사명
    private String applicantName;   // 지원자 이름
    private String applicantEmail;  // 지원자 이메일
    private String resumeTitle;     // 이력서 제목
}
