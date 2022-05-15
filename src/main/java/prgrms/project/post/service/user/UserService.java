package prgrms.project.post.service.user;

import org.springframework.data.domain.Pageable;
import prgrms.project.post.service.DefaultPage;

public interface UserService {

    Long registerUser(UserDto userDto);

    UserDto searchById(Long id);

    DefaultPage<UserDto> searchAll(Pageable pageable);

    Long updateUser(Long userId, UserDto userDto);

    void deleteById(Long id);
}
