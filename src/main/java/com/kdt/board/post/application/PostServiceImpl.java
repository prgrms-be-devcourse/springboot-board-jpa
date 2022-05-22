package com.kdt.board.post.application;

import com.kdt.board.post.application.dto.request.EditPostRequestDto;
import com.kdt.board.post.application.dto.request.WritePostRequestDto;
import com.kdt.board.post.application.dto.response.PostResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    @Override
    public void write(WritePostRequestDto writePostRequestDto) {

    }

    @Override
    public List<PostResponseDto> getAll(Pageable pageable) {
        return null;
    }

    @Override
    public PostResponseDto getOne(long id) {
        return null;
    }

    @Override
    public void edit(EditPostRequestDto editPostRequestDto) {

    }
}
