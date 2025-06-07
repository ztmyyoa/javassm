package com.example.demo.SSM_frame.project.service;

import com.example.demo.SSM_frame.project.pojo.consumer;

import java.util.List;

public interface ConsumerService {
    consumer getConsumerById(String username);
    List<consumer> getAllConsumers();
    void updateConsumer(consumer consumer);
    void deleteConsumer(int userId);
    void addConsumer(consumer consumer);
}
