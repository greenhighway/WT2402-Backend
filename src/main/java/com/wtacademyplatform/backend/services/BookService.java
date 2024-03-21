package com.wtacademyplatform.backend.services;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.wtacademyplatform.backend.dto.BookDto;
import com.wtacademyplatform.backend.dto.ResponseDto;
import com.wtacademyplatform.backend.dto.SaveBookDto;
import com.wtacademyplatform.backend.entities.Book;
import com.wtacademyplatform.backend.repositories.IBookRepository;

@Service
public class BookService {

    private final IBookRepository repo;

    public BookService(IBookRepository repo) {
        this.repo = repo;
    }

    public List<BookDto> findAllBooks(String searchTerm) {

        if (!searchTerm.isEmpty()) {
            return this.repo.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(searchTerm, searchTerm).stream().map(Book::ToDto).collect(Collectors.toList());
        }

        List<Book> books = this.repo.findAll();

        return books.stream().map(Book::ToDto).collect(Collectors.toList());
    }

    public Optional<Book> findBookById(long id) {
        return this.repo.findById(id);
    }

    public Optional<Book> findBookByISBN(String isbn) {
       return this.repo.findBookByIsbn(isbn);
    }

    public ResponseDto create(SaveBookDto saveBookDto) {
        Optional<Book> existingBook = this.findBookByISBN(saveBookDto.getIsbn());
        if (existingBook.isPresent()) {
            return new ResponseDto(false, "Book met gegeven ISBN bestaat al.");
        }

        Book book = new Book();
        book.setIsbn(saveBookDto.getIsbn());
        book.setTitle(saveBookDto.getTitle());
        book.setAuthor(saveBookDto.getAuthor());
        book.setAvailable(saveBookDto.isAvailable());
        book.setPublicationYear(saveBookDto.getPublicationYear());

        return new ResponseDto(true, this.repo.save(book).ToDto());
    }

    public ResponseDto update(long id, SaveBookDto saveBookDto) {
        Optional<Book> bookOptional = this.findBookById(id);

        if (bookOptional.isEmpty()) {
            return new ResponseDto(false, null);
        }

        Book existingBook = bookOptional.get();

        Optional<Book> foundBook = this.findBookByISBN(saveBookDto.getIsbn());

        // check if the isbn the user is going to change it to does not exist.
        if (foundBook.isPresent() && foundBook.get().getId() != id) {
            return new ResponseDto(false, "Boek met gegeven ISBN bestaat al.");
        }

        existingBook.setIsbn(saveBookDto.getIsbn());
        existingBook.setTitle(saveBookDto.getTitle());
        existingBook.setAuthor(saveBookDto.getAuthor());
        existingBook.setAvailable(saveBookDto.isAvailable());
        existingBook.setPublicationYear(saveBookDto.getPublicationYear());

        return new ResponseDto(true, this.repo.save(existingBook).ToDto());
    }

    public ResponseDto delete(long id) {
        Optional<Book> bookOptional = this.findBookById(id);

        if (bookOptional.isEmpty()) {
            return new ResponseDto(false, null);
        }

        // check if book has items
        Book book = bookOptional.get();
        int itemsInBook = book.getItems().size();

        if (itemsInBook > 0) {
            return new ResponseDto(false, "Dit boek heeft één of meerdere exampleren, verwijder eerst de exemplaren.");
        }

        this.repo.deleteById(id);

        return new ResponseDto(true, null);
    }
}