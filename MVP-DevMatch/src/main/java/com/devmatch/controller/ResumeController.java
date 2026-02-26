package com.devmatch.controller;

import com.devmatch.config.CustomUserDetails;
import com.devmatch.dto.ResumeDTO;
import com.devmatch.service.ResumeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

/**
 * 이력서 컨트롤러
 */
@Slf4j
@Controller
@RequestMapping("/resumes")
@RequiredArgsConstructor
public class ResumeController {
    
    private final ResumeService resumeService;
    
    /**
     * 인재풀 (공개 이력서 목록)
     */
    @GetMapping
    public String list(@RequestParam(required = false) String keyword, Model model) {
        List<ResumeDTO> resumes;
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            resumes = resumeService.searchResumes(keyword);
            model.addAttribute("keyword", keyword);
        } else {
            resumes = resumeService.getPublicResumes();
        }
        
        model.addAttribute("resumes", resumes);
        model.addAttribute("pageTitle", "인재풀");
        return "resume/list";
    }
    
    /**
     * 이력서 상세 보기
     */
    @GetMapping("/view/{resumeId}")
    public String view(@PathVariable Long resumeId, Model model) {
        ResumeDTO resume = resumeService.getResumeWithView(resumeId);
        
        if (resume == null) {
            return "redirect:/resumes";
        }
        
        model.addAttribute("resume", resume);
        model.addAttribute("pageTitle", resume.getTitle());
        return "resume/view";
    }
    
    /**
     * 내 이력서 목록
     */
    @GetMapping("/my")
    public String myResumes(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        List<ResumeDTO> resumes = resumeService.getMyResumes(user.getMemberId());
        model.addAttribute("resumes", resumes);
        model.addAttribute("pageTitle", "내 이력서");
        return "resume/my-list";
    }
    
    /**
     * 이력서 작성 폼
     */
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("resume", new ResumeDTO());
        model.addAttribute("pageTitle", "이력서 작성");
        return "resume/form";
    }
    
    /**
     * 이력서 등록 처리
     */
    @PostMapping("/new")
    public String create(@AuthenticationPrincipal CustomUserDetails user,
                        ResumeDTO resume,
                        RedirectAttributes redirectAttributes) {
        try {
            resume.setMemberId(user.getMemberId());
            resumeService.createResume(resume);
            
            redirectAttributes.addFlashAttribute("successMsg", "이력서가 등록되었습니다.");
            return "redirect:/resumes/my";
            
        } catch (Exception e) {
            log.error("이력서 등록 실패: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMsg", "이력서 등록에 실패했습니다.");
            return "redirect:/resumes/new";
        }
    }
    
    /**
     * 이력서 수정 폼
     */
    @GetMapping("/edit/{resumeId}")
    public String editForm(@PathVariable Long resumeId,
                          @AuthenticationPrincipal CustomUserDetails user,
                          Model model) {
        ResumeDTO resume = resumeService.getResumeById(resumeId);
        
        // 소유자 확인
        if (resume == null || !resume.getMemberId().equals(user.getMemberId())) {
            return "redirect:/resumes/my";
        }
        
        model.addAttribute("resume", resume);
        model.addAttribute("pageTitle", "이력서 수정");
        return "resume/form";
    }
    
    /**
     * 이력서 수정 처리
     */
    @PostMapping("/edit/{resumeId}")
    public String update(@PathVariable Long resumeId,
                        @AuthenticationPrincipal CustomUserDetails user,
                        ResumeDTO resume,
                        RedirectAttributes redirectAttributes) {
        try {
            // 소유자 확인
            if (!resumeService.isOwner(resumeId, user.getMemberId())) {
                redirectAttributes.addFlashAttribute("errorMsg", "수정 권한이 없습니다.");
                return "redirect:/resumes/my";
            }
            
            resume.setResumeId(resumeId);
            resumeService.updateResume(resume);
            
            redirectAttributes.addFlashAttribute("successMsg", "이력서가 수정되었습니다.");
            return "redirect:/resumes/view/" + resumeId;
            
        } catch (Exception e) {
            log.error("이력서 수정 실패: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMsg", "이력서 수정에 실패했습니다.");
            return "redirect:/resumes/edit/" + resumeId;
        }
    }
    
    /**
     * 이력서 삭제
     */
    @PostMapping("/delete/{resumeId}")
    public String delete(@PathVariable Long resumeId,
                        @AuthenticationPrincipal CustomUserDetails user,
                        RedirectAttributes redirectAttributes) {
        try {
            resumeService.deleteResume(resumeId, user.getMemberId());
            redirectAttributes.addFlashAttribute("successMsg", "이력서가 삭제되었습니다.");
        } catch (Exception e) {
            log.error("이력서 삭제 실패: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMsg", "이력서 삭제에 실패했습니다.");
        }
        return "redirect:/resumes/my";
    }
}
