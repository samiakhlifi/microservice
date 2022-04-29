package com.descovery.projectdiscovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class ProjectdiscoveryApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectdiscoveryApplication.class, args);
    }

}
