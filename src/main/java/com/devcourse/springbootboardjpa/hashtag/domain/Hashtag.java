package com.devcourse.springbootboardjpa.hashtag.domain;


import com.devcourse.springbootboardjpa.config.BaseEntity;
import com.devcourse.springbootboardjpa.post_hashtag.domain.PostHashtag;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "hashtag")
public class Hashtag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "hashtag")
    List<PostHashtag> postHashtags = new ArrayList<>();
}
