package com.example.springbootminiproject.model;

import javax.persistence.*;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String author;
    @Column
    private String title;
    @Column
    private int publicationDate;
    @Column
    private String summary;
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Genre genre;

    public Book() {
    }

    public Book(Long id, String author, String title, int publicationDate, String summary) {
        this.id = id;
        this.author = author;
        this.title = title;
        this.publicationDate = publicationDate;
        this.summary = summary;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(int publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
