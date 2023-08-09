package org.prgms.boardservice.domain.post;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;
import org.prgms.boardservice.domain.BaseTime;

@Builder
@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@DynamicUpdate
@EqualsAndHashCode(of = "id", callSuper = false)
public class Post extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private Title title;

    @Embedded
    private Content content;

    @NotNull
    private Long userId;

    public Post(Title title, Content content, Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public void update(Title title, Content content) {
        this.title = title;
        this.content = content;
    }
}
