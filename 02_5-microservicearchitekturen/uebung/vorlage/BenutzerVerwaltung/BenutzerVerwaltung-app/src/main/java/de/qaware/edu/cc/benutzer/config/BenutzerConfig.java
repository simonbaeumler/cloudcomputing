/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */
package de.qaware.edu.cc.benutzer.config;

import de.qaware.edu.cc.benutzer.service.AppService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class BenutzerConfig {

    @Bean
    public AppService benutzerService(@Value("${BenutzerVerwaltung.url}") String url) {
        return new AppService(url);
    }

}
