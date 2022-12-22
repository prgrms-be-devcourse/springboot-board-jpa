package com.example.board.domain.hobby.entity;

import com.example.board.global.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static com.example.board.global.validator.HobbyValidation.validateHobbyTypeNull;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "hobby")
@Entity
public class Hobby extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "hobby_id", updatable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "hobby_type", nullable = false)
    @NotNull
    private HobbyType hobbyType;

    @Builder
    public Hobby(HobbyType hobbyType) {
        validateHobbyTypeNull(hobbyType);
        this.hobbyType = hobbyType;
    }
}
