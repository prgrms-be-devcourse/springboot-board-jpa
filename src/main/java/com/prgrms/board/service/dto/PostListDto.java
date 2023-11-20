package com.prgrms.board.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PostListDto {

    int count;

    List<PostDto> postDtoList;

}
