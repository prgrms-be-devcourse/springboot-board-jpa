package org.prgrms.kdt.mapper;

import org.mapstruct.Mapper;
import org.prgrms.kdt.domain.User;
import org.prgrms.kdt.dto.UserDto.CurrentUser;

@Mapper(componentModel = "spring")
public interface UserMapper {

  CurrentUser of(User user);
}