package com.company.disample01;
/* 스프링 부트 시작 클래스 */
import com.company.disample01.used.Greet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DiSample01Application {
    public static void main(String[] args) {
        SpringApplication.run(DiSample01Application.class, args)
                .getBean(DiSample01Application.class).execute();
    }
    // 사용하는 쪽
    @Autowired
    private Greet g;
    /* 실행 */
    public void execute(){
        String msg = g.greenting();
        System.out.println(msg);
    }

}//eoc
