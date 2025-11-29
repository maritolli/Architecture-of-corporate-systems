package com.example.library.service;

import com.example.library.entity.*;
import com.example.library.repository.BookRepository;
import com.example.library.repository.BorrowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired private BookRepository bookRepository;
    @Autowired private BorrowingRepository borrowingRepository;

    public List<Book> getAllBooks() { return bookRepository.findAll(); }
    public Optional<Book> getBookById(Long id) { return bookRepository.findById(id); }
    public Book saveBook(Book book) { return bookRepository.save(book); }
    public void deleteBook(Long id) { bookRepository.deleteById(id); }

    @Transactional
    public void orderBook(Long readerId, Long bookId) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new RuntimeException("Книга не найдена"));
        if (!"в библиотеке".equals(book.getStatus())) {
            throw new RuntimeException("Книга уже выдана");
        }
        if (borrowingRepository.existsByBook_BookId(bookId)) {
            throw new RuntimeException("Книга уже выдана (дубликат)");
        }

        Borrowing borrowing = new Borrowing();
        borrowing.setBook(book);
        Reader reader = new Reader();
        reader.setReaderId(readerId);
        borrowing.setReader(reader);
        borrowing.setBorrowDate(LocalDate.now());

        borrowingRepository.save(borrowing);
        book.setStatus("на руках");
        bookRepository.save(book);
    }

    @Transactional
    public void returnBook(Long borrowingId) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
            .orElseThrow(() -> new RuntimeException("Выдача не найдена"));
        Book book = borrowing.getBook();
        book.setStatus("в библиотеке");
        bookRepository.save(book);
        borrowingRepository.delete(borrowing);
    }
}