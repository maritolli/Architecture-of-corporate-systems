package com.example.library.controller.rest;

import com.example.library.entity.Borrowing;
import com.example.library.service.BorrowingService;
import com.example.library.service.BookService;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrowings")
public class BorrowingRestController {

    @Autowired
    private BorrowingService borrowingService;

    @Autowired
    private BookService bookService;

    // JSON-версия 
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Borrowing> getAllBorrowingsJson() {
        return borrowingService.getAllBorrowings();
    }

    // XML-версия с XSL
    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getAllBorrowingsXml() throws Exception {
        List<Borrowing> borrowings = borrowingService.getAllBorrowings();
        
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new JavaTimeModule());
        xmlMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        
        String xmlBody = xmlMapper.writeValueAsString(borrowings);
        String fullXml =
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<?xml-stylesheet type=\"text/xsl\" href=\"/xsl/library.xsl\"?>\n" +
            xmlBody;
        return ResponseEntity.ok()
            .header("Content-Type", "application/xml; charset=UTF-8")
            .body(fullXml);
    }

    // Получить выдачу по ID
    @GetMapping(value = "/{id}", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Borrowing getBorrowingById(@PathVariable Long id) {
        return borrowingService.getBorrowingById(id);
    }

    // Заказать книгу
    @PostMapping("/order")
    public String orderBook(@RequestParam Long readerId, @RequestParam Long bookId) {
        bookService.orderBook(readerId, bookId);
        return "Книга успешно заказана";
    }

    // Вернуть книгу
    @DeleteMapping("/return/{borrowingId}")
    public String returnBook(@PathVariable Long borrowingId) {
        bookService.returnBook(borrowingId);
        return "Книга успешно возвращена";
    }

    // Удалить выдачу (альтернатива)
    @DeleteMapping("/{id}")
    public void deleteBorrowing(@PathVariable Long id) {
        bookService.returnBook(id);
    }
}