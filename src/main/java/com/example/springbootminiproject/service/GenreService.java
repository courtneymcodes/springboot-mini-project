package com.example.springbootminiproject.service;

import com.example.springbootminiproject.exception.InformationExistsException;
import com.example.springbootminiproject.model.Genre;
import com.example.springbootminiproject.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    /**
     *Saves an genre object to the database when a POST request is made to /genres endpoint
     * @param genreObject from request body
     * @return category object to save to the database
     * @throws InformationExistsException if a category with same id already exists in the database
     */
    public Genre createGenre(Genre genreObject){
        Optional<Genre> genreOptional = genreRepository.findById(genreObject.getId());
        if(genreOptional.isEmpty()){
            return genreRepository.save(genreObject);
        } else {
            throw new InformationExistsException("Genre with name " + genreObject.getName() + " already exists");
        }
    }

}
