package com.example.springmvcviewsample05.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("hello")
public class HelloViweController {
        @GetMapping("view")
        public String helloview(){
            return "hello";
        }
        
        @GetMapping("model")
        public String helloView(Model model){
            model.addAttribute("msg","타임리프");
            return "helloThymeleaf";
        }
        @GetMapping("modelandview")
    public ModelAndView helloview2(ModelAndView modelAndView){
            //데이터 저장
            modelAndView.addObject("msg", "모델엔 뷰 타임리프");;
            //뷰템플릿
            modelAndView.setViewName("helloThymeleaf");
            return modelAndView;
        }


}
