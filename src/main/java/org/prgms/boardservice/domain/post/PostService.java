package org.prgms.boardservice.domain.post;

import org.prgms.boardservice.domain.post.vo.PostUpdateVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.prgms.boardservice.util.ErrorMessage.NOT_FOUND_POST;

@Service
public class PostService {

    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Transactional
    public Long create(Post post) {
        return postRepository.save(post).getId();
    }

    @Transactional
    public Long update(PostUpdateVo postUpdateVo) {

        Optional<Post> findPost = postRepository.findById(postUpdateVo.id());

        if (findPost.isPresent()) {
            Post p = findPost.get();

            p.changeTitle(postUpdateVo.title());
            p.changeContent(postUpdateVo.content());

            return postRepository.save(p).getId();
        } else {
            throw new NoSuchElementException(NOT_FOUND_POST.getMessage());
        }
    }

    public Post getById(Long id) {
        return postRepository.findById(id).orElseThrow(() -> new NoSuchElementException(NOT_FOUND_POST.getMessage()));
    }

    public Page<Post> getByPage(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    @Transactional
    public void deleteById(Long id) {
        postRepository.deleteById(id);
    }

    @Transactional
    public void deleteAll() {
        postRepository.deleteAll();
    }
}
