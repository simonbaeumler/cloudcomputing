/*
 * Copyright 2015, Deutsche Telekom AG. All rights reserved.
 */

package de.qaware.edu.cc.ausleihe.client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by simon.baeumler on 16.10.2017.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {
    private String title;
    private String author;
    private String isbn;

    public Book() {
    }

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    // getter and setter
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
}



