package com.prgrms.be.app.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import java.util.Objects;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank // null, "", "    "
    @Max(value = 20)
    private String title;

    @NotBlank
    @Max(value = 400)
    @Column(columnDefinition = "TEXT")
    private String content;

//    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;

    public Post(Long id, String title, String content, User createdBy) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
    }

    public Post(String title, String content, User createdBy) {
        this.title = title;
        this.content = content;
        this.createdBy = createdBy;
    }

}
