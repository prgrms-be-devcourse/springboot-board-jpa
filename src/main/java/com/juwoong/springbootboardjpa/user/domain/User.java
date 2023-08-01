package com.juwoong.springbootboardjpa.user.domain;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.juwoong.springbootboardjpa.common.BaseEntity;
import com.juwoong.springbootboardjpa.post.domain.Post;
import com.juwoong.springbootboardjpa.user.domain.constant.Hobby;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "USERS")
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private Hobby hobby;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Post> posts;

}
