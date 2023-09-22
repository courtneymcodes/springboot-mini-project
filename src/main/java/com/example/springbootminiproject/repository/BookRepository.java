package com.example.springbootminiproject.repository;

import com.example.springbootminiproject.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByTitleAndUserId(String itemName, Long userId);
}
