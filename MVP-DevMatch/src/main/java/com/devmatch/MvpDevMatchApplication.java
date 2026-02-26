package com.devmatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MVP-DevMatch 메인 애플리케이션
 * 개발자 실력평가 및 채용 매칭 플랫폼
 */
@SpringBootApplication
public class MvpDevMatchApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(MvpDevMatchApplication.class, args);
        System.out.println("========================================");
        System.out.println("  MVP-DevMatch 서버 시작!");
        System.out.println("  http://localhost:8080");
        System.out.println("========================================");
    }
}
