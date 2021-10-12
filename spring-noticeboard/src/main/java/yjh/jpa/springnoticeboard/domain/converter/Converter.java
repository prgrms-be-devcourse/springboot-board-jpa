package yjh.jpa.springnoticeboard.domain.converter;


import org.springframework.stereotype.Component;
import yjh.jpa.springnoticeboard.domain.dto.PostDto;
import yjh.jpa.springnoticeboard.domain.dto.UserDto;
import yjh.jpa.springnoticeboard.domain.entity.Post;
import yjh.jpa.springnoticeboard.domain.entity.User;

@Component
public class Converter {

    public Post postDtoToEntity(PostDto postDto) {
        if ( postDto == null ) {
            return null;
        }
        Post post = Post.builder()
                .title(postDto.getTitle())
                .content(postDto.getContent())
                .user(userDtoToEntity(postDto.getUser()))
                .build();

        return post;
    }
    public PostDto postToDto(Post post) {
        if ( post == null ) {
            return null;
        }

        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .user(userToUserDto(post.getUser()))
                .build();

        return postDto;
    }

    public User userDtoToEntity(UserDto userDto) {
        if ( userDto == null ) {
            return null;
        }

        User user = User.builder()
                .age(userDto.getAge())
                .hobby(userDto.getHobby())
                .name(userDto.getName())
                .build();
        return user;
    }

    public UserDto userToUserDto(User user) {
        if ( user == null ) {
            return null;
        }
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .hobby(user.getHobby())
                .age(user.getAge())
                .build();

        return userDto;
    }

}
