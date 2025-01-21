package com.gohaibo.gohaibo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JoinColumn(name = "project_id")
    @OneToOne
    private  Project project;

    @JsonIgnore
    @OneToMany(mappedBy = "chat",cascade = CascadeType.ALL , orphanRemoval = true)
    List<Message> messages = new ArrayList<>();

    @ManyToMany
    private List<User> users = new ArrayList<>();
}
