package com.programmers.springboard.entity;

import jakarta.persistence.*;

@Entity
public class GroupPermission {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Group group;
    @ManyToOne(fetch = FetchType.LAZY)
    private Permission permission;
}
