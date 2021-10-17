package com.maenguin.kdtbbs.converter;

import com.maenguin.kdtbbs.domain.Post;
import com.maenguin.kdtbbs.domain.User;
import com.maenguin.kdtbbs.dto.PostAddDto;
import com.maenguin.kdtbbs.dto.PostDto;
import com.maenguin.kdtbbs.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class BBSConverter {

    public Post convertToPost(PostAddDto postAddDto) {
        return new Post(postAddDto.getTitle(), postAddDto.getContent());
    }

    public User convertToUser(UserDto userDto) {
        return new User(userDto.getName(), userDto.getHobby());
    }

    public PostDto convertToPostDto(Post post) {
        return new PostDto(post.getPostId(), post.getTitle(), post.getContent());
    }

    public UserDto convertToUserDto(User user) {
        return new UserDto(user.getName(), user.getHobby());
    }
}
