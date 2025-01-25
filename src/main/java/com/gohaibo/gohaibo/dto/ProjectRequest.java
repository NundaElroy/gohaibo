package com.gohaibo.gohaibo.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;



import java.util.ArrayList;
import java.util.List;

@Data
public class ProjectRequest {

    @NotBlank(message = "Name is mandatory and cannot be blank.")
    @Size(max = 100, message = "Name cannot exceed 100 characters.")
    private  String name;
    @NotBlank(message = "Description is mandatory and cannot be blank.")
    private String description;
    @NotEmpty(message = "Tags list cannot be empty. Provide at least one tag.")
    List<String> tags = new ArrayList<>();
    @NotBlank(message = "Category is mandatory and cannot be blank.")
    private String category;
}
