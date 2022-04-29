package com.MS1.gestionUsers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class GestionUsersApplication {

    public static void main(String[] args) {
        SpringApplication.run(GestionUsersApplication.class, args);
    }

}
