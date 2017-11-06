/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */
package de.qaware.edu.cc.benutzer.client;

import de.qaware.edu.cc.benutzer.DummyService;
import de.qaware.edu.cc.benutzer.dto.DummyDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * The rest client interface.
 *
 * Created by christoph.schauer on 26.10.2015.
 */

@FeignClient(url = "${services.BenutzerVerwaltung}")
public interface DummyServiceClient extends DummyService {
    //empty
}
