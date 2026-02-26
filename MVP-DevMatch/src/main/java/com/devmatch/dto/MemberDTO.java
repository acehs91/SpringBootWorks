package com.devmatch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 회원 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
    
    private Long memberId;
    private String email;
    private String password;
    private String name;
    private String memberType;      // DEVELOPER, CLIENT
    private String phone;
    private String profileImage;
    private String provider;        // 소셜로그인: google, kakao, naver
    private String providerId;
    private String status;          // ACTIVE, INACTIVE, BANNED
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
