package org.prgms.boardservice.domain.post;

import org.prgms.boardservice.domain.post.vo.PostUpdateVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

import static org.prgms.boardservice.util.ErrorMessage.NOT_FOUND_POST;

@Service
@Transactional
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Long create(Post post) {
        return postRepository.save(post).getId();
    }

    public Long update(PostUpdateVo postUpdateVo) {
        Post findPost = postRepository.findById(postUpdateVo.id())
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_POST.getMessage()));

        findPost.update(postUpdateVo.title(), postUpdateVo.content());

        return postRepository.save(findPost).getId();
    }

    @Transactional(readOnly = true)
    public Post getById(Long id) {
        return postRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException(NOT_FOUND_POST.getMessage()));
    }

    @Transactional(readOnly = true)
    public Page<Post> getByPage(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    public void deleteAll() {
        postRepository.deleteAll();
    }
}
