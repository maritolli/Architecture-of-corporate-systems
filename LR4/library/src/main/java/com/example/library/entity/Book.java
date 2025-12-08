package com.example.library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @NotNull
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Author author;

    @NotNull
    private String status = "в библиотеке"; // default

    // Геттеры и сеттеры
    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}