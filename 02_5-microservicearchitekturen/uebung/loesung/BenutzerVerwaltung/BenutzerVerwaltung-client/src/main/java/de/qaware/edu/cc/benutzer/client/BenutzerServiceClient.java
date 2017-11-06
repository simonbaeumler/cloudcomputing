/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */
package de.qaware.edu.cc.benutzer.client;

import de.qaware.edu.cc.benutzer.BenutzerService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * The rest client interface.
 *
 * Created by christoph.schauer on 26.10.2015.
 */

@FeignClient(url = "${services.BenutzerVerwaltung}")
public interface BenutzerServiceClient extends BenutzerService {
    //empty
}
