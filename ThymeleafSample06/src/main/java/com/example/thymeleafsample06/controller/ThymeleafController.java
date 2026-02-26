package com.example.thymeleafsample06.controller;

import com.example.thymeleafsample06.entity.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ThymeleafController {

    @GetMapping("/")
    public String showView(Model model) {
        Member member = new Member(1, "현수");
        Member member2 = new Member(20, "지영");

        // 리스트 생성 (방향)
        List<String> directionList = new ArrayList<>();
        directionList.add("동");
        directionList.add("서");
        directionList.add("남");
        directionList.add("북");
        
        // Map 생성
        Map<String, Member> memberMap = new HashMap<>();
        memberMap.put("minsoo", new Member(25, "민수"));
        memberMap.put("jiyoung", new Member(20, "지영"));
        
        // Member 리스트 생성 (반복용)
        List<Member> members = new ArrayList<>();
        members.add(new Member(1, "현수"));
        members.add(new Member(2, "민수"));
        members.add(new Member(3, "지영"));
        
        model.addAttribute("name", "현수");
        model.addAttribute("member", member);
        model.addAttribute("member2", member2);
        model.addAttribute("directionList", directionList);
        model.addAttribute("memberMap", memberMap);
        model.addAttribute("members", members);
        
        return "main";
    }
}
