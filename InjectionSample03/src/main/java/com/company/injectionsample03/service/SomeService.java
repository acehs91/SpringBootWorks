package com.company.injectionsample03.service;
/* DI 사용 될 일반 클래스 */
import org.springframework.stereotype.Component;

@Component
public class SomeService {
    public void doService() {
        System.out.println("의존성 주입된 어떤 서비스");
    }
}
