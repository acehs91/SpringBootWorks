package com.devmatch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 메인 페이지 컨트롤러
 */
@Controller
public class HomeController {
    
    /**
     * 메인 페이지
     */
    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("pageTitle", "DevMatch - 개발자 매칭 플랫폼");
        return "index";
    }
    
    /**
     * 대시보드 (로그인 후)
     */
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("pageTitle", "대시보드");
        return "dashboard";
    }
    
    /**
     * 소개 페이지
     */
    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("pageTitle", "서비스 소개");
        return "about";
    }
}
