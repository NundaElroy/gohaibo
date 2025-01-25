package com.gohaibo.gohaibo.serviceimp;

import com.gohaibo.gohaibo.entity.Chat;
import com.gohaibo.gohaibo.entity.Message;
import com.gohaibo.gohaibo.exception.ResourceNotFoundException;
import com.gohaibo.gohaibo.repo.MessageRepo;
import com.gohaibo.gohaibo.repo.UserRepo;
import com.gohaibo.gohaibo.service.MessageService;
import com.gohaibo.gohaibo.entity.User;

import com.gohaibo.gohaibo.service.ProjectService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MessageServiceImp implements MessageService {

    private final MessageRepo messageRepo;
    private final UserRepo userRepo;
    private final ProjectService projectService;

    public MessageServiceImp(MessageRepo messageRepo, UserRepo userRepo, ProjectService projectService) {
        this.messageRepo = messageRepo;
        this.userRepo = userRepo;
        this.projectService = projectService;
    }


    @Override
    public Message sendMessage(Long senderID, Long projectID, String content) throws ResourceNotFoundException {
        User sender = userRepo.findById(senderID).orElseThrow(() -> new ResourceNotFoundException("User not found"));

        Chat chat = projectService.getProjectById(projectID).getChat();

        Message message = new Message();

        message.setSender(sender);
        message.setChat(chat);
        message.setContent(content);
        message.setCreatedAt(LocalDateTime.now());

        Message savedMessage = messageRepo.save(message);


        chat.getMessages().add(savedMessage);

        return savedMessage;

    }

    @Override
    public List<Message> getMessagesByProjectID(Long projectID) throws ResourceNotFoundException {
        Chat chat = projectService.getChatByProjectID(projectID);
        List<Message> findByChatIDOrderByCreatedAtAsc = messageRepo.findByChatIdOrderByCreatedAtAsc(chat.getId());

        return findByChatIDOrderByCreatedAtAsc;
    }
}
