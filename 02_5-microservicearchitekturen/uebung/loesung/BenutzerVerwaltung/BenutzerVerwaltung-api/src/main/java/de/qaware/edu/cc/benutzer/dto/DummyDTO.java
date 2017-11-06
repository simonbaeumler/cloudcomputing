/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */
package de.qaware.edu.cc.benutzer.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * A dummy dto.
 * <p>
 * Created by simon.baeumler on 11.10.2015.
 */
@ApiModel(description = "A dummy element")
public class DummyDTO {

    @JsonProperty(value = "uid")
    @ApiModelProperty(value = "The dummy uid", required = true)
    private long uid;

    @JsonProperty(value = "content")
    @ApiModelProperty(value = "The dummy content", required = true)
    private String content;

    @JsonProperty(value = "someValue")
    @ApiModelProperty(value = "The someValue field", required = false)
    private String url;

    /**
     * Value constructor
     *
     * @param uid     the uid
     * @param content the content
     * @param url     the url
     */
    public DummyDTO(long uid, String content, String url) {
        this.uid = uid;
        this.content = content;
        this.url = url;
    }

    /**
     * Default constructor
     */
    public DummyDTO() {
        // default constructor
    }

    /**
     * Return the uid.
     *
     * @return id
     */
    public long getUid() {
        return uid;
    }

    /**
     * Returns the content.
     *
     * @return the content
     */
    public String getContent() {
        return content;
    }

    /**
     * Returns the url.
     *
     * @return the ulr
     */
    public String getUrl() {
        return url;
    }
}
