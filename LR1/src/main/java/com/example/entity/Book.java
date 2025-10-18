package com.example.entity;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Book")
@NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    private Long bookId;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    @JsonbTransient
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Borrowing> borrowings;

    @Column(nullable = false)
    private String status = "в библиотеке";

    // Конструкторы
    public Book() {}

    public Book(String title, Author author) {
        this.title = title;
        this.author = author;
    }

    // Геттеры и сеттеры
    public Long getBookId() { return bookId; }
    public void setBookId(Long bookId) { this.bookId = bookId; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public Author getAuthor() { return author; }
    public void setAuthor(Author author) { this.author = author; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public List<Borrowing> getBorrowings() { return borrowings; }
    public void setBorrowings(List<Borrowing> borrowings) { this.borrowings = borrowings; }
}