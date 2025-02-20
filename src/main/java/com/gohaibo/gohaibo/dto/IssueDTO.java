package com.gohaibo.gohaibo.dto;

import com.gohaibo.gohaibo.entity.Project;
import com.gohaibo.gohaibo.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private Long projectID;
    private String priority;
    private LocalDate dueDate;

    private List<String> tags = new ArrayList<>();

    private Project project;

    //Exclude Assignee duing serialisation

    private User assignee;


}
