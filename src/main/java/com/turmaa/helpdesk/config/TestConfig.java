package com.turmaa.helpdesk.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.turmaa.helpdesk.services.DBservice;

@Configuration
@Profile("test")
public class TestConfig {

    @Autowired
    private DBservice dbService;

    @Bean
    public void instanciaDB() {
        this.dbService.instanciaDB();
    }
}