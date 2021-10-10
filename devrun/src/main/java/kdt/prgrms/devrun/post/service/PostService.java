package kdt.prgrms.devrun.post.service;

import kdt.prgrms.devrun.post.dto.DetailPostDto;
import kdt.prgrms.devrun.post.dto.PostForm;
import kdt.prgrms.devrun.post.dto.SimplePostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface PostService {

    Page<SimplePostDto> getPostPagingList(Pageable pageable);

    DetailPostDto getPostById(Long postId);

    Long createPost(PostForm postForm);

    Long updatePost(Long postId ,PostForm postForm);

    void deletePostById(Long postId);

}
