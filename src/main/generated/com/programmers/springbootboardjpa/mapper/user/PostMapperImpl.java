package com.programmers.springbootboardjpa.mapper.user;

import com.programmers.springbootboardjpa.domain.post.Post;
import com.programmers.springbootboardjpa.dto.post.PostCreateRequest;
import com.programmers.springbootboardjpa.dto.post.PostResponse;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-01T23:36:09+0900",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.7 (Amazon.com Inc.)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public Post toPost(PostCreateRequest postCreateRequest) {
        if ( postCreateRequest == null ) {
            return null;
        }

        Post.PostBuilder<?, ?> post = Post.builder();

        post.title( postCreateRequest.getTitle() );
        post.content( postCreateRequest.getContent() );

        return post.build();
    }

    @Override
    public PostResponse toPostResponse(Post post) {
        if ( post == null ) {
            return null;
        }

        PostResponse.PostResponseBuilder postResponse = PostResponse.builder();

        postResponse.id( post.getId() );
        postResponse.title( post.getTitle() );
        postResponse.content( post.getContent() );
        postResponse.createdBy( post.getCreatedBy() );
        postResponse.createdAt( post.getCreatedAt() );

        return postResponse.build();
    }
}
