package com.wtacademyplatform.backend.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wtacademyplatform.backend.dto.BookDto;
import com.wtacademyplatform.backend.dto.ResponseDto;
import com.wtacademyplatform.backend.dto.SaveBookDto;
import com.wtacademyplatform.backend.entities.Book;
import com.wtacademyplatform.backend.services.BookService;

@RequestMapping("/book")
@RestController
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/all")
    public List<BookDto> findAll(@RequestParam(defaultValue = "") String search) {
        return this.bookService.findAllBooks(search);
    }

    @GetMapping("/{id}")
    public Optional<Book> findById(@PathVariable("id") long id) {
        return this.bookService.findBookById(id);
    }

    @PostMapping("/create")
    public ResponseDto create(@RequestBody SaveBookDto saveBookDto) {
        return this.bookService.create(saveBookDto);
    }

    @PutMapping("/update/{id}")
    public ResponseDto update(@PathVariable("id") long id, @RequestBody SaveBookDto saveBookDto) {
        return this.bookService.update(id, saveBookDto);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseDto delete(@PathVariable("id") long id) {
        return this.bookService.delete(id);
    }
}