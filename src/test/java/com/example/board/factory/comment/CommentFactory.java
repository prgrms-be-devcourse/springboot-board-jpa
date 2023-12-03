package com.example.board.factory.comment;

import com.example.board.domain.comment.entity.Comment;
import com.example.board.domain.member.entity.Member;
import com.example.board.domain.post.entity.Post;

import static com.example.board.factory.member.MemberFactory.createMemberWithRoleUser;
import static com.example.board.factory.post.PostFactory.createPost;

public class CommentFactory {

    public static Comment createComment(Post post, Member writer, Comment parent) {
        if (parent == null) {
            return new Comment("테스트 댓글", post, writer, null);
        } else {
            return new Comment("테스트 댓글", post, writer, parent);
        }
    }

    public static Comment createComment(Comment parent) {
        return new Comment("content",  createPost(), createMemberWithRoleUser(), parent);
    }
}
