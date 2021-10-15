package org.jpa.kdtboard.post.service;

import javassist.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.jpa.kdtboard.domain.board.*;
import org.jpa.kdtboard.post.converter.PostConverter;
import org.jpa.kdtboard.post.dto.PostDto;
import org.jpa.kdtboard.post.dto.PostType;
import org.jpa.kdtboard.common.Encrypt;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;

/**
 * Created by yunyun on 2021/10/12.
 */

@Service
@Transactional
@RequiredArgsConstructor
public class PostService {
    private final PostConverter postConverter;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public List<PostDto> findAll(){
        List<Post> dataAllSelected = postRepository.findAll();
        return dataAllSelected.stream().map(postConverter::convertDto).toList();
    }

    public Page<PostDto> findAllWithPaging(Pageable pageable){
        return postRepository.findAll(pageable)
                .map(v -> postConverter.convertDto((Post) v));
    }

    public PostDto findById(Long id) throws Throwable {
        return (PostDto) postRepository.findById(id)
                .map(v -> postConverter.convertDto((Post) v))
                .orElseThrow(() -> new NotFoundException("Id에 해당하는 정보가 존재하지 않습니다."));
    }

    @Transactional
    public Long save(PostDto postDto) throws NoSuchAlgorithmException {

        // 스프링 시큐리티를 잘 몰라서 아래와 같이 처리하였습니다.
        postDto.setPassword(Encrypt.SHA256(postDto.getPassword()));

        var dataToSave = postConverter.convertEntity(postDto);
            var userDataAllByName = userRepository.findByName(dataToSave.getCreatedBy());
            if (userDataAllByName.isEmpty()) throw new RuntimeException("유효한 작성자가 아닙니다. 확인 바랍니다.");
        dataToSave.setUser(userDataAllByName.get(0));

        var dataSaved = (Post) postRepository.save(dataToSave);
        return dataSaved.getId();
    }

    @Transactional
    public Long update(Long id, PostDto postDto) throws Throwable {

        if (PostType.HOMEWORK.equals(postDto.getPostType())){
            HomeworkPost homeworkPost = (HomeworkPost) postRepository.findById(id).orElseThrow( () -> new NotFoundException("Id에 해당하는 정보를 찾을 수 없습니다. "));
            homeworkPost.setTitle(postDto.getTitle());
            homeworkPost.setContent(postDto.getContent());
            homeworkPost.setCreatedBy(postDto.getCreatedBy());
            homeworkPost.setHomeworkStatus(postDto.getHomeworkStatus());
            return id;
        }
        if (PostType.INTRODUCE.equals(postDto.getPostType())){
            IntroducePost introducePost = (IntroducePost) postRepository.findById(id).orElseThrow( () -> new NotFoundException("Id에 해당하는 정보를 찾을 수 없습니다. "));
            introducePost.setTitle(postDto.getTitle());
            introducePost.setContent(postDto.getContent());
            introducePost.setCreatedBy(postDto.getCreatedBy());
            return id;
        }
        if (PostType.NOTICE.equals(postDto.getPostType())){
            NoticePost noticePost = (NoticePost) postRepository.findById(id).orElseThrow( () -> new NotFoundException("Id에 해당하는 정보를 찾을 수 없습니다. "));
            noticePost.setTitle(postDto.getTitle());
            noticePost.setContent(postDto.getContent());
            noticePost.setCreatedBy(postDto.getCreatedBy());
            noticePost.setExpireDate(postDto.getExpireDate());
            return id;
        }
        if (PostType.QUESTION.equals(postDto.getPostType())){
            QuestionPost questionPost = (QuestionPost) postRepository.findById(id).orElseThrow( () -> new NotFoundException("Id에 해당하는 정보를 찾을 수 없습니다. "));
            questionPost.setTitle(postDto.getTitle());
            questionPost.setContent(postDto.getContent());
            questionPost.setCreatedBy(postDto.getCreatedBy());
            questionPost.setPostScope(postDto.getPostScope());
            return id;
        }
        throw new IllegalArgumentException("잘못된 게시판 타입입니다. 확인 바랍니다.");

    }

    public Boolean validateForPassword(Long id,String password) throws Throwable {
        Post postEntity = (Post) postRepository.findById(id).orElseThrow(() -> new NotFoundException("Id에 해당하는 정보를 찾을 수 없습니다. "));
        return postEntity.getPassword().equals(Encrypt.SHA256(password));
    }


}
