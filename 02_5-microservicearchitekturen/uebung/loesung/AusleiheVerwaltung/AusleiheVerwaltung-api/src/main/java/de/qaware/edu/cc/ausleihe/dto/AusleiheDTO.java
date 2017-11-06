/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */
package de.qaware.edu.cc.ausleihe.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * A benutzer dto.
 * <p>
 * Created by simon.baeumler on 11.10.2015.
 */
@ApiModel(description = "A benutzer element")
public class AusleiheDTO {

    @JsonProperty(value = "id")
    @ApiModelProperty(value = "The benutzer uid", required = true)
    private long id;

    @JsonProperty(value = "benutzerName")
    @ApiModelProperty(value = "The benutzer content", required = true)
    private String benutzerName;

    @JsonProperty(value = "titel")
    @ApiModelProperty(value = "The titel field", required = false)
    private String buchtitel;

    @JsonProperty(value = "autor")
    @ApiModelProperty(value = "The author field", required = false)
    private String autor;

    /**
     * Value constructor
     *
     * @param id     the uid
     * @param benutzerName the content
     * @param buchtitel     the url
     */
    public AusleiheDTO(long id, String benutzerName, String buchtitel, String autor) {
        this.id = id;
        this.benutzerName = benutzerName;
        this.buchtitel = buchtitel;
        this.autor = autor;
    }

    /**
     * Default constructor
     */
    public AusleiheDTO() {
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
    public String getBenutzerName() {
        return benutzerName;
    }

    /**
     * Returns the url.
     *
     * @return the ulr
     */
    public String getBuchtitel() {
        return buchtitel;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
}
