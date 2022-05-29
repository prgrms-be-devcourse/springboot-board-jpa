package prgrms.project.post.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prgrms.project.post.controller.response.PageResponse;
import prgrms.project.post.domain.user.User;
import prgrms.project.post.repository.UserRepository;
import prgrms.project.post.util.exception.EntityNotFoundException;
import prgrms.project.post.util.mapper.HobbyMapper;
import prgrms.project.post.util.mapper.UserMapper;

import static java.text.MessageFormat.format;
import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    private final HobbyMapper hobbyMapper;

    @Transactional
    public Long registerUser(UserDto userDto) {
        var user = userMapper.toEntity(userDto);

        return userRepository.save(user).getId();
    }

    @Transactional(readOnly = true)
    public UserDto searchById(Long userId) {
        var retrievedUser = findUser(userId);

        return userMapper.toDto(retrievedUser);
    }

    @Transactional(readOnly = true)
    public PageResponse<UserDto> searchAll(Pageable pageable) {
        return PageResponse.of(userRepository.findUsersAll(pageable).map(userMapper::toDto));
    }

    @Transactional
    public Long updateUser(Long userId, UserDto userDto) {
        var retrievedUser = findUser(userId);
        var updatedUser = retrievedUser.updateUserInfo(userDto.name(), userDto.age(), userDto.hobbies().stream().map(hobbyMapper::toEntity).collect(toSet()));

        return updatedUser.getId();
    }

    @Transactional
    public void deleteById(Long userId) {
        var retrievedUser = findUser(userId);

        userRepository.delete(retrievedUser);
    }

    private User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(
            () -> new EntityNotFoundException(
                format("회원을 찾을 수 없습니다. (id: {0})", userId)
            )
        );
    }
}
