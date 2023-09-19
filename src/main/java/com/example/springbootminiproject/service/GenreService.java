package com.example.springbootminiproject.service;

import com.example.springbootminiproject.exception.InformationExistsException;
import com.example.springbootminiproject.exception.InformationNotFoundException;
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
     *Saves a genre object to the database when a POST request is made to /genres endpoint
     * @param genreObject from request body
     * @return category object to save to the database
     * @throws InformationExistsException if a category with same id already exists in the database
     */
    public Genre createGenre(Genre genreObject){
        Genre genre = genreRepository.findByName(genreObject.getName());
        if(genre == null){
            return genreRepository.save(genreObject);
        } else {
            throw new InformationExistsException("Genre with name " + genreObject.getName() + " already exists");
        }
    }

    /**
     * Gets a genre object by its id
     * @param genreId path variable
     * @return a genre object
     */
    public Genre getGenre(Long genreId){
        Optional<Genre> genreOptional = genreRepository.findById(genreId);
        if(genreOptional.isPresent()){
            return genreOptional.get();
        } else {
            throw new InformationNotFoundException("Genre with id " + genreId + " not found");
        }
    }

    public Genre updateGenre(Long genreId, Genre genreObject) {
        Optional<Genre> genreOptional = genreRepository.findById(genreId);
        if (genreOptional.isPresent()) {
            if (genreOptional.get().getName().equals(genreObject.getName()) && genreOptional.get().getDescription().equals(genreObject.getDescription())) {
                throw new InformationExistsException("Genre name " + genreObject.getName() + " and description " + genreObject.getDescription() + " already exists");
            } else {
                Genre updateGenre = genreRepository.findById(genreId).get();
                updateGenre.setName(genreObject.getName());
                updateGenre.setDescription(genreObject.getDescription());
                return genreRepository.save(updateGenre);
            }
        } else {
            throw new InformationNotFoundException("Genre with id " + genreId + " not found");
        }
    }
}
