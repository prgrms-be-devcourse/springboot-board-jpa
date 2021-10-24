package kdt.cse0518.board.post.service;

import kdt.cse0518.board.post.converter.PostConverter;
import kdt.cse0518.board.post.dto.PostDto;
import kdt.cse0518.board.post.dto.ResponseDto;
import kdt.cse0518.board.post.entity.Post;
import kdt.cse0518.board.post.factory.PostFactory;
import kdt.cse0518.board.post.repository.PostRepository;
import kdt.cse0518.board.user.dto.UserDto;
import kdt.cse0518.board.user.entity.User;
import kdt.cse0518.board.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostConverter converter;
    private final PostFactory factory;

    public PostService(final PostRepository postRepository, final UserRepository userRepository, final PostConverter converter, final PostFactory factory) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.converter = converter;
        this.factory = factory;
    }

    public PostDto findById(final Long postId) {
        return converter.toPostDto(postRepository.findById(postId)
                .orElseThrow(() -> new NullPointerException("Id에 해당하는 Post가 없습니다.")));
    }

    public Page<PostDto> findAllByUser(final UserDto userDto, final Pageable pageable) {
        final User userEntity = userRepository.findById(userDto.getUserId())
                .orElseThrow(() -> new NullPointerException("Id에 해당하는 User가 없습니다."));
        final Page<Post> postsByUser = postRepository.findByUser(userEntity, pageable);
        return postsByUser.map(converter::toPostDto);
    }

    public Page<PostDto> findAll(final Pageable pageable) {
        return postRepository.findAll(pageable)
                .map(converter::toPostDto);
    }

    public Long newPostSave(final ResponseDto res) {
        final Post postEntity = factory.createPost(res.getTitle(), res.getContent(), userRepository.findById(res.getUserId()).get());
        return postRepository.save(postEntity).getPostId();
    }

    public Long update(final PostDto postDto) {
        final Post postEntity = postRepository.findById(postDto.getPostId())
                .orElseThrow(() -> new NullPointerException("Id에 해당하는 Post가 없습니다."));
        postEntity.update(postDto.getTitle(), postDto.getContent());
        return postRepository.save(postEntity).getPostId();
    }
}
