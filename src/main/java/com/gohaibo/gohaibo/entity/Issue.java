package com.gohaibo.gohaibo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;
    private String status;
    private Long projectID;
    private String priority;
    private LocalDate localDate;
    private  List<String> tags = new ArrayList<>();

    @JoinColumn(name = "assignee_id")
    @ManyToOne
    private User assignee;

    @JsonIgnore
    @JoinColumn(name = "project_id")
    @ManyToOne
    private Project project;

    @JsonIgnore
    @OneToMany(mappedBy = "issue",cascade = CascadeType.ALL , orphanRemoval = true)
    private  List<Comments> comments = new ArrayList<>();
    //because of a recursion issue


}
