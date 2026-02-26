package com.devmatch.service;

import com.devmatch.dto.MessageDTO;
import java.util.List;

/**
 * 메시지 서비스 인터페이스
 */
public interface MessageService {
    
    // 메시지 전송
    boolean send(MessageDTO message);
    
    // 메시지 조회
    MessageDTO getMessage(Long messageId);
    
    // 대화 목록 (최근 대화 상대)
    List<MessageDTO> getConversations(Long memberId);
    
    // 특정 상대와의 대화
    List<MessageDTO> getConversation(Long memberId, Long partnerId);
    
    // 안 읽은 메시지 수
    int getUnreadCount(Long memberId);
    
    // 메시지 삭제
    boolean delete(Long messageId, Long memberId);
}
