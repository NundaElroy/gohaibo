package com.gohaibo.gohaibo.serviceimp;


import com.gohaibo.gohaibo.entity.Chat;
import com.gohaibo.gohaibo.exception.ResourceCannotBeCreatedException;
import com.gohaibo.gohaibo.repo.ChatRepo;
import com.gohaibo.gohaibo.service.ChatService;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImp implements ChatService {
    private final ChatRepo chatRepo;

    public ChatServiceImp(ChatRepo chatRepo) {
        this.chatRepo = chatRepo;
    }

    @Override
    public Chat createChat(Chat chat) throws ResourceCannotBeCreatedException {
        return chatRepo.save(chat);
    }
}
