package com.prgrms.boardjpa.domain.post;

import com.prgrms.boardjpa.domain.BaseEntity;
import com.prgrms.boardjpa.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Post extends BaseEntity {

    @Id
    @SequenceGenerator(
            name = "POST_SEQ_GENERATOR"
            , sequenceName = "POST_SEQ"
            , initialValue = 1
            , allocationSize = 1
    )
    @GeneratedValue(generator = "POST_SEQ_GENERATOR")
    private Long id;
    @Column(length = 30)
    private String title;
    @Lob
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;

    @Builder
    public Post(LocalDateTime createdAt, String createdBy, String title, String content, Member member) {
        super(createdAt, createdBy);
        this.title = title;
        this.content = content;
        this.member = member;
    }

    public void changePostInfo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void enrollWriter(Member member) {
        if(Objects.nonNull(this.member)) {
            this.member.getPosts().remove(this);
        }
        this.member = member;
        member.getPosts().add(this);
    }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
