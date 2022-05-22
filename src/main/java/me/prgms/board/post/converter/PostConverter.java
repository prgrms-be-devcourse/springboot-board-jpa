package me.prgms.board.post.converter;

import me.prgms.board.domain.post.Post;
import me.prgms.board.domain.User;
import me.prgms.board.post.dto.CreatePostDto;
import me.prgms.board.post.dto.ResponsePostDto;
import me.prgms.board.user.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {

    public Post postDtoToPost(CreatePostDto postDto) {
        return new Post(postDto.getTitle(), postDto.getContent(), userDtoToUser(postDto.getUserDto()));
    }

    public ResponsePostDto postToPostDto(Post post) {
        return new ResponsePostDto(post.getId(), post.getTitle(), post.getContent(), userToUserDto(post.getUser()));
    }

    private User userDtoToUser(UserDto userDto) {
        return new User(userDto.getName(), userDto.getAge(), userDto.getHobby());
    }

    private UserDto userToUserDto(User user) {
        return new UserDto(user.getId(), user.getName(), user.getAge(), user.getHobby());
    }
}
