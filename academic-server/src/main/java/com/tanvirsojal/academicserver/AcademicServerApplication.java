package com.tanvirsojal.academicserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class AcademicServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AcademicServerApplication.class, args);
    }

}
