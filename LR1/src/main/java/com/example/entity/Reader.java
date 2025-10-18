package com.example.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Reader")
@NamedQuery(name = "Reader.findAll", query = "SELECT r FROM Reader r")
public class Reader {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reader_id")
    private Long readerId;

    @Column(nullable = false)
    private String name;

    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;

    @OneToMany(mappedBy = "reader", cascade = CascadeType.ALL)
    @Column(name = "borrowing_id")
    private List<Borrowing> borrowings;

    // Конструкторы
    public Reader() {}

    public Reader(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    // Геттеры и сеттеры
    public Long getReaderId() { return readerId; }
    public void setReaderId(Long readerId) { this.readerId = readerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public List<Borrowing> getBorrowings() { return borrowings; }
    public void setBorrowings(List<Borrowing> borrowings) { this.borrowings = borrowings; }
}