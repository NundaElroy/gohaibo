package com.gohaibo.gohaibo.utility;


import lombok.Data;

@Data
public class CreateCommentRequest {
    private Long issueID;
    private String content;


}
