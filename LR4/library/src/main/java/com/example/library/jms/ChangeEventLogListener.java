package com.example.library.jms;

import com.example.library.config.JmsConfig;
import com.example.library.service.ChangeLogService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ChangeEventLogListener {

    private final ChangeLogService changeLogService;

    public ChangeEventLogListener(ChangeLogService changeLogService) {
        this.changeLogService = changeLogService;
    }

    @JmsListener(destination = JmsConfig.ENTITY_CHANGE_DESTINATION)
    public void onMessage(ChangeEvent event) {
        changeLogService.logChange(event);
    }
}
