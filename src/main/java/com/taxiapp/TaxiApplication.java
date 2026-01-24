package com.taxiapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TaxiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaxiApplication.class, args);
    }


}
