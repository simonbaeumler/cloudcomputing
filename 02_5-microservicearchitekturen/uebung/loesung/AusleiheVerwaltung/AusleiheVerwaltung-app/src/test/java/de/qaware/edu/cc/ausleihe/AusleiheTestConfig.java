/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */
package de.qaware.edu.cc.ausleihe;

import de.qaware.edu.cc.ausleihe.service.AppService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * The test configuration.
 */
@Configuration
@ConfigurationProperties
@ComponentScan
public class AusleiheTestConfig {

    @Bean
    public AppService benutzerService() {
        return new AppService();
    }

}
