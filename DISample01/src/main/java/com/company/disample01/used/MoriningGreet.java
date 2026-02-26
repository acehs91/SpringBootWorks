package com.company.disample01.used;

import org.springframework.stereotype.Component;

// 주입에 사용되는 -> 제공하는 쪽
@Component
public class MoriningGreet implements Greet {
    @Override
    public String greenting() {
        return "좋은 아침입니다.";
    }
}
