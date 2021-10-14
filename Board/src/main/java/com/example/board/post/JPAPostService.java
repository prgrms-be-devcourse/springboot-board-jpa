package com.example.board.post;

import com.example.board.member.JPAMemberService;
import com.example.board.member.MemberJPARepository;
import javassist.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JPAPostService implements PostService{

    PostJPARepository repository;
    PostConverter converter;
    MemberJPARepository memberRepository;

    public JPAPostService(PostJPARepository repository, PostConverter converter, MemberJPARepository memberRepository) {
        this.repository = repository;
        this.converter = converter;
        this.memberRepository = memberRepository;
    }

    @Transactional
    @Override
    public PostDto createPost(PostDto postDto) {
        Post post = PostConverter.of(postDto);
        repository.save(post);
        post.setMember(memberRepository.findByName(postDto.memberName()).get());
        postDto = converter.toPostDto(post);
        return postDto;
    }

    @Transactional
    @Override
    public PostDto updatePost(PostDto postDto) throws NotFoundException{
        Optional<Post> post = repository.findById(postDto.id());
        if (post.isEmpty()){
            throw new NotFoundException("해당하는 게시물이 없습니다.");
        }
        post.get().update(postDto.title(), postDto.content());
        PostDto updatedPostDto = converter.toPostDto(post.get());
        return updatedPostDto;
    }

    @Transactional
    @Override
    public void deletePost(PostDto postDto) throws NotFoundException{
        repository.findById(postDto.id())
                .map(post -> repository.deleteById(post.getId()))
                .orElseThrow(() -> new NotFoundException("해당하는 게시물이 없습니다."));
    }

    @Transactional
    @Override
    public PostDto findById(Long id) throws NotFoundException {
        return repository.findById(id)
                .map(post -> converter.toPostDto(post))
                .orElseThrow(() -> new NotFoundException("해당하는 게시물이 없습니다."));
    }

    @Transactional
    @Override
    public List<PostDto> findAll() {
        List<Post> posts = repository.findAll();
        List<PostDto> postDtos = new ArrayList<>();
        for (Post p : posts){
            postDtos.add(converter.toPostDto(p));
        }
        return postDtos;
    }
}
