package com.example.library.service;

import com.example.library.jms.ChangeEvent;
import org.springframework.stereotype.Service;

@Service
public class ChangeNotificationService {

    private final EmailService emailService;

    public ChangeNotificationService(EmailService emailService) {
        this.emailService = emailService;
    }

    public void handle(ChangeEvent event) {
        if (!"Book".equals(event.getEntityName())
                || !"UPDATE".equals(event.getOperation())) {
            return;
        }

        boolean toHands = event.getOldValue() != null
                && event.getOldValue().contains("в библиотеке")
                && event.getNewValue() != null
                && event.getNewValue().contains("на руках");

        boolean toLibrary = event.getOldValue() != null
                && event.getOldValue().contains("на руках")
                && event.getNewValue() != null
                && event.getNewValue().contains("в библиотеке");

        if (toHands) {
            emailService.sendChangeNotification(
                    "Книга выдана",
                    buildBody(event, "выдана")
            );
        } else if (toLibrary) {
            emailService.sendChangeNotification(
                    "Книга возвращена",
                    buildBody(event, "возвращена в библиотеку")
            );
        }
    }

    private String buildBody(ChangeEvent event, String actionText) {
        return "Книга с ID = " + event.getEntityId()
                + " была " + actionText + ".\n"
                + "Старое значение: " + event.getOldValue() + "\n"
                + "Новое значение: " + event.getNewValue();
    }
}
