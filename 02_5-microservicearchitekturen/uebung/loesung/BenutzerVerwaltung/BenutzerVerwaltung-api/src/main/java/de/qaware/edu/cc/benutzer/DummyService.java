/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */
package de.qaware.edu.cc.benutzer;

import de.qaware.edu.cc.benutzer.dto.DummyDTO;
import org.springframework.http.MediaType;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Functional interface of dummy service.
 * <p>
 * Created by christoph.schauer on 26.10.2015.
 */
public interface DummyService {

    /*
     * Define the path for dummy values
     */
    String SERVICE_BASE = "/BenutzerVerwaltung/v0";

    /*
     * Define the path for dummy values
     */
    String VALUE_PATH = SERVICE_BASE + "/dummyservice";

    /**
     * Takes the name value and returns a dummy DTO. See {@link DummyDTO}.
     *
     * @param name the name
     * @return dummy
     */
    @ApiOperation(value = "Returns the dummy object")
    @RequestMapping(value = VALUE_PATH,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    DummyDTO getDummyValue(@RequestParam(value = "name") String name);
}
