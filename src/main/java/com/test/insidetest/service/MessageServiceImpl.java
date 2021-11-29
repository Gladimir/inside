package com.test.insidetest.service;

import com.test.insidetest.model.MessageCreateRequest;
import com.test.insidetest.repository.Message;
import com.test.insidetest.repository.MessageRepository;
import com.test.insidetest.repository.UserEntity;
import com.test.insidetest.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Override
    public Message createMessage(MessageCreateRequest messageCreateRequest) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        Message message = mapper.map(messageCreateRequest, Message.class);

        Optional<UserEntity> userEntity = userRepository.findByName(messageCreateRequest.getName());

        userEntity.ifPresent(userEntity1 -> message.setUserEntity(userEntity1));

        messageRepository.save(message);


        return message;
    }

    @Override
    public List<Message> get10LastMessages() {
        return messageRepository.findTop10ByOrderByIdDesc()
                .stream().peek(message -> message.setUserEntity(null)).collect(Collectors.toList()); // This is dirty hack
    }
}
