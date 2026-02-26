package com.devmatch.mapper;

import com.devmatch.dto.MessageDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 메시지 Mapper
 */
@Mapper
public interface MessageMapper {
    
    // 메시지 전송
    int insert(MessageDTO message);
    
    // 메시지 조회 (단건)
    MessageDTO findById(Long messageId);
    
    // 대화 목록 (최근 대화 상대 목록)
    List<MessageDTO> findConversations(Long memberId);
    
    // 특정 상대와의 대화 내역
    List<MessageDTO> findByPartner(@Param("memberId") Long memberId, 
                                   @Param("partnerId") Long partnerId);
    
    // 읽음 처리
    int markAsRead(@Param("memberId") Long memberId, 
                   @Param("partnerId") Long partnerId);
    
    // 안 읽은 메시지 수
    int countUnread(Long memberId);
    
    // 메시지 삭제
    int delete(Long messageId);
}
