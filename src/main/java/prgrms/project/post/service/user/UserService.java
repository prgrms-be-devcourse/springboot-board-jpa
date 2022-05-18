package prgrms.project.post.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prgrms.project.post.repository.UserRepository;
import prgrms.project.post.service.DefaultPage;
import prgrms.project.post.util.mapper.HobbyMapper;
import prgrms.project.post.util.mapper.UserMapper;

import java.util.NoSuchElementException;

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
    public UserDto searchById(Long id) {
        var retrievedUser = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Can't find any userDto"));

        return userMapper.toDto(retrievedUser);
    }

    @Transactional(readOnly = true)
    public DefaultPage<UserDto> searchAll(Pageable pageable) {
        return DefaultPage.of(userRepository.findUsersAll(pageable).map(userMapper::toDto));
    }

    @Transactional
    public Long updateUser(Long userId, UserDto userDto) {
        var retrievedUser = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Can't find any userDto"));
        var updatedUser = retrievedUser.update(userDto.name(), userDto.age(), userDto.hobbies().stream().map(hobbyMapper::toEntity).collect(toSet()));

        return userRepository.save(updatedUser).getId();
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}
