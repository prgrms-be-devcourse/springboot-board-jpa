package com.prgrms.work.controller.dto;

import com.prgrms.work.post.domain.Post;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class PostRequest {

    public static class PostCreateDto {

        @NotEmpty(message = "제목은 필수로 입력하셔야 합니다.")
        private String title;
        @NotEmpty(message = "내용은 필수로 입력하셔야 합니다.")
        private String content;

        @Valid
        @NotNull(message = "작성자 정보는 필수입니다.")
        private MemberRequest.MemberCreateDto memberDto;

        public PostCreateDto(String title, String content, MemberRequest.MemberCreateDto memberDto) {
            this.title = title;
            this.content = content;
            this.memberDto = memberDto;
        }

        public MemberRequest.MemberCreateDto getMemberDto() {
            return this.memberDto;
        }

        public String getTitle() {
            return this.title;
        }

        public String getContent() {
            return this.content;
        }

        public Post toEntity() {
            return Post.create(
                this.memberDto.getName(),
                this.title,
                this.content,
                this.memberDto.toEntity()
            );
        }

    }

    public static class UpdateDto{

        @NotEmpty(message = "제목은 필수로 입력하셔야 합니다.")
        private String title;
        @NotEmpty(message = "내용은 필수로 입력하셔야 합니다.")
        private String content;

        public UpdateDto(String title, String content) {
            this.title = title;
            this.content = content;
        }

        public String getTitle() {
            return title;
        }

        public String getContent() {
            return content;
        }

    }

}
