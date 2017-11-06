/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */
package de.qaware.edu.cc.benutzer;

import de.qaware.edu.cc.benutzer.service.AppService;
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
public class BenutzerTestConfig {

    @Bean
    public AppService benutzerService(@Value("${BenutzerVerwaltung.url}") String url) {
        return new AppService(url);
    }

}
