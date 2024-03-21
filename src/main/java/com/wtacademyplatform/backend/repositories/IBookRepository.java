package com.wtacademyplatform.backend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wtacademyplatform.backend.entities.Book;

public interface IBookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findBookByIsbn(String isbn);

    List<Book> findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(String title, String author);
}