package prgms.boardmission.post.dto;

import prgms.boardmission.member.dto.MemberDto;

public record PostDto(String title, String content, MemberDto memberDto) {
}
