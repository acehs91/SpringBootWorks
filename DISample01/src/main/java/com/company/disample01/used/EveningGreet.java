package com.company.disample01.used;

import org.springframework.stereotype.Component;

//@Component
public class EveningGreet implements Greet {
    @Override
    public String greenting() {
        return "굿나잇~~";
    }
}
