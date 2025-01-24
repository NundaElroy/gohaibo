package com.gohaibo.gohaibo.repo;


import com.gohaibo.gohaibo.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepo extends JpaRepository<Comment,Long> {


    List<Comment> findCommentsByIssueId(Long issueID);
}
