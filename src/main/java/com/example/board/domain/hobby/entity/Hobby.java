package com.example.board.domain.hobby.entity;

import com.example.board.domain.hobby.validator.HobbyValidator;
import com.example.board.global.entity.BaseEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "hobby")
@Entity
public class Hobby extends BaseEntity {

    private static final HobbyValidator validator = new HobbyValidator();

    @Id
    @GeneratedValue
    @Column(name = "hobby_id", updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "hobby_type")
    private HobbyType hobbyType;


    @Builder
    public Hobby(HobbyType hobbyType) {
        validator.validateHobbyTypeNull(hobbyType);
        this.hobbyType = hobbyType;
    }

}
