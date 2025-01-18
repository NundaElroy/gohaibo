package com.gohaibo.gohaibo.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.util.List;
import java.util.ArrayList;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String password;

    @OneToMany(mappedBy = "assignee",cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Issue> assignedIssues = new ArrayList<>();

    private int projectSize;



}
