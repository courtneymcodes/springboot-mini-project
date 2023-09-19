package com.example.springbootminiproject.controller;

import com.example.springbootminiproject.model.Genre;
import com.example.springbootminiproject.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class GenreController {
    private GenreService genreService;
    @Autowired
    public void setGenreService(GenreService genreService) {
        this.genreService = genreService;
    }

    /**
     * Returns the result of calling the getGenres method on the GenreService class when a GET request is made to /genres endpoint
     * @return a list of all genres in the database
     */
    @GetMapping(path = "/genres/")
    public List<Genre> getGenres(){
        return genreService.getGenres();
    }
}
