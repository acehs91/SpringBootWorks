package com.company.injectionsample03.example.impl;

import com.company.injectionsample03.example.Example;
import com.company.injectionsample03.service.SomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/* 필드 주입 예시 구현 클래스 */
@Component
public class FieldInjectionExample  implements Example {
    @Autowired
    private SomeService someService;

    @Override
    public void run() {
        someService.doService();
    }
}
