package prgrms.project.post.service.user;

import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {

    Long registerUser(UserDto userDto);

    UserDto searchById(Long id);

    List<UserDto> searchAll(Pageable pageable);

    Long updateUser(Long userId, UserDto userDto);

    void deleteById(Long id);
}
