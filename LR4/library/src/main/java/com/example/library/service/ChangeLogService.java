package com.example.library.service;

import com.example.library.entity.ChangeLog;
import com.example.library.jms.ChangeEvent;
import com.example.library.repository.ChangeLogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ChangeLogService {

    private final ChangeLogRepository changeLogRepository;

    public ChangeLogService(ChangeLogRepository changeLogRepository) {
        this.changeLogRepository = changeLogRepository;
    }

    public void logChange(ChangeEvent event) {
        ChangeLog log = new ChangeLog();
        log.setEntityName(event.getEntityName());
        log.setOperation(event.getOperation());
        log.setChangeTime(LocalDateTime.now());
        log.setOldValue(event.getOldValue());
        log.setNewValue(event.getNewValue());
        changeLogRepository.save(log);
    }
}
