package com.gohaibo.gohaibo.utility;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateMessageRequest {

    @NotNull(message = "Sender ID is required.")
    @Positive(message = "Sender ID must be a positive number.")
    private Long senderID;

    @NotNull(message = "Project ID is required.")
    @Positive(message = "Project ID must be a positive number.")
    private Long projectID;

    @NotBlank(message = "Content is required.")
    @Size(max = 500, message = "Content must not exceed 500 characters.")
    private String content;
}
