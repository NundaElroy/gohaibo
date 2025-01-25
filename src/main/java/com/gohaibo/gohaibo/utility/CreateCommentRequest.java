package com.gohaibo.gohaibo.utility;


import lombok.Data;

import jakarta.validation.constraints.*;

@Data
public class CreateCommentRequest {

    @NotNull(message = "Issue ID is required.")
    @Positive(message = "Issue ID must be a positive number.")
    private Long issueID;

    @NotBlank(message = "Content is required.")
    @Size(max = 500, message = "Content must not exceed 500 characters.")
    private String content;
}
