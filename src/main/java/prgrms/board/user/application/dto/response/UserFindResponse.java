package prgrms.board.user.application.dto.response;

import prgrms.board.post.domain.Post;
import prgrms.board.user.domain.User;

import java.util.List;

public record UserFindResponse(
        String name,
        Integer age,
        String hobby,
        List<Post> posts
) {
    public static UserFindResponse of(User user) {
        String userName = user.getName();
        int userAge = user.getAge();
        String userHobby = user.getHobby();
        List<Post> userPosts = user.getPosts();

        return new UserFindResponse(
                userName, userAge,
                userHobby, userPosts
        );
    }
}
