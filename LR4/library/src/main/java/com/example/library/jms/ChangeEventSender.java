package com.example.library.jms;

import com.example.library.config.JmsConfig;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChangeEventSender {

    private final JmsTemplate jmsTemplate;

    public ChangeEventSender(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void send(ChangeEvent event) {
        jmsTemplate.convertAndSend(JmsConfig.ENTITY_CHANGE_DESTINATION, event);
    }
}
