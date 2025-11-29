package com.example.library.repository;

import com.example.library.entity.Borrowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BorrowingRepository extends JpaRepository<Borrowing, Long> {
    @Query("SELECT b FROM Borrowing b " +
           "JOIN FETCH b.book bo " +
           "JOIN FETCH bo.author " +
           "JOIN FETCH b.reader")
    List<Borrowing> findAllWithDetails();

    @Query("SELECT COUNT(b) > 0 FROM Borrowing b WHERE b.book.bookId = :bookId")
    boolean existsByBook_BookId(Long bookId);

    @Query("SELECT b FROM Borrowing b WHERE b.book.bookId = :bookId")
    Borrowing findByBookId(@Param("bookId") Long bookId);
}