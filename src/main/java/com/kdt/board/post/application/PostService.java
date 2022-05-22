package com.kdt.board.post.application;

import com.kdt.board.post.application.dto.request.EditPostRequestDto;
import com.kdt.board.post.application.dto.request.WritePostRequestDto;
import com.kdt.board.post.application.dto.response.PostResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    void write(WritePostRequestDto writePostRequestDto);

    List<PostResponseDto> getAll(Pageable pageable);

    PostResponseDto getOne(long id);

    void edit(EditPostRequestDto editPostRequestDto);
}
