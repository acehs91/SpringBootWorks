package com.devmatch.controller;

import com.devmatch.config.CustomUserDetails;
import com.devmatch.dto.JobPostingDTO;
import com.devmatch.service.JobPostingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

/**
 * 채용공고 컨트롤러
 */
@Slf4j
@Controller
@RequestMapping("/jobs")
@RequiredArgsConstructor
public class JobPostingController {
    
    private final JobPostingService jobPostingService;
    
    /**
     * 채용공고 목록
     */
    @GetMapping
    public String list(@RequestParam(required = false) String keyword, Model model) {
        List<JobPostingDTO> jobs;
        
        if (keyword != null && !keyword.trim().isEmpty()) {
            jobs = jobPostingService.searchJobPostings(keyword);
            model.addAttribute("keyword", keyword);
        } else {
            jobs = jobPostingService.getOpenJobPostings();
        }
        
        model.addAttribute("jobs", jobs);
        model.addAttribute("pageTitle", "채용공고");
        return "job/list";
    }
    
    /**
     * 채용공고 상세 보기
     */
    @GetMapping("/view/{postingId}")
    public String view(@PathVariable Long postingId, Model model) {
        JobPostingDTO job = jobPostingService.getJobPostingWithView(postingId);
        
        if (job == null) {
            return "redirect:/jobs";
        }
        
        model.addAttribute("job", job);
        model.addAttribute("pageTitle", job.getTitle());
        return "job/view";
    }
    
    /**
     * 내 채용공고 목록
     */
    @GetMapping("/my")
    public String myJobs(@AuthenticationPrincipal CustomUserDetails user, Model model) {
        List<JobPostingDTO> jobs = jobPostingService.getMyJobPostings(user.getMemberId());
        model.addAttribute("jobs", jobs);
        model.addAttribute("pageTitle", "내 채용공고");
        return "job/my-list";
    }
    
    /**
     * 채용공고 작성 폼
     */
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("job", new JobPostingDTO());
        model.addAttribute("pageTitle", "채용공고 등록");
        return "job/form";
    }
    
    /**
     * 채용공고 등록 처리
     */
    @PostMapping("/new")
    public String create(@AuthenticationPrincipal CustomUserDetails user,
                        JobPostingDTO job,
                        RedirectAttributes redirectAttributes) {
        try {
            job.setMemberId(user.getMemberId());
            jobPostingService.createJobPosting(job);
            
            redirectAttributes.addFlashAttribute("successMsg", "채용공고가 등록되었습니다.");
            return "redirect:/jobs/my";
            
        } catch (Exception e) {
            log.error("채용공고 등록 실패: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMsg", "채용공고 등록에 실패했습니다.");
            return "redirect:/jobs/new";
        }
    }
    
    /**
     * 채용공고 수정 폼
     */
    @GetMapping("/edit/{postingId}")
    public String editForm(@PathVariable Long postingId,
                          @AuthenticationPrincipal CustomUserDetails user,
                          Model model) {
        JobPostingDTO job = jobPostingService.getJobPostingById(postingId);
        
        if (job == null || !job.getMemberId().equals(user.getMemberId())) {
            return "redirect:/jobs/my";
        }
        
        model.addAttribute("job", job);
        model.addAttribute("pageTitle", "채용공고 수정");
        return "job/form";
    }
    
    /**
     * 채용공고 수정 처리
     */
    @PostMapping("/edit/{postingId}")
    public String update(@PathVariable Long postingId,
                        @AuthenticationPrincipal CustomUserDetails user,
                        JobPostingDTO job,
                        RedirectAttributes redirectAttributes) {
        try {
            if (!jobPostingService.isOwner(postingId, user.getMemberId())) {
                redirectAttributes.addFlashAttribute("errorMsg", "수정 권한이 없습니다.");
                return "redirect:/jobs/my";
            }
            
            job.setPostingId(postingId);
            jobPostingService.updateJobPosting(job);
            
            redirectAttributes.addFlashAttribute("successMsg", "채용공고가 수정되었습니다.");
            return "redirect:/jobs/view/" + postingId;
            
        } catch (Exception e) {
            log.error("채용공고 수정 실패: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMsg", "채용공고 수정에 실패했습니다.");
            return "redirect:/jobs/edit/" + postingId;
        }
    }
    
    /**
     * 채용공고 상태 변경 (마감/재오픈)
     */
    @PostMapping("/status/{postingId}")
    public String changeStatus(@PathVariable Long postingId,
                              @RequestParam String status,
                              @AuthenticationPrincipal CustomUserDetails user,
                              RedirectAttributes redirectAttributes) {
        try {
            if (!jobPostingService.isOwner(postingId, user.getMemberId())) {
                redirectAttributes.addFlashAttribute("errorMsg", "권한이 없습니다.");
                return "redirect:/jobs/my";
            }
            
            jobPostingService.updateJobPostingStatus(postingId, status);
            String msg = "CLOSED".equals(status) ? "채용이 마감되었습니다." : "채용이 재오픈되었습니다.";
            redirectAttributes.addFlashAttribute("successMsg", msg);
            
        } catch (Exception e) {
            log.error("상태 변경 실패: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMsg", "상태 변경에 실패했습니다.");
        }
        return "redirect:/jobs/my";
    }
    
    /**
     * 채용공고 삭제
     */
    @PostMapping("/delete/{postingId}")
    public String delete(@PathVariable Long postingId,
                        @AuthenticationPrincipal CustomUserDetails user,
                        RedirectAttributes redirectAttributes) {
        try {
            jobPostingService.deleteJobPosting(postingId, user.getMemberId());
            redirectAttributes.addFlashAttribute("successMsg", "채용공고가 삭제되었습니다.");
        } catch (Exception e) {
            log.error("채용공고 삭제 실패: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMsg", "채용공고 삭제에 실패했습니다.");
        }
        return "redirect:/jobs/my";
    }
}
