/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */
package de.qaware.edu.cc.benutzer.service;

import de.qaware.edu.cc.benutzer.dto.DummyDTO;

import java.util.concurrent.atomic.AtomicLong;

/**
 * The dummy service.
 *
 * Created by simon.baeumler on 14.10.2015.
 */
public class MyService {

    private final AtomicLong uidCounter = new AtomicLong();

    private final String url;

    public MyService(String url) {
        this.url = url;
    }

    /**
     * A service method.
     *
     * @param name the query parameter
     * @return a dummy dto
     */
    public DummyDTO getDummyDataContent(String name) {
        return new DummyDTO(uidCounter.incrementAndGet(), name, url);
    }

    public String getUrl() {
        return url;
    }
}
