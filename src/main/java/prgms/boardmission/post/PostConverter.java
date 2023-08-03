package prgms.boardmission.post;

import prgms.boardmission.domain.Member;
import prgms.boardmission.domain.Post;
import prgms.boardmission.member.converter.MemberConverter;
import prgms.boardmission.member.dto.MemberDto;
import prgms.boardmission.post.dto.PostDto;

import java.time.LocalDateTime;

public final class PostConverter {
    public static Post convertToPost(PostDto postDto){
            Post post = new Post();
            post.setId(postDto.postId());
            post.setTitle(postDto.title());
            post.setContent(postDto.content());
            post.setCratedAt(LocalDateTime.now());
            post.setCreatedBy(postDto.memberDto().name());
            post.setMember(MemberConverter.convertToMember(postDto.memberDto()));

            return post;
    }

    public static PostDto convertToPostDto(Post post){
        Member member = post.getMember();
        MemberDto memberDto = MemberConverter.convertToMemberDto(member);

        return new PostDto(post.getId(), post.getTitle(), post.getContent(), memberDto);

    }
}
