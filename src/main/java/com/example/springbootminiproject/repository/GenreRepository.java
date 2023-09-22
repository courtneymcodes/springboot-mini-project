package com.example.springbootminiproject.repository;

import com.example.springbootminiproject.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**GenreRepository interface extends JpaRepository enabling it to inherit methods used to perform CRUD (Create, Read, Update, Delete) operations on entities of type Genre in the database*/
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
    List<Genre> findByUserId(Long userId);
    Genre findByUserIdAndName(Long UserId, String genreName);
    Genre findByIdAndUserId(Long genreId, Long userId);

}
