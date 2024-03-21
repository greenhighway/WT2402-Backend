package com.wtacademyplatform.backend.entities;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wtacademyplatform.backend.dto.BookDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Book {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length=13, unique = true)
    private String isbn;

    @Column(nullable = false, length=50)
    private String title;

    @Column(nullable = false, length=30)
    private String author;

    @Column(nullable = false)
    private boolean available;

    @Column(nullable = false)
    private int publicationYear;

    @Column()
    @CreationTimestamp
    private Instant createdAt;

    @Column()
    @UpdateTimestamp
    private Instant updatedAt;
    
    @JsonIgnore
    @OneToMany(mappedBy = "book")
    private List<Item> items = new ArrayList<>();

    public BookDto ToDto(){
        return new BookDto(id,isbn,title,author,available,publicationYear,items.size());
    }
}