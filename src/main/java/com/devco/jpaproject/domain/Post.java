package com.devco.jpaproject.domain;

import com.devco.jpaproject.domain.common.BaseEntity;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@EqualsAndHashCode
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 1000)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "writer_id", referencedColumnName = "id")
    private User writer;

    public void updateTitleAndContent(String title, String content){
        this.title = title;
        this.content = content;
        setModifiedAt(LocalDateTime.now());
    }

    /**
     * 연관관계 편의 매소드
     */
    public void addToWriter(User writer){
        this.writer = writer;        //양방향
        writer.getPosts().add(this); //연결을 함
    }

    public boolean deleteFromWriter(){
        if(Objects.nonNull(this.writer)){
            return this.writer.getPosts().remove(this);
        }

        return false;
    }

}
