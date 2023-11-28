package com.programmers.springboard.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class GroupPermission {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    private Groups groups;
    @ManyToOne(fetch = FetchType.LAZY)
    private Permission permission;
}
