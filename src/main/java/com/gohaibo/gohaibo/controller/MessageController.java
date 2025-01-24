package com.gohaibo.gohaibo.controller;

import com.gohaibo.gohaibo.entity.Chat;
import com.gohaibo.gohaibo.entity.Message;
import com.gohaibo.gohaibo.entity.User;
import com.gohaibo.gohaibo.service.MessageService;
import com.gohaibo.gohaibo.service.ProjectService;
import com.gohaibo.gohaibo.service.UserService;
import com.gohaibo.gohaibo.utility.CreateMessageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/message")
public class MessageController {

    private final UserService userService;
    private final ProjectService projectService;
    private final MessageService messageService;

    public MessageController(UserService userService, ProjectService projectService, MessageService messageService) {
        this.userService = userService;
        this.projectService = projectService;
        this.messageService = messageService;
    }


    @PostMapping("/send")
    public ResponseEntity<Message> sendMessage(@RequestBody CreateMessageRequest req) throws Exception {

        User user = userService.findUserById(req.getSenderID());
        Chat chat = projectService.getProjectById(req.getProjectID()).getChat();

        Message sentMessage = messageService.sendMessage(user.getId(), req.getProjectID(), req.getContent());

        return ResponseEntity.ok(sentMessage);

    }

    @GetMapping("/chat/{projectID}")
    public ResponseEntity<?> getMessagesByProjectID(@PathVariable Long projectID) throws Exception {
        return ResponseEntity.ok(messageService.getMessagesByProjectID(projectID));
    }
}
