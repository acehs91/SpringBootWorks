package com.company.injectionsample03;

import com.company.injectionsample03.example.Example;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InjectionSample03Application {

    public static void main(String[] args) {
        SpringApplication.run(InjectionSample03Application.class, args)
                .getBean(InjectionSample03Application.class).exe();
    }

    @Autowired
    private Example example;

    private void exe() {
        example.run();
    }

}
