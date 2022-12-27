package com.prgrms.domain.post;

import com.prgrms.domain.user.User;
import com.prgrms.domain.user.UserService;
import com.prgrms.dto.PostDto;
import com.prgrms.dto.PostDto.Response;
import com.prgrms.dto.PostDto.ResponsePostDtos;
import com.prgrms.exception.ErrorCode;
import com.prgrms.exception.customException.PostNotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@Service
public class PostService {

    private final PostRepository postRepository;

    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService) {

        this.postRepository = postRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public Response findPostById(Long id) {

        return postRepository.findById(id)
            .map(Response::new)
            .orElseThrow(() -> new PostNotFoundException("id: " + id, ErrorCode.POST_NOT_FOUND));
    }

    @Transactional
    public Response insertPost(@Valid PostDto.Request postDto) {

        User user = userService.findUserById(postDto.userId()).toUser();
        Post saved = postRepository.save(postDto.toPost(user));

        return new Response(saved);

    }

    @Transactional
    public Response updatePost(Long id, @Valid PostDto.Update postDto) {

        Post post = postRepository.findById(id)
            .orElseThrow(() -> new PostNotFoundException("id: " + id, ErrorCode.POST_NOT_FOUND));

        post.update(postDto.content(), postDto.title());

        return new Response(post);
    }

    @Transactional(readOnly = true)
    public ResponsePostDtos getPostsByPage(Pageable pageable) {

        List<Response> responses = postRepository.findAll(pageable)
            .map(Response::new)
            .stream().toList();

        return new ResponsePostDtos(responses);
    }

}
