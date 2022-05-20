package com.prgrms.board.user.domain;

import com.prgrms.board.common.exception.MaxPostException;
import com.prgrms.board.post.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
@NoArgsConstructor
public class UserPosts {
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Post> posts = new ArrayList<>();

    public void canPost() {
        validatePostCountByDay(this.posts);
    }

    private void validatePostCountByDay(List<Post> posts) {
        long count = posts.stream()
                .filter(e -> e.getCreatedAt().toLocalDate().isEqual(LocalDate.now()))
                .count();
        if (count >= 3) {
            throw new MaxPostException();
        }
    }
}
