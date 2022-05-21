package org.programmers.springbootboardjpa.web.dto.post;

import org.programmers.springbootboardjpa.domain.Post;

public record PostUpdateFormV1(Long postId, String title, String content, Long userId) implements PostUpdateForm {
    @Override
    public Post applyToPost(Post post) {
        if (!postId.equals(post.getPostId())) {
            throw new IllegalArgumentException("수정하려는 대상 ID와 주어진 ID가 불일치합니다!");
        }
        return post.edit(new Post(postId,
                (isStringFieldAssigned(title)) ? title : post.getTitle(),
                (isStringFieldAssigned(content)) ? content : post.getContent(),
                post.getUser()));
    }

    private boolean isStringFieldAssigned(String field) {
        return ((field != null) && !(toString().isBlank()));
    }
}
