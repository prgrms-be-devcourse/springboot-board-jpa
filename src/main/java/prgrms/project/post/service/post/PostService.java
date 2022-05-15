package prgrms.project.post.service.post;

import org.springframework.data.domain.Pageable;
import prgrms.project.post.service.DefaultPage;

public interface PostService {

    Long uploadPost(PostDto postRequestDto);

    PostDto searchById(Long id);

    DefaultPage<PostDto> searchAll(Pageable pageable);

    Long updatePost(Long id, PostDto postRequestDto);

    void deleteById(Long id);
}
