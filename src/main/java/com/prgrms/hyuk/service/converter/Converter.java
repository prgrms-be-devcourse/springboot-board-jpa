package com.prgrms.hyuk.service.converter;

import com.prgrms.hyuk.domain.post.Content;
import com.prgrms.hyuk.domain.post.Post;
import com.prgrms.hyuk.domain.post.Title;
import com.prgrms.hyuk.domain.user.Age;
import com.prgrms.hyuk.domain.user.Hobby;
import com.prgrms.hyuk.domain.user.Name;
import com.prgrms.hyuk.domain.user.User;
import com.prgrms.hyuk.dto.PostCreateRequest;
import com.prgrms.hyuk.dto.PostDto;
import com.prgrms.hyuk.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class Converter {

    public User toUser(UserDto userDto) {
        return new User(
            new Name(userDto.getName()),
            new Age(userDto.getAge()),
            Hobby.valueOf(userDto.getHobby().toUpperCase())
        );
    }

    public Post toPost(PostCreateRequest postCreateRequest) {
        var post = new Post(
            new Title(postCreateRequest.getTitle()),
            new Content(postCreateRequest.getContent()));

        var user = toUser(postCreateRequest.getUser());
        post.assignUser(user);

        return post;
    }

    public PostDto toPostDto(Post post) {
        UserDto userDto = toUserDto(post.getUser());

        return new PostDto(
            post.getId(),
            post.getTitle().getTitle(),
            post.getContent().getContent(),
            userDto
        );
    }

    private UserDto toUserDto(User user) {
        return new UserDto(
            user.getName().getName(),
            user.getAge().getAge(),
            user.getHobby().name()
        );
    }
}
