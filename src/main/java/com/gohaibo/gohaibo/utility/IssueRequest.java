package com.gohaibo.gohaibo.utility;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import jakarta.validation.constraints.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueRequest {


    @NotBlank(message = "Title is required.")
    @Size(max = 100, message = "Title must not exceed 100 characters.")
    private String title;

    @NotBlank(message = "Description is required.")
    private String description;

    @NotBlank(message = "Status is required.")
    @Pattern(regexp = "Open|In Progress|Resolved|Closed", message = "Invalid status. Must be one of: Open, In Progress, Resolved, Closed.")
    private String status;

    @NotNull(message = "Project ID is required.")
    @Positive(message = "Project ID must be a positive number.")
    private Long projectID;

    @NotBlank(message = "Priority is required.")
    @Pattern(regexp = "Low|Medium|High|Critical", message = "Invalid priority. Must be one of: Low, Medium, High, Critical.")
    private String priority;

    @FutureOrPresent(message = "Due date must be today or in the future.")
    private LocalDate dueDate;
}
