/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */
package de.qaware.edu.cc.benutzer.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Import;

import de.qaware.edu.cc.benutzer.service.MyService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties
public class DummyConfig {

    @Bean
    public MyService dummyService(@Value("${BenutzerVerwaltung.url}") String url) {
        return new MyService(url);
    }

//    /**
//     * Register a filter which set trace-id to log4j2 context, and creates a trace-id if no header field contains it.
//     *
//     * @return instance of the registration bean
//     */
//    @Bean
//    public FilterRegistrationBean traceIdFilter() {
//        return TasteOsFilterFactory.getTraceIdFilter(false, "/BenutzerVerwaltung/*");
//    }
//
//    /**
//     * Register a filter which logs request against /BenutzerVerwaltung/* urls.
//     *
//     * @return Instance of the bean
//     */
//    @Bean
//    public FilterRegistrationBean loggingFilter() {
//        return TasteOsFilterFactory.getLogFilter("/BenutzerVerwaltung/*");
//    }
}
