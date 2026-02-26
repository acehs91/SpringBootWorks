package com.example.JavaConfigSampleApplication.config;

import com.example.JavaConfigSampleApplication.service.BusinessLogic;
import com.example.JavaConfigSampleApplication.service.impl.SampleLogicImpl;
import com.example.JavaConfigSampleApplication.service.impl.TestLogicImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/* 해당 클래스가 설정 클래스임을 나타냄 */
@Configuration
public class AppConfig {
    /* 메서드에 추가하여 해당 메서드가 빈을 반환한다는 것을 의미 = 스프링 컨테이너에 의해 관리 */
    @Bean(name = "test") // 반환 객체를 Bean 이름을 test로 해서 컨테이너에 등록 | @Qualifier("test")와 1:1 매칭
    public BusinessLogic dataLogic() {
        return new TestLogicImpl();
    }

    @Bean(name = "sample")
    public BusinessLogic viewLogic(){
        return new SampleLogicImpl();
    }

}
