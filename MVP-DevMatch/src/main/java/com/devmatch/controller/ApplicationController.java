package com.devmatch.controller;

import com.devmatch.config.CustomUserDetails;
import com.devmatch.dto.ApplicationDTO;
import com.devmatch.dto.ResumeDTO;
import com.devmatch.dto.JobPostingDTO;
import com.devmatch.service.ApplicationService;
import com.devmatch.service.ResumeService;
import com.devmatch.service.JobPostingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

/**
 * 지원 관련 컨트롤러
 */
@Controller
@RequestMapping("/applications")
@RequiredArgsConstructor
public class ApplicationController {
    
    private final ApplicationService applicationService;
    private final ResumeService resumeService;
    private final JobPostingService jobPostingService;
    
    /**
     * 지원하기 폼 (GET)
     */
    @GetMapping("/apply/{postingId}")
    public String applyForm(@PathVariable Long postingId,
                           @AuthenticationPrincipal CustomUserDetails user,
                           Model model,
                           RedirectAttributes rttr) {
        
        // 개발자만 지원 가능
        if (!"DEVELOPER".equals(user.getMemberType())) {
            rttr.addFlashAttribute("error", "개발자만 지원할 수 있습니다.");
            return "redirect:/jobs/view/" + postingId;
        }
        
        // 이미 지원했는지 체크
        if (applicationService.hasApplied(postingId, user.getMemberId())) {
            rttr.addFlashAttribute("error", "이미 지원한 공고입니다.");
            return "redirect:/jobs/view/" + postingId;
        }
        
        // 채용공고 정보
        JobPostingDTO posting = jobPostingService.getJobPosting(postingId);
        if (posting == null || !"OPEN".equals(posting.getStatus())) {
            rttr.addFlashAttribute("error", "마감된 공고입니다.");
            return "redirect:/jobs";
        }
        
        // 내 이력서 목록
        List<ResumeDTO> myResumes = resumeService.getMyResumes(user.getMemberId());
        
        model.addAttribute("posting", posting);
        model.addAttribute("resumes", myResumes);
        model.addAttribute("application", new ApplicationDTO());
        
        return "application/apply-form";
    }
    
    /**
     * 지원하기 처리 (POST)
     */
    @PostMapping("/apply/{postingId}")
    public String apply(@PathVariable Long postingId,
                       @ModelAttribute ApplicationDTO application,
                       @AuthenticationPrincipal CustomUserDetails user,
                       RedirectAttributes rttr) {
        
        application.setPostingId(postingId);
        application.setMemberId(user.getMemberId());
        
        if (applicationService.apply(application)) {
            rttr.addFlashAttribute("message", "지원이 완료되었습니다!");
        } else {
            rttr.addFlashAttribute("error", "지원에 실패했습니다. 이미 지원했거나 오류가 발생했습니다.");
        }
        
        return "redirect:/applications/my";
    }
    
    /**
     * 내 지원내역 (개발자용)
     */
    @GetMapping("/my")
    public String myApplications(@AuthenticationPrincipal CustomUserDetails user,
                                 Model model) {
        List<ApplicationDTO> applications = applicationService.getMyApplications(user.getMemberId());
        model.addAttribute("applications", applications);
        return "application/my-applications";
    }
    
    /**
     * 채용공고별 지원자 목록 (의뢰인용)
     */
    @GetMapping("/applicants/{postingId}")
    public String applicants(@PathVariable Long postingId,
                            @AuthenticationPrincipal CustomUserDetails user,
                            Model model,
                            RedirectAttributes rttr) {
        
        // 공고 소유자 확인
        JobPostingDTO posting = jobPostingService.getJobPosting(postingId);
        if (posting == null || !posting.getMemberId().equals(user.getMemberId())) {
            rttr.addFlashAttribute("error", "접근 권한이 없습니다.");
            return "redirect:/jobs/my";
        }
        
        List<ApplicationDTO> applicants = applicationService.getApplicants(postingId);
        model.addAttribute("posting", posting);
        model.addAttribute("applicants", applicants);
        
        return "application/applicants";
    }
    
    /**
     * 지원 상태 변경 (의뢰인용)
     */
    @PostMapping("/status/{applicationId}")
    public String updateStatus(@PathVariable Long applicationId,
                              @RequestParam String status,
                              @AuthenticationPrincipal CustomUserDetails user,
                              RedirectAttributes rttr) {
        
        // 권한 확인
        ApplicationDTO app = applicationService.getApplication(applicationId);
        if (app == null) {
            rttr.addFlashAttribute("error", "지원내역을 찾을 수 없습니다.");
            return "redirect:/jobs/my";
        }
        
        JobPostingDTO posting = jobPostingService.getJobPosting(app.getPostingId());
        if (!posting.getMemberId().equals(user.getMemberId())) {
            rttr.addFlashAttribute("error", "접근 권한이 없습니다.");
            return "redirect:/jobs/my";
        }
        
        if (applicationService.updateStatus(applicationId, status)) {
            rttr.addFlashAttribute("message", "상태가 변경되었습니다.");
        } else {
            rttr.addFlashAttribute("error", "상태 변경에 실패했습니다.");
        }
        
        return "redirect:/applications/applicants/" + app.getPostingId();
    }
    
    /**
     * 지원 취소 (개발자용)
     */
    @PostMapping("/cancel/{applicationId}")
    public String cancel(@PathVariable Long applicationId,
                        @AuthenticationPrincipal CustomUserDetails user,
                        RedirectAttributes rttr) {
        
        if (applicationService.cancel(applicationId, user.getMemberId())) {
            rttr.addFlashAttribute("message", "지원이 취소되었습니다.");
        } else {
            rttr.addFlashAttribute("error", "지원 취소에 실패했습니다. 이미 검토 중인 지원은 취소할 수 없습니다.");
        }
        
        return "redirect:/applications/my";
    }
}
