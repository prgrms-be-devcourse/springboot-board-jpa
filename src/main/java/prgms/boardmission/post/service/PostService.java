package prgms.boardmission.post.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prgms.boardmission.domain.Post;
import prgms.boardmission.post.PostConverter;
import prgms.boardmission.post.dto.PostDto;
import prgms.boardmission.post.repository.PostRepository;

@Service
@Transactional
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public long save(PostDto postDto) {
        Post newPost = PostConverter.convertToPost(postDto);
        Post Post = postRepository.save(newPost);

        return newPost.getId();
    }
}
