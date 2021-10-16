package kdt.prgms.springbootboard.service;

import javassist.NotFoundException;
import kdt.prgms.springbootboard.converter.PostConverter;
import kdt.prgms.springbootboard.dto.PostDetailDto;
import kdt.prgms.springbootboard.dto.PostDto;
import kdt.prgms.springbootboard.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class PostService {

    private final PostRepository postRepository;
    private final PostConverter postConverter;

    public PostService(PostRepository postRepository,
        PostConverter postConverter) {
        this.postRepository = postRepository;
        this.postConverter = postConverter;
    }

    @Transactional
    public Long save(PostDto postDto) {
        return postRepository.save(postConverter.convertPost(postDto)).getId();
    }

    @Transactional
    public Long update(PostDto postDto) throws NotFoundException {
        var foundPost = postRepository.findById(postDto.getId()).orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
        foundPost.changePost(postDto.getTitle(), postDto.getContent());
        return foundPost.getId();
    }

    public Page<PostDto> findAll(Pageable pageable) {
        return postRepository.findAll(pageable).map(postConverter::convertPostDto);
    }

    public PostDetailDto findOne(Long postId) throws NotFoundException {
        return postRepository.findById(postId)
            .map(postConverter::convertPostDetailDto)
            .orElseThrow(() -> new NotFoundException("게시글을 찾을 수 없습니다."));
    }

}
