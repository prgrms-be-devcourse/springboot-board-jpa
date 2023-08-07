package prgms.boardmission.post;

import prgms.boardmission.domain.Member;
import prgms.boardmission.domain.Post;
import prgms.boardmission.member.MemberConverter;
import prgms.boardmission.post.dto.PostDto;

import java.time.LocalDateTime;

public final class PostConverter {
    public static Post convertToPost(PostDto.Request request) {
        String title = request.title();
        String content = request.content();
        Member member = MemberConverter.convertToMember(request.memberDto());

        Post post = new Post(title, content, member);

        return post;
    }

    public static PostDto.Response convertToPostDto(Post post) {
        return new PostDto.Response(post.getId(), post.getTitle(), post.getContent(), LocalDateTime.now());
    }
}
