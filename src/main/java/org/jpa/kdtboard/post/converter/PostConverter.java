package org.jpa.kdtboard.post.converter;

import org.jpa.kdtboard.domain.board.*;
import org.jpa.kdtboard.post.dto.PostDto;
import org.jpa.kdtboard.post.dto.PostType;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * Created by yunyun on 2021/10/12.
 */

@Component
public class PostConverter {

    public PostDto convertDto(Post post) {
        if (post instanceof HomeworkPost){
            return PostDto.builder()
                    .id(post.getId())
                    .postType(PostType.HOMEWORK)
                    .title(post.getTitle())
                    .content(post.getContent())
                    .createdAt(post.getCreatedAt())
                    .password(null)
                    .updatedAt(post.getUpdatedAt())
                    .createdBy(post.getCreatedBy())
                    .homeworkStatus(((HomeworkPost) post).getHomeworkStatus())
                    .build();
        }
        if (post instanceof IntroducePost) {
            return PostDto.builder()
                    .id(post.getId())
                    .postType(PostType.INTRODUCE)
                    .title(post.getTitle())
                    .content(post.getContent())
                    .createdAt(post.getCreatedAt())
                    .updatedAt(post.getUpdatedAt())
                    .createdBy(post.getCreatedBy())
                    .password(null)
                    .build();
        }
        if (post instanceof NoticePost) {
            return PostDto.builder()
                    .id(post.getId())
                    .postType(PostType.NOTICE)
                    .title(post.getTitle())
                    .content(post.getContent())
                    .createdAt(post.getCreatedAt())
                    .updatedAt(post.getUpdatedAt())
                    .createdBy(post.getCreatedBy())
                    .password(null)
                    .expireDate(((NoticePost) post).getExpireDate())
                    .build();
        }
        if (post instanceof QuestionPost) {
            return PostDto.builder()
                    .id(post.getId())
                    .postType(PostType.QUESTION)
                    .title(post.getTitle())
                    .content(post.getContent())
                    .createdAt(post.getCreatedAt())
                    .updatedAt(post.getUpdatedAt())
                    .createdBy(post.getCreatedBy())
                    .password(null)
                    .postScope(((QuestionPost) post).getPostScope())
                    .build();
        }
        throw new IllegalArgumentException("잘못된 게시판 타입입니다. 확인 바랍니다.");
    }

    public Post convertEntity(PostDto postDto) {
        if (PostType.HOMEWORK.equals(postDto.getPostType())){
            HomeworkPost homeworkPost = new HomeworkPost();
            homeworkPost.setTitle(postDto.getTitle());
            homeworkPost.setContent(postDto.getContent());
            homeworkPost.setPassword(postDto.getPassword());
            homeworkPost.setCreatedBy(postDto.getCreatedBy());
            homeworkPost.setHomeworkStatus(postDto.getHomeworkStatus());
            return homeworkPost;
        }

        if (PostType.INTRODUCE.equals(postDto.getPostType())){
            IntroducePost introducePost = new IntroducePost();
            introducePost.setTitle(postDto.getTitle());
            introducePost.setContent(postDto.getContent());
            introducePost.setPassword(postDto.getPassword());
            introducePost.setCreatedBy(postDto.getCreatedBy());
            return introducePost;
        }

        if (PostType.NOTICE.equals(postDto.getPostType())){
            NoticePost noticePost = new NoticePost();
            noticePost.setTitle(postDto.getTitle());
            noticePost.setContent(postDto.getContent());
            noticePost.setPassword(postDto.getPassword());
            noticePost.setCreatedBy(postDto.getCreatedBy());
            noticePost.setExpireDate(postDto.getExpireDate());
            return noticePost;
        }

        if (PostType.QUESTION.equals(postDto.getPostType())){
            QuestionPost questionPost = new QuestionPost();
            questionPost.setTitle(postDto.getTitle());
            questionPost.setContent(postDto.getContent());
            questionPost.setPassword(postDto.getPassword());
            questionPost.setCreatedBy(postDto.getCreatedBy());
            questionPost.setPostScope(postDto.getPostScope());
            return questionPost;
        }
        throw new IllegalArgumentException("잘못된 게시판 타입입니다. 확인 바랍니다.");
    }

}
