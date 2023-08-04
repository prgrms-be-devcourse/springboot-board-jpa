package prgrms.board.user.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prgrms.board.post.domain.Post;
import prgrms.board.user.application.dto.response.UserFindResponse;
import prgrms.board.user.application.dto.request.UserSaveRequest;
import prgrms.board.user.application.dto.response.UserSaveResponse;
import prgrms.board.user.domain.User;
import prgrms.board.user.domain.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserSaveResponse saveUser(UserSaveRequest request) {
        String name = request.name();
        Integer age = request.age();
        User newUser = new User(name, age);
        User savedUser = userRepository.save(newUser);
        Long userId = savedUser.getId();

        return new UserSaveResponse(userId, name, age);
    }

    public UserFindResponse findById(Long userId) {
        Optional<User> userResponse = userRepository.findById(userId);
        User userById = userResponse.orElseThrow();
        String name = userById.getName();
        Integer age = userById.getAge();
        String hobby = userById.getHobby();
        List<Post> posts = userById.getPosts();

        return new UserFindResponse(name, age, hobby, posts);
    }
}
