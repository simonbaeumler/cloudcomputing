/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */
package de.qaware.edu.cc.ausleihe.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Import;

import de.qaware.edu.cc.ausleihe.service.AppService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class AusleiheConfig {

    @Bean
    public AppService ausleiheService(@Value("${AusleiheVerwaltung.url}") String url) {
        return new AppService(url);
    }

}
