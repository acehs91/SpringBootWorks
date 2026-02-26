package com.devmatch.service.impl;

import com.devmatch.dto.MessageDTO;
import com.devmatch.mapper.MessageMapper;
import com.devmatch.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * 메시지 서비스 구현체
 */
@Service
@RequiredArgsConstructor
@Transactional
public class MessageServiceImpl implements MessageService {
    
    private final MessageMapper messageMapper;
    
    @Override
    public boolean send(MessageDTO message) {
        return messageMapper.insert(message) > 0;
    }
    
    @Override
    @Transactional(readOnly = true)
    public MessageDTO getMessage(Long messageId) {
        return messageMapper.findById(messageId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<MessageDTO> getConversations(Long memberId) {
        return messageMapper.findConversations(memberId);
    }
    
    @Override
    public List<MessageDTO> getConversation(Long memberId, Long partnerId) {
        // 대화 조회 시 읽음 처리
        messageMapper.markAsRead(memberId, partnerId);
        return messageMapper.findByPartner(memberId, partnerId);
    }
    
    @Override
    @Transactional(readOnly = true)
    public int getUnreadCount(Long memberId) {
        return messageMapper.countUnread(memberId);
    }
    
    @Override
    public boolean delete(Long messageId, Long memberId) {
        MessageDTO msg = messageMapper.findById(messageId);
        if (msg == null) return false;
        // 본인이 보낸 메시지만 삭제 가능
        if (!msg.getSenderId().equals(memberId)) {
            return false;
        }
        return messageMapper.delete(messageId) > 0;
    }
}
