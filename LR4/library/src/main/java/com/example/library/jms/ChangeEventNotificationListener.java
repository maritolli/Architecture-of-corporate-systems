package com.example.library.jms;

import com.example.library.config.JmsConfig;
import com.example.library.service.ChangeNotificationService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ChangeEventNotificationListener {

    private final ChangeNotificationService notificationService;

    public ChangeEventNotificationListener(ChangeNotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @JmsListener(destination = JmsConfig.ENTITY_CHANGE_DESTINATION)
    public void onMessage(ChangeEvent event) {
        notificationService.handle(event);
    }
}
