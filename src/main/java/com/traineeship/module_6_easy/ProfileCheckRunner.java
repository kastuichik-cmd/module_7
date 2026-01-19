package com.traineeship.module_6_easy;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ProfileCheckRunner {

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    @PostConstruct
    public void printConfig() {
        System.out.println("=========================================");
        System.out.println("APP STARTED WITH PROFILE: " + activeProfile);
        System.out.println("СONNECTED TO DB: " + dbUrl);
        System.out.println("=========================================");
    }
}
