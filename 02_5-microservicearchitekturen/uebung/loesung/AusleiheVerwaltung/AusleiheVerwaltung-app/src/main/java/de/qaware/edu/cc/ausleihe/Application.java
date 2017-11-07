/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */
package de.qaware.edu.cc.ausleihe;

import de.qaware.edu.cc.ausleihe.client.BookshelfClient;
import de.qaware.edu.cc.benutzer.client.BenutzerServiceClient;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;

/**
 * The benutzer application.
 * <p>
 * Created by simon.baeumler on 11.10.2015.
 */
@SpringBootApplication
@EnableFeignClients(basePackageClasses = {
        BenutzerServiceClient.class,
        BookshelfClient.class})
public class Application {

    /**
     * Start the spring boot application.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(Application.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }
}
