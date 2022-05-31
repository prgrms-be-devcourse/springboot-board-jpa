package prgrms.project.post.util.mapper;

import org.mapstruct.Mapper;
import prgrms.project.post.domain.user.User;
import prgrms.project.post.service.user.UserDto;

@Mapper(componentModel = "spring")
public interface UserMapper extends EntityMapper<UserDto, User> {
}
