package com.example.library.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.library.repository.AuthorRepository;
import com.example.library.entity.Author;

import java.util.List;

@Service
public class AuthorService {
    @Autowired private AuthorRepository authorRepository;
    public List<Author> getAllAuthors() { return authorRepository.findAll(); }
}