package com.jpaboard.post.application;

import com.jpaboard.exception.NotFoundPostException;
import com.jpaboard.post.domain.Post;
import com.jpaboard.post.infra.JpaPostRepository;
import com.jpaboard.post.ui.PostCommon;
import com.jpaboard.post.ui.PostCreate;
import com.jpaboard.post.ui.PostUpdate;
import com.jpaboard.user.domain.User;
import com.jpaboard.user.ui.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class PostService {

    private final JpaPostRepository postRepository;

    public PostService(JpaPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Page<PostCommon.Response> findPostAll(Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(this::toResponse);

    }

    public PostCommon.Response findPost(long id) {
        Post post = findBydId(id);
        return toResponse(post);
    }


    @Transactional
    public PostCreate.Response createPost(PostCreate.Request request) {
        UserDto.Request userRequest = request.user();
        User user = new User(userRequest.name(), userRequest.age(), userRequest.hobby());

        Post post = postRepository.save(
                new Post(request.title(), request.content(), user)
        );

        return new PostCreate.Response(post.getId(), post.getTitle(), post.getContent());
    }

    @Transactional
    public PostUpdate.Response updatePost(long id, PostUpdate.Request postUpdateRequest) {
        Post post = findBydId(id);
        post.updatePost(postUpdateRequest.title(), postUpdateRequest.content());

        return new PostUpdate.Response(post.getId(), post.getTitle(), post.getContent());
    }

    private Post findBydId(long id) {
        return postRepository.findById(id)
                .orElseThrow(NotFoundPostException::new);
    }

    private PostCommon.Response toResponse(Post post) {
        User user = post.getUser();

        UserDto.Response userDto = new UserDto.Response(
                user.getName(),
                user.getAge(),
                user.getHobby()
        );

        return new PostCommon.Response(post.getId(), post.getTitle(), post.getContent(), userDto);
    }

}
