package com.gohaibo.gohaibo.controller;


import com.gohaibo.gohaibo.entity.Comment;
import com.gohaibo.gohaibo.entity.User;
import com.gohaibo.gohaibo.exception.ResourceNotFoundException;
import com.gohaibo.gohaibo.service.CommentService;
import com.gohaibo.gohaibo.service.UserService;
import com.gohaibo.gohaibo.utility.ApiResponse;
import com.gohaibo.gohaibo.utility.CreateCommentRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;

    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CreateCommentRequest req,
                                                 @RequestHeader("Authorization") String token) throws ResourceNotFoundException {
        User user = userService.findUserProfileByJwt(token);
        Comment comment = commentService.createComment(req.getIssueID(), user.getId(), req.getContent());
        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("/{commentID}")
    public  ResponseEntity<ApiResponse<Object>> deleteComment(@PathVariable Long commentID,
                                                              @RequestHeader("Authorization") String token) throws ResourceNotFoundException {
        User user = userService.findUserProfileByJwt(token);
        commentService.deleteComment(commentID, user.getId());
        return ResponseEntity.ok(new ApiResponse<>(true , "Comment deleted successfully","Operation successful"));
    }

    @GetMapping("/{issueID}")
    public ResponseEntity<List<Comment>>  getCommentsByIssueID(@PathVariable Long issueID) throws ResourceNotFoundException {
        List<Comment> comments = commentService.findCommentsByIssueID(issueID);
        return ResponseEntity.ok(comments);
    }
}
