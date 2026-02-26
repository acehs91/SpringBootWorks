package com.example.RequestParamSample07.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RequestParamController {
    
    @GetMapping("/input")
    public String showinput() {
        return "input";
    }
    
    @GetMapping("/result")
    public String showoutputGet(@RequestParam("val") String value, Model model) {
        model.addAttribute("value", value);
        return "output";
    }
    
    @PostMapping("/result")
    public String showoutputPost(@RequestParam("val") String value, Model model) {
        model.addAttribute("value", value);
        return "output";
    }
}
