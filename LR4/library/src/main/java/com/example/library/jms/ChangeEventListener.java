package com.example.library.jms;

import com.example.library.config.JmsConfig;
import com.example.library.service.ChangeLogService;
import com.example.library.service.EmailService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ChangeEventListener {

    private final ChangeLogService changeLogService;
    private final EmailService emailService;

    public ChangeEventListener(ChangeLogService changeLogService,
                               EmailService emailService) {
        this.changeLogService = changeLogService;
        this.emailService = emailService;
    }

    @JmsListener(destination = JmsConfig.CHANGE_QUEUE)
    public void onMessage(ChangeEvent event) {
        changeLogService.logChange(event);

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
            String subject = "Книга выдана";
            String text = "Книга с ID = " + event.getEntityId()
                    + " была выдана.\n"
                    + "Старое значение: " + event.getOldValue() + "\n"
                    + "Новое значение: " + event.getNewValue();
            emailService.sendChangeNotification(subject, text);
        } else if (toLibrary) {
            String subject = "Книга возвращена";
            String text = "Книга с ID = " + event.getEntityId()
                    + " была возвращена в библиотеку.\n"
                    + "Старое значение: " + event.getOldValue() + "\n"
                    + "Новое значение: " + event.getNewValue();
            emailService.sendChangeNotification(subject, text);
        }
    }
}
