/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */
package de.qaware.edu.cc.ausleihe.client;

import de.qaware.edu.cc.ausleihe.AusleiheService;
import org.springframework.cloud.netflix.feign.FeignClient;

/**
 * The rest client interface.
 *
 * Created by christoph.schauer on 26.10.2015.
 */

@FeignClient(name = "ausleiheverwaltung", url = "${services.AusleiheVerwaltung}")
public interface AusleiheServiceClient extends AusleiheService {
    //empty
}
