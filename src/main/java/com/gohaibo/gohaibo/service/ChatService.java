package com.gohaibo.gohaibo.service;

import com.gohaibo.gohaibo.entity.Chat;
import com.gohaibo.gohaibo.exception.ResourceCannotBeCreatedException;

public interface ChatService {
    Chat createChat(Chat chat) throws ResourceCannotBeCreatedException;

//    Chat getChatById(Long id) throws Exception;
//
//    void deleteChat(Long id) throws Exception;
//
//    Chat updateChat(Chat chat, Long id) throws Exception;
}
