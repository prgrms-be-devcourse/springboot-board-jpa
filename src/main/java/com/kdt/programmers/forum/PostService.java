package com.kdt.programmers.forum;

import com.kdt.programmers.forum.domain.Post;
import com.kdt.programmers.forum.transfer.PostDto;
import com.kdt.programmers.forum.utils.PostConverter;
import javassist.NotFoundException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {

    private final PostJpaRepository postRepository;

    private final PostConverter postConverter;

    public PostService(PostJpaRepository postRepository, PostConverter postConverter) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
    }

    @Transactional
    public Long savePost(PostDto dto) {
        Post post = postConverter.convertToPost(dto);
        Post entity = postRepository.save(post);
        return entity.getId();
    }
}
