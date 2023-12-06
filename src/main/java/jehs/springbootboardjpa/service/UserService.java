package jehs.springbootboardjpa.service;

import jehs.springbootboardjpa.entity.User;
import jehs.springbootboardjpa.exception.UserErrorMessage;
import jehs.springbootboardjpa.exception.UserException;
import jehs.springbootboardjpa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserException(UserErrorMessage.NOT_FOUND));
    }
}
