package com.example.library.controller.rest;

import com.example.library.entity.Author;
import com.example.library.service.AuthorService;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {
     @Autowired
    private AuthorService authorService;

    // JSON-версия 
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Author> getAllAuthorsJson() {
        return authorService.getAllAuthors();
    }

    // XML-версия с XSL
    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<String> getAllAuthorsXml() throws Exception {
        List<Author> authors = authorService.getAllAuthors();
        XmlMapper xmlMapper = new XmlMapper();
        String xmlBody = xmlMapper.writeValueAsString(authors);
        String fullXml = 
            "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
            "<?xml-stylesheet type=\"text/xsl\" href=\"/xsl/library.xsl\"?>\n" +
            xmlBody;
        return ResponseEntity.ok()
            .header("Content-Type", "application/xml; charset=UTF-8")
            .body(fullXml);
    }

    @PostMapping(consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    @ResponseStatus(HttpStatus.CREATED)
    public Author createAuthor(@RequestBody Author author) {
        return authorService.saveAuthor(author);
    }

    @PutMapping(value = "/{id}", consumes = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
    public Author updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        author.setAuthorId(id);
        return authorService.saveAuthor(author);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAuthor(@PathVariable Long id) {
        authorService.deleteAuthor(id);
    }
}