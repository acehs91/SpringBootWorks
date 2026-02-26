package com.devmatch.controller;

import com.devmatch.dto.MemberDTO;
import com.devmatch.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 인증 관련 컨트롤러 (로그인, 회원가입)
 */
@Slf4j
@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    
    private final MemberService memberService;
    
    /**
     * 로그인 페이지
     */
    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                           Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "이메일 또는 비밀번호가 올바르지 않습니다.");
        }
        model.addAttribute("pageTitle", "로그인");
        return "auth/login";
    }
    
    /**
     * 회원가입 페이지 (유형 선택)
     */
    @GetMapping("/register")
    public String registerType(Model model) {
        model.addAttribute("pageTitle", "회원가입");
        return "auth/register-type";
    }
    
    /**
     * 개발자 회원가입 폼
     */
    @GetMapping("/register/developer")
    public String registerDeveloper(Model model) {
        model.addAttribute("pageTitle", "개발자 회원가입");
        model.addAttribute("memberType", "DEVELOPER");
        return "auth/register";
    }
    
    /**
     * 의뢰인 회원가입 폼
     */
    @GetMapping("/register/client")
    public String registerClient(Model model) {
        model.addAttribute("pageTitle", "의뢰인 회원가입");
        model.addAttribute("memberType", "CLIENT");
        return "auth/register";
    }
    
    /**
     * 회원가입 처리 (POST)
     */
    @PostMapping("/register")
    public String register(@RequestParam String email,
                          @RequestParam String password,
                          @RequestParam String passwordConfirm,
                          @RequestParam String name,
                          @RequestParam(required = false) String phone,
                          @RequestParam String memberType,
                          RedirectAttributes redirectAttributes) {
        
        // 비밀번호 확인
        if (!password.equals(passwordConfirm)) {
            redirectAttributes.addFlashAttribute("errorMsg", "비밀번호가 일치하지 않습니다.");
            return "redirect:/auth/register/" + memberType.toLowerCase();
        }
        
        // 이메일 중복 확인
        if (memberService.isEmailExists(email)) {
            redirectAttributes.addFlashAttribute("errorMsg", "이미 사용 중인 이메일입니다.");
            return "redirect:/auth/register/" + memberType.toLowerCase();
        }
        
        try {
            // 회원 등록
            MemberDTO member = MemberDTO.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .phone(phone)
                    .memberType(memberType)
                    .build();
            
            memberService.register(member);
            log.info("회원가입 성공: {} ({})", email, memberType);
            
            redirectAttributes.addFlashAttribute("successMsg", "회원가입이 완료되었습니다. 로그인해주세요.");
            return "redirect:/auth/login";
            
        } catch (Exception e) {
            log.error("회원가입 실패: {}", e.getMessage());
            redirectAttributes.addFlashAttribute("errorMsg", "회원가입 중 오류가 발생했습니다.");
            return "redirect:/auth/register/" + memberType.toLowerCase();
        }
    }
    
    /**
     * 이메일 중복 확인 (AJAX)
     */
    @GetMapping("/check-email")
    @ResponseBody
    public boolean checkEmail(@RequestParam String email) {
        return !memberService.isEmailExists(email);
    }
}
