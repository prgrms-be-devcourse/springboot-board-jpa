package prgrms.project.post.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import prgrms.project.post.repository.HobbyRepository;
import prgrms.project.post.repository.PostRepository;
import prgrms.project.post.repository.UserRepository;
import prgrms.project.post.util.mapper.HobbyMapper;
import prgrms.project.post.util.mapper.UserMapper;

import java.util.List;
import java.util.NoSuchElementException;

import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final UserRepository userRepository;
    private final HobbyRepository hobbyRepository;
    private final PostRepository postRepository;
    private final UserMapper userMapper;
    private final HobbyMapper hobbyMapper;

    @Override
    public Long registerUser(UserDto userDto) {
        var user = userMapper.toEntity(userDto);

        return userRepository.save(user).getId();
    }

    @Override
    @Transactional(readOnly = true)
    public UserDto searchById(Long id) {
        var retrievedUser = userRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Can't find any userDto"));

        return userMapper.toDto(retrievedUser);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> searchAll(Pageable pageable) {
        return userRepository.findAllBy(pageable).map(userMapper::toDto).toList();
    }

    @Override
    public Long updateUser(Long userId, UserDto userDto) {
        var retrievedUser = userRepository.findById(userId).orElseThrow(() -> new NoSuchElementException("Can't find any userDto"));
        var updatedUser = retrievedUser.update(userDto.name(), userDto.age(), userDto.hobbies().stream().map(hobbyMapper::toEntity).collect(toSet()));

        return userRepository.save(updatedUser).getId();
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        postRepository.deleteAllInBatch();
        hobbyRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }
}
