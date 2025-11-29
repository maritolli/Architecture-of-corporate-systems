package com.example.library.service;

import org.springframework.stereotype.Service;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.library.repository.BorrowingRepository;
import com.example.library.entity.Borrowing;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class BorrowingService {
    @Autowired private BorrowingRepository borrowingRepository;

    @Transactional(readOnly = true)
    public List<Borrowing> getAllBorrowings() {
        List<Borrowing> borrowings = borrowingRepository.findAllWithDetails();
        for (Borrowing b : borrowings) {
            Hibernate.initialize(b.getBook().getAuthor());
            Hibernate.initialize(b.getReader());
        }
        return borrowings;
    }
    public Borrowing getBorrowingById(Long id) {
        return borrowingRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Выдача не найдена"));
    }
}