package com.plansolve.spring_boot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ServletComponentScan
@EnableCaching
@EnableScheduling
public class SpringBootManageApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootManageApplication.class, args);
    }

    /*MQConsumer testDomainMapper = SpringUtil.getBean(MQConsumer.class);*/

}
