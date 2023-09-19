package com.example.springbootminiproject.service;

import com.example.springbootminiproject.model.Genre;
import com.example.springbootminiproject.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    private GenreRepository genreRepository;
    @Autowired
    public void setGenreRepository(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }

    /**
     * Gets all genres from the database when a GET request is made to /genres endpoint
     * @return a list of all genres in the database
     */
    public List<Genre> getGenres() {
        return genreRepository.findAll();
    }

}
