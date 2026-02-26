package com.example.PathVariableSample08.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class PathVariableController {
    // 화면 이동용
    @GetMapping("/show")
    public String showView() {
        return "show";
    }
    // GET + URL 분기
    @GetMapping("/function/{no}")
    public String selectFunction(@PathVariable("no") Integer no){
        // 뷰 이름
        String view = null;
        switch (no) {
            case 1:
                view = "pathvariable/function1";
                break;
            case 2:
                view = "pathvariable/function2";
                break;
            case 3:
                view = "pathvariable/function3";
                break;
        }
        return view;
    }
    
    // POST 방식 = params 속성 전달 가능
/* params = "a" => 요청 파라미터 이름이 "a"인 항목이 존재해야 함 메서드가 실행 => 파라미터의 '값'은 아님 -> 이름 존재여부 검사*/
    @PostMapping(value = "send", params = "a")
    public String showAView() {
        return "submit/a";
    }
    @PostMapping(value = "send", params = "b")
    public String showBView() {
        return "submit/b";
    }
    @PostMapping(value = "send", params = "c")
    public String showCView() {
        return "submit/c";
    }
    
}//eoc
