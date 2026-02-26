package com.devmatch.controller;

import com.devmatch.config.CustomUserDetails;
import com.devmatch.dto.MessageDTO;
import com.devmatch.dto.MemberDTO;
import com.devmatch.service.MessageService;
import com.devmatch.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

/**
 * 메시지 컨트롤러
 */
@Controller
@RequestMapping("/messages")
@RequiredArgsConstructor
public class MessageController {
    
    private final MessageService messageService;
    private final MemberService memberService;
    
    /**
     * 메시지 목록 (대화 상대 목록)
     */
    @GetMapping
    public String list(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        List<MessageDTO> conversations = messageService.getConversations(user.getMemberId());
        int unreadCount = messageService.getUnreadCount(user.getMemberId());
        
        model.addAttribute("conversations", conversations);
        model.addAttribute("unreadCount", unreadCount);
        model.addAttribute("currentUserId", user.getMemberId());
        
        return "message/list";
    }
    
    /**
     * 대화방 (특정 상대와의 대화)
     */
    @GetMapping("/chat/{partnerId}")
    public String chat(@PathVariable Long partnerId,
                      @AuthenticationPrincipal CustomUserDetails user,
                      Model model,
                      RedirectAttributes rttr) {
        
        // 상대방 정보
        MemberDTO partner = memberService.getMemberById(partnerId);
        if (partner == null) {
            rttr.addFlashAttribute("error", "사용자를 찾을 수 없습니다.");
            return "redirect:/messages";
        }
        
        // 대화 내역 조회 (+ 읽음 처리)
        List<MessageDTO> messages = messageService.getConversation(user.getMemberId(), partnerId);
        
        model.addAttribute("partner", partner);
        model.addAttribute("messages", messages);
        model.addAttribute("currentUserId", user.getMemberId());
        
        return "message/chat";
    }
    
    /**
     * 메시지 전송
     */
    @PostMapping("/send")
    public String send(@RequestParam Long receiverId,
                      @RequestParam String content,
                      @RequestParam(required = false) Long applicationId,
                      @AuthenticationPrincipal CustomUserDetails user,
                      RedirectAttributes rttr) {
        
        if (content == null || content.trim().isEmpty()) {
            rttr.addFlashAttribute("error", "메시지 내용을 입력하세요.");
            return "redirect:/messages/chat/" + receiverId;
        }
        
        MessageDTO message = MessageDTO.builder()
                .senderId(user.getMemberId())
                .receiverId(receiverId)
                .applicationId(applicationId)
                .content(content.trim())
                .build();
        
        if (messageService.send(message)) {
            rttr.addFlashAttribute("message", "메시지를 전송했습니다.");
        } else {
            rttr.addFlashAttribute("error", "메시지 전송에 실패했습니다.");
        }
        
        return "redirect:/messages/chat/" + receiverId;
    }
    
    /**
     * 새 대화 시작 (상대 선택)
     */
    @GetMapping("/new")
    public String newChat(@RequestParam(required = false) Long to,
                         @AuthenticationPrincipal CustomUserDetails user,
                         Model model,
                         RedirectAttributes rttr) {
        
        // 특정 상대에게 바로 메시지
        if (to != null) {
            MemberDTO partner = memberService.getMemberById(to);
            if (partner != null) {
                return "redirect:/messages/chat/" + to;
            }
        }
        
        // TODO: 상대 검색 기능
        return "redirect:/messages";
    }
}
