package com.example.JavaConfigSampleApplication;
/* 실행 클래스 */
import com.example.JavaConfigSampleApplication.service.BusinessLogic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaConfigSampleApplication {

	public static void main(String[] args) {
        SpringApplication.run(JavaConfigSampleApplication.class, args)
                .getBean(JavaConfigSampleApplication.class).exe();
	}
    @Autowired
    @Qualifier("test") // 이 이름의 Bean을 사용해라 => @Bean(name="test")과 매칭 => 같은 구현체여도 충돌 없음 
    private BusinessLogic business1;

    @Autowired
    @Qualifier("sample")
    private BusinessLogic business2;

    /* 실행 */
    private void exe() {
        business1.doLogic();
        business2.doLogic();
    }

}
