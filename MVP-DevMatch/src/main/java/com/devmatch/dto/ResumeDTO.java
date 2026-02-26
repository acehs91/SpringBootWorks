package com.devmatch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 이력서 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumeDTO {
    
    private Long resumeId;
    private Long memberId;
    private String title;
    private String introduction;    // 자기소개
    private String skills;          // 기술스택 (쉼표 구분)
    private String experience;      // 경력사항
    private String education;       // 학력
    private String portfolioUrl;
    private String githubUrl;
    private String salaryType;      // MONTHLY, HOURLY, PROJECT
    private Long salaryAmount;
    private String isPublic;        // Y, N
    private Long viewCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // 조인용 필드
    private String memberName;      // 작성자 이름
    private String memberEmail;     // 작성자 이메일
}
