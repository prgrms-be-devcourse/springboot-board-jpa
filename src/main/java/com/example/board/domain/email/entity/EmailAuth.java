package com.example.board.domain.email.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailAuth {

    @Id
    @Column(name = "auth_key", nullable = false)
    private String authKey;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String purpose;

    public EmailAuth(String authKey, String email, String purpose) {
        this.authKey = authKey;
        this.email = email;
        this.purpose = purpose;
    }
}
