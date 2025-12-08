package com.example.library.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.library.repository.ReaderRepository;
import com.example.library.entity.Reader;

import java.util.List;

@Service
public class ReaderService {
    @Autowired private ReaderRepository readerRepository;
    public List<Reader> getAllReaders() { return readerRepository.findAll(); }
}