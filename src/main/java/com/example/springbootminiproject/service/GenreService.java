package com.example.springbootminiproject.service;

import com.example.springbootminiproject.exception.InformationExistsException;
import com.example.springbootminiproject.exception.InformationNotFoundException;
import com.example.springbootminiproject.model.Book;
import com.example.springbootminiproject.model.Genre;
import com.example.springbootminiproject.model.User;
import com.example.springbootminiproject.repository.BookRepository;
import com.example.springbootminiproject.repository.GenreRepository;
import com.example.springbootminiproject.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class GenreService {
    private GenreRepository genreRepository;
    private BookRepository bookRepository;
    @Autowired
    public void setGenreRepository(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
    }
    @Autowired
    public void setBookRepository(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Gets the data for the user that is currently logged in
     * @return a user object
     */
    public static User getCurrentLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        return userDetails.getUser();
    }

    /**
     * Gets all genres associated with logged in user from the database when a GET request is made to /genres endpoint
     * @return a list of all genres in the database
     */
    public List<Genre> getGenres() {
        List<Genre> genreList = genreRepository.findByUserId(GenreService.getCurrentLoggedInUser().getId());  //get id of logged in user
        if (genreList.isEmpty()) {
            throw new InformationNotFoundException("no categories found for user id " + GenreService.getCurrentLoggedInUser().getId());
        } else {
            return genreList;
        }
    }

    /**
     *Saves a genre object to the database when a POST request is made to /genres endpoint. Sets the user to logged in user
     * @param genreObject from request body
     * @return category object to save to the database
     * @throws InformationExistsException if a category with same id already exists in the database
     */
    public Genre createGenre(Genre genreObject){
        Genre genre = genreRepository.findByUserIdAndName(GenreService.getCurrentLoggedInUser().getId(), genreObject.getName());
        if(genre == null){
            genreObject.setUser(GenreService.getCurrentLoggedInUser());
            return genreRepository.save(genreObject);
        } else {
            throw new InformationExistsException("Genre with name " + genreObject.getName() + " already exists");
        }
    }

    /**
     * Gets a genre object by its id that is associated with logged in user when a GET request is made to /genres/{genreId}
     * @param genreId path variable
     * @return a genre object
     * @throws InformationNotFoundException no genre matching given id can be found in database
     */
    public Genre getGenre(Long genreId){
        Genre genre = genreRepository.findByIdAndUserId(genreId, getCurrentLoggedInUser().getId());
        if(genre != null){
            return genre;
        } else {
            throw new InformationNotFoundException("Genre with id " + genreId + " not found");
        }
    }

    /**
     * Updates an existing genre in the database belonging to logged in user
     * @param genreId a path variable of type Long
     * @param genreObject a genre object
     * @return a genre object
     * @throws InformationExistsException if data being updated already exists in database
     * @throws InformationNotFoundException if genre with given id does not exist in database
     */
    public Genre updateGenre(Long genreId, Genre genreObject) {
        Genre genre = genreRepository.findByIdAndUserId(genreId, GenreService.getCurrentLoggedInUser().getId());
        if (genre != null) {
            if (genre.getName().equals(genreObject.getName()) && genre.getDescription().equals(genreObject.getDescription())) {
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

    /**
     * Deletes a genre from the database
     * @param genreId a number type Long
     * @return a Genre object
     * @throws InformationNotFoundException if genre does not exist in database
     */
    public Genre deleteGenre(Long genreId){
        Optional<Genre> genreOptional = genreRepository.findById(genreId);
        if(genreOptional.isPresent()){
            genreRepository.deleteById(genreId);
            return genreOptional.get();
        }else {
            throw new InformationNotFoundException("Genre with id " + genreId + " not found");
        }
    }

    /**
     * Gets a list of books associated with a genre. Finds a genre by its unique id and retrieves its bookList field
     * @param genreId a unique number type Long used to
     * @return a list of type Book
     * throws InformationNotFoundException if genre does not exists in the database
     */
    public List<Book> getGenreBooks(Long genreId){
        Optional<Genre> genreOptional = genreRepository.findById(genreId);
        if(genreOptional.isPresent()) {
            return genreOptional.get().getBookList();
        }else {
            throw new InformationNotFoundException("Genre with id "+ genreId + " not found");
        }
    }

    /**
     * Saves a Genre object to the database and associates it with a genre
     * @param genreId a Long
     * @param bookObject a book object from request body
     * @return a book object to save to the database
     * @throws InformationNotFoundException attempt of saving to database fails
     */
    public Book createGenreBook(Long genreId, Book bookObject){
        try {
            Genre genre = genreRepository.findById(genreId).get();
            bookObject.setGenre(genre);
            return bookRepository.save(bookObject);
        }catch (NoSuchElementException e) {
            throw new InformationNotFoundException("Genre with id " + genreId + " not found");
        }
    }

}
