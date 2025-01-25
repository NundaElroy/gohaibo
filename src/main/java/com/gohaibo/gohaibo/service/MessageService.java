package com.gohaibo.gohaibo.service;

import com.gohaibo.gohaibo.entity.Message;
import com.gohaibo.gohaibo.exception.ResourceNotFoundException;

import java.util.List;

public interface MessageService {

    Message sendMessage(Long senderID , Long projectID , String content) throws ResourceNotFoundException;

    List<Message> getMessagesByProjectID(Long projectID) throws ResourceNotFoundException;
}
