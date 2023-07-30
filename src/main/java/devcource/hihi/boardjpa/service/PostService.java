package devcource.hihi.boardjpa.service;

import devcource.hihi.boardjpa.controller.CursorResult;
import devcource.hihi.boardjpa.domain.Post;
import devcource.hihi.boardjpa.dto.CreatePostDto;
import devcource.hihi.boardjpa.repository.PostRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository boardRepository) {
        this.postRepository = boardRepository;
    }

    public CreatePostDto saveDto(CreatePostDto postDto) {
        Post post = postDto.toEntity();
        Post savedPost = postRepository.save(post);
        return Post.toDto(savedPost);
    }

    public CreatePostDto findById(Long id) {
        Post post = postRepository.findById(id).get();
        return Post.toDto(post);
    }

    public List<CreatePostDto> findByUserId(Long id){
        List<CreatePostDto> postDtoList = getPostDtoList(postRepository.findByUserId(id));
        return postDtoList;
    }

    public List<CreatePostDto> findByTitle(String title) {
        List<CreatePostDto> postDtoList = getPostDtoList(postRepository.findByTitle(title));
        return postDtoList;
    }

    private List<CreatePostDto> getPostDtoList(List<Post> postRepository) {
        List<CreatePostDto> postDtoList = new ArrayList<>();
        List<Post> postList = postRepository;
        for (Post post : postList) {
            CreatePostDto postDto = Post.toDto(post);
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
