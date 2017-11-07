/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */
package de.qaware.edu.cc.benutzer.service;

import de.qaware.edu.cc.benutzer.dto.BenutzerDTO;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * The benutzer service.
 *
 * Created by simon.baeumler on 14.10.2015.
 */
public class AppService {

    public AppService(String url) {
        this.benutzer = new HashMap<Long, BenutzerDTO>();
        this.benutzer.put(123L, new BenutzerDTO(123L, "Albert Alpha", "Adresse 1"));
        this.benutzer.put(234L, new BenutzerDTO(234L, "Bernd Benutzer", "Adresse 2"));
        this.benutzer.put(345L, new BenutzerDTO(345L, "Charlotte Clever", "Adresse 3"));
    }

    private Map<Long, BenutzerDTO> benutzer;

    /**
     * A service method.
     *
     * @param id the query parameter
     * @return a benutzer dto
     */
    public BenutzerDTO getBenutzerDataContent(long id) {
        return benutzer.get(id);
    }
}
