package com.gohaibo.gohaibo.service;

import com.gohaibo.gohaibo.entity.Comment;

import java.util.List;

public interface CommentService {

    Comment createComment(Long IssueID, Long userID, String comment) throws Exception;

     void deleteComment(Long commentID, Long userID) throws Exception;

     List<Comment> findCommentsByIssueID(Long issueID) throws Exception;
}
