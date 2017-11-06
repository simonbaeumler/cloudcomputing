/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */
package de.qaware.edu.cc.benutzer;

import de.qaware.edu.cc.benutzer.dto.BenutzerDTO;
import org.springframework.http.MediaType;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Functional interface of benutzer service.
 * <p>
 * Created by christoph.schauer on 26.10.2015.
 */
public interface BenutzerService {

    /*
     * Define the path for benutzer values
     */
    String SERVICE_BASE = "/BenutzerVerwaltung/v0";

    /*
     * Define the path for benutzer values
     */
    String VALUE_PATH = SERVICE_BASE + "/benutzerservice";

    /**
     * Takes the name value and returns a benutzer DTO. See {@link BenutzerDTO}.
     *
     * @param id the name
     * @return benutzer
     */
    @ApiOperation(value = "Returns the benutzer object")
    @RequestMapping(value = VALUE_PATH,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    BenutzerDTO getBenutzerValue(@RequestParam(value = "id") long id);
}
