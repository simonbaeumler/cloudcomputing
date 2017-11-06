/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */

package de.qaware.edu.cc.ausleihe.datamodel;

/**
 * Created by simon.baeumler on 06.11.2017.
 */
public class Ausleihe {

    long id;

    long benutzerId;

    long buchId;

    public Ausleihe(long id, long benutzerId, long buchId) {
        this.id = id;
        this.benutzerId = benutzerId;
        this.buchId = buchId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getBenutzerId() {
        return benutzerId;
    }

    public void setBenutzerId(long benutzerId) {
        this.benutzerId = benutzerId;
    }

    public long getBuchId() {
        return buchId;
    }

    public void setBuchId(long buchId) {
        this.buchId = buchId;
    }
}
