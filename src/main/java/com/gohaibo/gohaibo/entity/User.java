package com.gohaibo.gohaibo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @OneToMany(mappedBy = "assignee",cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Issue> assignedIssues = new ArrayList<>();

    private int projectSize;

    @JsonIgnore
    @OneToMany(mappedBy = "owner",cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Project> projects = new ArrayList<>();

    @JsonIgnore
    @ManyToMany(mappedBy = "team")
    private List<Project> teamProjects = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "commenter",cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Comment> comments = new ArrayList<>();

    @ManyToMany(mappedBy = "users")
    private List<Chat> chats = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "sender",cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();




}
