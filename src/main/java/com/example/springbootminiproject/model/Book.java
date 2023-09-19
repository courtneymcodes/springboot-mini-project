package com.example.springbootminiproject.model;

import java.time.LocalDate;

public class Book {
    private Long id;
    private String author;
    private String title;
    private int publicationDate;
    private String summary;

    public Book() {
    }

    public Book(Long id, String author, String title, int publicationDate, String summary) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.publicationDate = publicationDate;
        this.summary = summary;
    }
}
