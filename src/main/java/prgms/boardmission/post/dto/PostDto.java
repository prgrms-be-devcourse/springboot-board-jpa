package prgms.boardmission.post.dto;

import prgms.boardmission.member.dto.MemberDto;

import java.time.LocalDateTime;

import static prgms.boardmission.post.dto.PostDto.*;

public sealed interface PostDto permits Request, Response {
    record Request(String title, String content, MemberDto memberDto) implements PostDto {
    }

    record Response(long postId, String title, String content, LocalDateTime createdAt) implements PostDto {
    }
}
