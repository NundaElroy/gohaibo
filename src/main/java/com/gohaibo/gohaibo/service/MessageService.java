package com.gohaibo.gohaibo.service;

import com.gohaibo.gohaibo.entity.Message;

import java.util.List;

public interface MessageService {

    Message sendMessage(Long senderID , Long projectID , String content) throws Exception;

    List<Message> getMessagesByProjectID(Long projectID) throws Exception;
}
