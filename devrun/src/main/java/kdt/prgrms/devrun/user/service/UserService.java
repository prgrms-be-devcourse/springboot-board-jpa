package kdt.prgrms.devrun.user.service;

import kdt.prgrms.devrun.common.dto.DuplicationCheckResult;
import kdt.prgrms.devrun.user.dto.AddUserRequestDto;
import kdt.prgrms.devrun.user.dto.DetailUserDto;

public interface UserService {

    DetailUserDto getUserById(Long userId);

    Long createUser(AddUserRequestDto addUserRequestDto);

    DuplicationCheckResult checkDuplicatedLoginId(String loginId);

    DuplicationCheckResult checkDuplicatedEmail(String email);

}
