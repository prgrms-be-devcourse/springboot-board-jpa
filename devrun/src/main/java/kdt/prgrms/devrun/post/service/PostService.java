package kdt.prgrms.devrun.post.service;

import kdt.prgrms.devrun.post.dto.AddPostRequestDto;
import kdt.prgrms.devrun.post.dto.DetailPostDto;
import kdt.prgrms.devrun.post.dto.EditPostRequestDto;
import kdt.prgrms.devrun.post.dto.SimplePostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface PostService {

    Page<SimplePostDto> getPostPagingList(Pageable pageable);

    DetailPostDto getPostById(Long postId);

    Long createPost(AddPostRequestDto addPostRequestDto);

    Long updatePost(Long postId , EditPostRequestDto editPostRequestDto);

    void deletePostById(Long postId);

}
