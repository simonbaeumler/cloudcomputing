/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */

package de.qaware.edu.cc.ausleihe.client;

import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Feign client for the Bookshelf Service
 *
 * Created by simon.baeumler on 07.11.2017.
 */
@FeignClient(name = "bookshelf", url = "${services.Bookshelf}")
public interface BookshelfClient {

    /*
     * Define the path for benutzer values
     */
    String VALUE_PATH = "/api/api/books/{isbn}";


    /**
     * Takes the isbn value and returns a book from the bookshelf.
     *
     * @param isbn the isbn number of the book
     * @return the found book
     */
    @ApiOperation(value = "Returns the book object")
    @RequestMapping(value = VALUE_PATH,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    Book byIsbn(@PathVariable("isbn") String isbn);
}
