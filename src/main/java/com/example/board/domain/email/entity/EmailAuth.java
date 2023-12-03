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
    @Column(name = "email_key", nullable = false)
    private String key;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String purpose;

    public EmailAuth(String key, String email, String purpose) {
        this.key = key;
        this.email = email;
        this.purpose = purpose;
    }
}
