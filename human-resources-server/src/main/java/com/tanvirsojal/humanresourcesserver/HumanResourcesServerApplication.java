package com.tanvirsojal.humanresourcesserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class HumanResourcesServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(HumanResourcesServerApplication.class, args);
    }

}
