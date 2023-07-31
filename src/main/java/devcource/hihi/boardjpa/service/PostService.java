package devcource.hihi.boardjpa.service;

import devcource.hihi.boardjpa.controller.CursorResult;
import devcource.hihi.boardjpa.domain.Post;
import devcource.hihi.boardjpa.domain.User;
import devcource.hihi.boardjpa.dto.post.CreatePostDto;
import devcource.hihi.boardjpa.dto.post.ResponsePostDto;
import devcource.hihi.boardjpa.dto.post.UpdatePostDto;
import devcource.hihi.boardjpa.repository.PostRepository;
import devcource.hihi.boardjpa.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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
    public ResponsePostDto creteDto(CreatePostDto postDto) {
        Post post = postDto.toEntity();
        User user = userRepository.findById(post.getCreated_by().getId())
                .orElseThrow(() -> new RuntimeException("작성자의 id가 존재하지 않습니다"));
        post.allocateUser(user);
        Post savedPost = postRepository.save(post);
        return Post.toResponseDto(savedPost);
    }

    public ResponsePostDto findById(Long id) {
        Post post = postRepository.findById(id).get();
        return Post.toResponseDto(post);
    }

    public List<ResponsePostDto> findByUserId(Long id){
        List<ResponsePostDto> postDtoList = getPostDtoList(postRepository.findByUserId(id));
        return postDtoList;
    }

    public List<ResponsePostDto> findByTitle(String title) {
        List<ResponsePostDto> postDtoList = getPostDtoList(postRepository.findByTitle(title));
        return postDtoList;
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

    private List<ResponsePostDto> getPostDtoList(List<Post> postRepository) {
        List<ResponsePostDto> postDtoList = new ArrayList<>();
        List<Post> postList = postRepository;
        for (Post post : postList) {
            ResponsePostDto postDto = Post.toResponseDto(post);
            postDtoList.add(postDto);
        }
        return postDtoList;
    }

    public CursorResult<Post> get(Long cursorId, Pageable page) {
        final List<Post> postList = getPosts(cursorId, page);
        final Long lastIdOfList = postList.isEmpty() ?
                null : postList.get(postList.size() - 1).getId();

        return new CursorResult<>(postList, hasNext(lastIdOfList));
    }

    List<Post> getPosts(Long id, Pageable page) {
        return id == null ? this.postRepository.findAllByOrderByIdDesc(page) :
                this.postRepository.findByIdLessThanOrderByIdDesc(id, page);
    }

    private Boolean hasNext(Long id) {
        if (id == null) return false;
        return this.postRepository.existsByIdLessThan(id);
    }
}
