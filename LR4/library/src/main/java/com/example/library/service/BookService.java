package com.example.library.service;

import com.example.library.entity.Book;
import com.example.library.entity.Borrowing;
import com.example.library.entity.Reader;
import com.example.library.jms.ChangeEvent;
import com.example.library.jms.ChangeEventSender;
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

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BorrowingRepository borrowingRepository;

    @Autowired
    private ChangeEventSender changeEventSender;

    @Transactional
    public List<Book> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            book.getAuthor().getName();
        }
        return books;
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
        ChangeEvent event = new ChangeEvent(
                "Book",
                "DELETE",
                id,
                null,
                null
        );
        changeEventSender.send(event);
    }

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

        String oldStatusJson = "{\"status\": \"" + book.getStatus() + "\"}";

        Borrowing borrowing = new Borrowing();
        borrowing.setBook(book);
        Reader reader = new Reader();
        reader.setReaderId(readerId);
        borrowing.setReader(reader);
        borrowing.setBorrowDate(LocalDate.now());

        borrowingRepository.save(borrowing);
        book.setStatus("на руках");
        bookRepository.save(book);

        String newStatusJson = "{\"status\": \"" + book.getStatus() + "\"}";

        ChangeEvent event = new ChangeEvent(
                "Book",
                "UPDATE",
                book.getBookId(),
                oldStatusJson,
                newStatusJson
        );
        changeEventSender.send(event);
    }

    @Transactional
    public void returnBook(Long borrowingId) {
        Borrowing borrowing = borrowingRepository.findById(borrowingId)
                .orElseThrow(() -> new RuntimeException("Выдача не найдена"));
        Book book = borrowing.getBook();

        String oldStatusJson = "{\"status\": \"" + book.getStatus() + "\"}";

        book.setStatus("в библиотеке");
        bookRepository.save(book);
        borrowingRepository.delete(borrowing);

        String newStatusJson = "{\"status\": \"" + book.getStatus() + "\"}";

        ChangeEvent event = new ChangeEvent(
                "Book",
                "UPDATE",
                book.getBookId(),
                oldStatusJson,
                newStatusJson
        );
        changeEventSender.send(event);
    }
}
