package com.gohaibo.gohaibo.serviceimp;

import com.gohaibo.gohaibo.entity.Comment;
import com.gohaibo.gohaibo.entity.Issue;
import com.gohaibo.gohaibo.entity.User;
import com.gohaibo.gohaibo.exception.ResourceNotFoundException;
import com.gohaibo.gohaibo.repo.CommentRepo;
import com.gohaibo.gohaibo.repo.IssueRepo;
import com.gohaibo.gohaibo.repo.UserRepo;
import com.gohaibo.gohaibo.service.CommentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;



@Service
public class CommentServiceImp implements CommentService {

    private final IssueRepo issueRepo;
    private final CommentRepo commentRepo;
    private final UserRepo userRepo;

    public CommentServiceImp(IssueRepo issueRepo, CommentRepo commentRepo, UserRepo userRepo) {
        this.issueRepo = issueRepo;
        this.commentRepo = commentRepo;
        this.userRepo = userRepo;
    }


    @Override
    public Comment createComment(Long IssueID, Long userID, String content) throws ResourceNotFoundException {
        Issue issue = issueRepo.findById(IssueID).orElseThrow(() -> new ResourceNotFoundException("Issue with id " + IssueID +
                " not found"));
        User user = userRepo.findById(userID).orElseThrow(() -> new ResourceNotFoundException("User with id " + userID + " not found"));

        Comment comment = new Comment();
        comment.setIssue(issue);
        comment.setCommenter(user);
        comment.setContent(content);
        comment.setCreatedAt(LocalDateTime.now());

        Comment savedComment = commentRepo.save(comment);

        issue.getComments().add(savedComment);

        issueRepo.save(issue);

        return  savedComment;



    }

    @Override
    public void deleteComment(Long commentID, Long userID) throws ResourceNotFoundException{
        Comment comment = commentRepo.findById(commentID).orElseThrow(() -> new ResourceNotFoundException("Comment with id " + commentID +
                " not found"));

        User user = userRepo.findById(userID).orElseThrow(() -> new ResourceNotFoundException("User with id " + userID + " not found"));

        if(!comment.getCommenter().equals(user)) {
            throw new ResourceNotFoundException("User does not have permission to delete this comment");
        }

        commentRepo.deleteById(commentID);
    }



    @Override
    public List<Comment> findCommentsByIssueID(Long issueID) throws ResourceNotFoundException{
        return commentRepo.findCommentsByIssueId(issueID);
    }
}
