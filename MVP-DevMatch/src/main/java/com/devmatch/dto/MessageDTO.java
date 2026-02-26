package com.devmatch.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

/**
 * 메시지 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MessageDTO {
    
    private Long messageId;
    private Long senderId;
    private Long receiverId;
    private Long applicationId;
    private String content;
    private String isRead;          // Y, N
    private LocalDateTime createdAt;
    
    // 조인용 필드
    private String senderName;
    private String senderEmail;
    private String receiverName;
    private String receiverEmail;
}
