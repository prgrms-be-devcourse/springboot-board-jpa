package prgrms.project.post.service.post;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {

    Long uploadPost(PostDto postRequestDto);

    PostDto searchById(Long id);

    List<PostDto> searchAll(Pageable pageable);

    Long updatePost(Long id, PostDto postRequestDto);

    void deleteById(Long id);
}
