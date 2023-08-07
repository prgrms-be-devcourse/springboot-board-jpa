package devcource.hihi.boardjpa.service;

import devcource.hihi.boardjpa.domain.Post;
import devcource.hihi.boardjpa.domain.User;
import devcource.hihi.boardjpa.dto.post.CreatePostDto;
import devcource.hihi.boardjpa.dto.post.PageCursorDto;
import devcource.hihi.boardjpa.dto.post.ResponsePostDto;
import devcource.hihi.boardjpa.dto.post.UpdatePostDto;
import devcource.hihi.boardjpa.repository.PostRepository;
import devcource.hihi.boardjpa.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository boardRepository, UserRepository userRepository) {
        this.postRepository = boardRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ResponsePostDto createDto(CreatePostDto postDto) {
        Post post = postDto.toEntity();

        User user = userRepository.findById(postDto.user().getId()).orElseThrow(() -> new RuntimeException("작성자는 null이 될 수 없습니다"));
        post.allocateUser(user);

        return Post.toResponseDto(post);
    }
    public ResponsePostDto findById(Long id) {
        Post post = postRepository.findById(id).get();
        return Post.toResponseDto(post);
    }

    @Transactional
    public ResponsePostDto updatePost(Long id, UpdatePostDto dto) {
        Post post = postRepository.findById(id).get();
        post.changeTitle(dto.title());
        post.changeContent(dto.content());
        return Post.toResponseDto(post);
    }

    @Transactional
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }

    public PageCursorDto<Post> getPostsByCursor(Long cursor, int limit) {
        List<Post> posts = postRepository.findByCursor(cursor, limit + 1);
        Long prevCursor = null;
        Long nextCursor = null;

        if (!posts.isEmpty()) {
            if (posts.size() > limit) {
                posts.remove(limit);
                nextCursor = posts.get(limit - 1).getId();
            }

            if (cursor != null) {
                prevCursor = cursor;
            }
        }

        return new PageCursorDto<>(posts, prevCursor, nextCursor);
    }


}
