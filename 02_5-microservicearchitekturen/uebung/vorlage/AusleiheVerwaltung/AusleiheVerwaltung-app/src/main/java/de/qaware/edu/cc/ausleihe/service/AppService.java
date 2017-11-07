/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */
package de.qaware.edu.cc.ausleihe.service;

import de.qaware.edu.cc.ausleihe.datamodel.Ausleihe;
import de.qaware.edu.cc.ausleihe.dto.AusleiheDTO;

import java.util.HashMap;
import java.util.Map;

/**
 * The benutzer service.
 *
 * Created by simon.baeumler on 14.10.2015.
 */
public class AppService {

    private Map<Long, Ausleihe> ausleihen;

    public AppService() {
        this.ausleihen = new HashMap<Long, Ausleihe>();
        this.ausleihen.put(111L, new Ausleihe(111L, 123L, "1234"));
        this.ausleihen.put(222L, new Ausleihe(222L, 234L, "1234"));
        this.ausleihen.put(333L, new Ausleihe(333L, 345L, "4567"));
    }

    /**
     * A service method.
     *
     * @param name the query parameter
     * @return a benutzer dto
     */
    public AusleiheDTO getAusleiheDataContent(long id) {

        Ausleihe ausleihe = ausleihen.get(id);

        // TODO get Benutzer from Benutzerverwaltung

        // TODO get Book from Bookshelf

        // TODO add data from fetched Benutzer and Book
        AusleiheDTO ausleiheDTO = new AusleiheDTO(id, null, null, null);
        return ausleiheDTO;
    }
}
