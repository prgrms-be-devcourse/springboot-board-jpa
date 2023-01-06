package com.prgrms.boardjpa.domain.post;

import com.prgrms.boardjpa.domain.BaseEntity;
import com.prgrms.boardjpa.domain.member.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@ToString(exclude = "member")
@Builder
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
    @Column(columnDefinition = "TEXT")
    private String content;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", referencedColumnName = "id")
    private Member member;


    public void changePostInfo(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public void enrollWriter(Member member) {
        if (Objects.nonNull(this.member)) {
            List<Post> posts = this.member.getPosts();
            posts.remove(this);
        }
        this.member = member;
        List<Post> posts = member.getPosts();
        posts.add(this);
    }
}
