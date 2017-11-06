/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */
package de.qaware.edu.cc.benutzer.rest.controller.v0;

import de.qaware.edu.cc.benutzer.DummyService;
import de.qaware.edu.cc.benutzer.dto.DummyDTO;
import de.qaware.edu.cc.benutzer.service.MyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
/**
 * The dummy controller implementation.
 * <p>
 * Created by simon.baeumler on 11.10.2015.
 */
@Api(value = "/", description = "All dummy services")
@RestController
public class DummyController implements DummyService {

    private static final Logger LOG = LoggerFactory.getLogger(DummyController.class);

    private MyService myService;

    /**
     * Creates the controller with given service.
     *
     * @param myService the service
     */
    @Autowired
    public DummyController(MyService myService) {
        this.myService = myService;
    }

    @Override
    public DummyDTO getDummyValue(@RequestParam(value = "name") String name) {

        LOG.debug("Environment info: {}", myService.getUrl());
        return myService.getDummyDataContent(name);
    }
}
