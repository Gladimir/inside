package com.test.insidetest.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MessageRepository extends CrudRepository<Message, Long> {
    List<Message> findTop10ByOrderByIdDesc();
}
