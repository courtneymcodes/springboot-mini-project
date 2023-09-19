package com.example.springbootminiproject.model;

public class Genre {
    private Long id;
    private String name;
    private String description;

    public Genre() {
    }

    public Genre(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
