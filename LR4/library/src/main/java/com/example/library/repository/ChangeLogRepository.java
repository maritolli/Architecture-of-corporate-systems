package com.example.library.repository;

import com.example.library.entity.ChangeLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChangeLogRepository extends JpaRepository<ChangeLog, Long> {
}
