package prgrms.board.user.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prgrms.board.user.application.dto.request.UserSaveRequest;
import prgrms.board.user.application.dto.response.UserFindResponse;
import prgrms.board.user.application.dto.response.UserSaveResponse;
import prgrms.board.user.domain.User;
import prgrms.board.user.domain.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public UserSaveResponse saveUser(UserSaveRequest request) {
        User newUser = request.toEntity();
        User savedUser = userRepository.save(newUser);

        return UserSaveResponse.of(savedUser);
    }

    public UserFindResponse findById(Long userId) {
        User userById = userRepository.findById(userId)
                .orElseThrow();

        return UserFindResponse.of(userById);
    }
}
