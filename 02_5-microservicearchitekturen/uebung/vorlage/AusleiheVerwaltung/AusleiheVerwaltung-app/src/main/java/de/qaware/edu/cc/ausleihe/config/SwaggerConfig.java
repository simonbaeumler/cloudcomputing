/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */

package de.qaware.edu.cc.ausleihe.config;

import com.google.common.base.Predicate;
import springfox.documentation.service.Contact;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * Spring configuration for swagger documentation.
 * <p>
 * Created by christoph.schauer on 16.11.2015.
 */
@Configuration
@EnableSwagger2
@Profile({"development", "test", "itest"})
public class SwaggerConfig {


    /**
     * Provides the swagger configuration.
     *
     * @return Swagger configuration
     */
    @Bean
    public Docket swaggerSpringMvcPlugin(
            @Value("${AusleiheVerwaltung.swagger.title}") String title,
            @Value("${AusleiheVerwaltung.swagger.version}") String version,
            @Value("${AusleiheVerwaltung.swagger.description}") String description,
            @Value("${AusleiheVerwaltung.swagger.contact}") String contact) {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(paths())
                .build()
                .apiInfo(apiInfo(title, version, description, contact));
    }

    /**
     * Creates the API info.
     *
     * @param title       The title.
     * @param version     The version.
     * @param description The description.
     * @param contactName The contact name.
     * @return API info
     */
    private ApiInfo apiInfo(String title, String version, String description, String contactName) {
        return new ApiInfoBuilder()
                .title(title)
                .version(version)
                .description(description)
                .contact(new Contact(contactName, "", ""))
                .build();
    }

    /**
     * Creates the path configuration.
     *
     * @return path configuration
     */
    private Predicate<String> paths() {
        return regex("/AusleiheVerwaltung/.*");
    }
}
