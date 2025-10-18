package com.example.entity;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "Borrowing")
@NamedQuery(name = "Borrowing.findAll", query = "SELECT b FROM Borrowing b")
public class Borrowing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrowing_id")
    private Long borrowingId;

    @JsonbTransient
    @ManyToOne
    @JoinColumn(name = "reader_id", nullable = false)
    private Reader reader;

    @JsonbTransient
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    @Column(name = "borrow_date", nullable = false)
    private LocalDate borrowDate; 

    public Borrowing() {
        this.borrowDate = LocalDate.now(); 
    }

    public Borrowing(Reader reader, Book book, LocalDate borrowDate) {
        this.reader = reader;
        this.book = book;
        this.borrowDate = borrowDate;
    }

    // Геттеры и сеттеры
    public Long getBorrowingId() { return borrowingId; }
    public void setBorrowingId(Long borrowingId) { this.borrowingId = borrowingId; }
    public Reader getReader() { return reader; }
    public void setReader(Reader reader) { this.reader = reader; }
    public Book getBook() { return book; }
    public void setBook(Book book) { this.book = book; }
    public LocalDate getBorrowDate() {
        return borrowDate;
    }
    public void setBorrowDate(LocalDate borrowDate) { this.borrowDate = borrowDate; }

    public String getBookTitle() {
        return book != null ? book.getTitle() : "—";
    }

    public String getReaderName() {
        return reader != null ? reader.getName() : "—";
    }
}