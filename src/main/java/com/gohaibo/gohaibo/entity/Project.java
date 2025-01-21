package com.gohaibo.gohaibo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private  String name;
    private String description;
    private String category;

    @ElementCollection
    List<String> tags = new ArrayList<>();

    @OneToOne(mappedBy = "project",cascade = CascadeType.ALL , orphanRemoval = true)
    @JsonIgnore
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Issue> assignedIssues = new ArrayList<>();

    @ManyToMany
    private List<User> team = new ArrayList<>();

    @OneToMany(mappedBy = "project",cascade = CascadeType.ALL , orphanRemoval = true)
    private List<Invitation> invitations = new ArrayList<>();
}
