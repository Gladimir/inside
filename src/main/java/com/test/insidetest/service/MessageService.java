package com.test.insidetest.service;

import com.test.insidetest.model.MessageCreateRequest;
import com.test.insidetest.repository.Message;

import java.util.List;

public interface MessageService {
    Message createMessage(MessageCreateRequest message);
    List<Message> get10LastMessages();
}
