/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */
package de.qaware.edu.cc.benutzer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * A benutzer dto.
 * <p>
 * Created by simon.baeumler on 11.10.2015.
 */
@ApiModel(description = "A benutzer element")
public class BenutzerDTO {

    @JsonProperty(value = "id")
    @ApiModelProperty(value = "The benutzer uid", required = true)
    private long id;

    @JsonProperty(value = "name")
    @ApiModelProperty(value = "The benutzer content", required = true)
    private String name;

    @JsonProperty(value = "adresse")
    @ApiModelProperty(value = "The someValue field", required = false)
    private String adresse;

    /**
     * Value constructor
     *
     * @param id     the uid
     * @param name the content
     * @param adresse     the url
     */
    public BenutzerDTO(long id, String name, String adresse) {
        this.id = id;
        this.name = name;
        this.adresse = adresse;
    }

    /**
     * Default constructor
     */
    public BenutzerDTO() {
        // default constructor
    }

    /**
     * Return the uid.
     *
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * Returns the content.
     *
     * @return the content
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the url.
     *
     * @return the ulr
     */
    public String getAdresse() {
        return adresse;
    }
}
