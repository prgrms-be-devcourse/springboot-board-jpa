package prgms.boardmission.post.dto;

import prgms.boardmission.member.dto.MemberDto;

public record PostDto(Long postId, String title, String content, MemberDto memberDto){
}
