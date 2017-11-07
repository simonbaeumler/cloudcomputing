/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */
package de.qaware.edu.cc.ausleihe.rest.controller.v0;

import de.qaware.edu.cc.ausleihe.AusleiheService;
import de.qaware.edu.cc.ausleihe.dto.AusleiheDTO;
import de.qaware.edu.cc.ausleihe.service.AppService;
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
public class AusleiheController implements AusleiheService {

    private static final Logger LOG = LoggerFactory.getLogger(AusleiheController.class);

    private AppService appService;

    /**
     * Creates the controller with given service.
     *
     * @param appService the service
     */
    @Autowired
    public AusleiheController(AppService appService) {
        this.appService = appService;
    }

    public AusleiheDTO getAusleiheValue(@RequestParam(value = "name") long id) {

        LOG.debug("getAusleihe: {}", id);
        return appService.getAusleiheDataContent(id);
    }
}
