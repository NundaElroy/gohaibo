package com.gohaibo.gohaibo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "assignee_id")
    @ManyToOne
    private User assignee;
}
