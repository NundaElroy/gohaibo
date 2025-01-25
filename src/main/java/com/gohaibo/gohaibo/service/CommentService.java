package com.gohaibo.gohaibo.service;

import com.gohaibo.gohaibo.entity.Comment;
import com.gohaibo.gohaibo.exception.ResourceNotFoundException;

import java.util.List;

public interface CommentService {

    Comment createComment(Long IssueID, Long userID, String comment) throws ResourceNotFoundException;

     void deleteComment(Long commentID, Long userID) throws ResourceNotFoundException;

     List<Comment> findCommentsByIssueID(Long issueID) throws ResourceNotFoundException;
}
