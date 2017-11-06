/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */
package de.qaware.edu.cc.benutzer.rest.controller.v0;

import de.qaware.edu.cc.benutzer.dto.BenutzerDTO;
import de.qaware.edu.cc.benutzer.service.AppService;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * The benutzer controller implementation.
 * <p>
 * Created by simon.baeumler on 11.10.2015.
 */
@Api(value = "/", description = "All benutzer services")
@RestController
public class BenutzerController implements de.qaware.edu.cc.benutzer.BenutzerService {

    private static final Logger LOG = LoggerFactory.getLogger(BenutzerController.class);

    private AppService myService;

    /**
     * Creates the controller with given service.
     *
     * @param myService the service
     */
    @Autowired
    public BenutzerController(AppService myService) {
        this.myService = myService;
    }

    public BenutzerDTO getBenutzerValue(@RequestParam(value = "id") long id) {

        LOG.info("Call getBenutzerValue: {}", id);
        return myService.getBenutzerDataContent(id);
    }
}
