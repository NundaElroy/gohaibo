package com.gohaibo.gohaibo.utility;

import lombok.Data;

@Data
public class CreateMessageRequest {

    private Long senderID;
    private Long projectID;
    private String content;


}
