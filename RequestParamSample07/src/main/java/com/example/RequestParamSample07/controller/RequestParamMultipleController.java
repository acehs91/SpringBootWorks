package com.example.RequestParamSample07.controller;

import com.example.RequestParamSample07.form.SampleForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RequestParamMultipleController {
    // GET
    @GetMapping("/multiple")
    public String showView() {
        return "entry";
    }
    // POST
    @PostMapping("/confirm")
    public String confirmView(SampleForm f) {
        return "confirm";
    }
}
