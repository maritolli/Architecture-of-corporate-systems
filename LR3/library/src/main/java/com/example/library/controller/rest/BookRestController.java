package com.example.library.controller.rest;

import com.example.library.entity.Book;
import com.example.library.service.BookService;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookRestController {

    @Autowired
    private BookService bookService;

    // JSON
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> getAllBooksJson() {
        return bookService.getAllBooks();
    }

    // XML с XSL
    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getAllBooksXml() throws Exception {
        List<Book> books = bookService.getAllBooks();
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new Hibernate6Module());
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        String xmlBody = xmlMapper.writeValueAsString(books);
        String fullXml =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<?xml-stylesheet type=\"text/xsl\" href=\"/xsl/library.xsl\"?>\n" +
            xmlBody;
        return ResponseEntity.ok()
            .header("Content-Type", "application/xml; charset=UTF-8")
            .body(fullXml);
    }


    // Получить книгу по ID (автоматически поддерживает JSON/XML)
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Book getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
            .orElseThrow(() -> new RuntimeException("Книга не найдена"));
    }

    // Создать книгу
    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Book createBook(@RequestBody Book book) {
        return bookService.saveBook(book);
    }

    // Обновить книгу
    @PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Book updateBook(@PathVariable Long id, @RequestBody Book book) {
        book.setBookId(id);
        return bookService.saveBook(book);
    }

    // Удалить книгу
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
    }
}