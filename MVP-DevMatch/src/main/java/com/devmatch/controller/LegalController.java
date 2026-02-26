package com.devmatch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 법적 문서 컨트롤러 (약관, 개인정보처리방침)
 */
@Controller
@RequestMapping("/legal")
public class LegalController {
    
    @GetMapping("/terms")
    public String terms() {
        return "legal/terms";
    }
    
    @GetMapping("/privacy")
    public String privacy() {
        return "legal/privacy";
    }
}
