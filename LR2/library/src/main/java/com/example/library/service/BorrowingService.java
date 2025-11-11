package com.example.library.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.library.repository.BorrowingRepository;
import com.example.library.entity.Borrowing;

import java.util.List;

@Service
public class BorrowingService {
    @Autowired private BorrowingRepository borrowingRepository;

    public List<Borrowing> getAllBorrowings() {
        return borrowingRepository.findAll();
    }
}