package com.programmers.board.core.post.application.dto;

import com.programmers.board.core.post.domain.Post;
import com.programmers.board.core.user.domain.User;

public class PostCreateRequest {

    private final Long userId;

    private final String title;

    private final String content;

    public PostCreateRequest(Long userId, String title, String content) {
        this.userId = userId;
        this.title = title;
        this.content = content;
    }

    // toEntity
    public Post toEntity(User user) {
        Post post = Post.builder()
                .title(this.title)
                .content(this.content)
                .user(user)
                .build();
        return post;
    }

    //Getter
    public Long getUserId() {
        return userId;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }


    //Builder
    public static PostCreateRequestBuilder builder(){
        return new PostCreateRequestBuilder();
    }
    
    public static class PostCreateRequestBuilder{
        
        private Long userId;
        
        private String title;
        
        private String content;
        
        public PostCreateRequestBuilder(){}
        
        public PostCreateRequestBuilder userId(Long userId){
            this.userId = userId;
            return this;
        }

        public PostCreateRequestBuilder title(String title){
            this.title = title;
            return this;
        }

        public PostCreateRequestBuilder content(String content){
            this.content = content;
            return this;
        }
        
        public PostCreateRequest build(){
            return new PostCreateRequest(this.userId, this.title, this.content);
        } 

    }
}
