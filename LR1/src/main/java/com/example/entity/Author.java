package com.example.entity;

import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Author") // Соответствует таблице Author в БД
@NamedQueries({
        @NamedQuery(name = "Author.findAll", query = "SELECT a FROM Author a"),
        @NamedQuery(name = "Author.findByCountry", query = "SELECT a FROM Author a WHERE a.country = :country")
})
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Автоинкремент в БД
    @Column(name = "author_id") // Соответствует колонке author_id
    private Long authorId;

    @Column(nullable = false, length = 100) // NOT NULL, max 100 символов
    private String name;

    @Column(nullable = false, length = 50)
    private String country;

    // Связь "один-ко-многим" с книгами
    @JsonbTransient
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Column(name = "book_id")
    private List<Book> books;

    // Конструкторы
    public Author() {} // Обязательный пустой конструктор для JPA

    public Author(String name, String country) {
        this.name = name;
        this.country = country;
    }

    // Геттеры и сеттеры (обязательны для JPA)
    public Long getAuthorId() { return authorId; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public List<Book> getBooks() { return books; }
    public void setBooks(List<Book> books) { this.books = books; }

    // Для удобного вывода в логах
    @Override
    public String toString() {
        return "Author{id=" + authorId + ", name='" + name + "', country='" + country + "'}";
    }
}