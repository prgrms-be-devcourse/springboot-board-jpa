package com.programmers.springboard.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class GroupPermission {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groups_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Groups groups;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "permission_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Permission permission;
}
