package kdt.prgrms.devrun.user.service;

import kdt.prgrms.devrun.common.dto.DuplicationCheckResult;
import kdt.prgrms.devrun.common.exception.DuplicatedEmailException;
import kdt.prgrms.devrun.common.exception.DuplicatedLoginIdException;
import kdt.prgrms.devrun.common.exception.UserNotFoundException;
import kdt.prgrms.devrun.domain.User;
import kdt.prgrms.devrun.user.dto.AddUserRequestDto;
import kdt.prgrms.devrun.user.dto.DetailUserDto;
import kdt.prgrms.devrun.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SimpleUserService implements UserService{

    private final UserRepository userRepository;

    @Override
    public DetailUserDto getUserById(Long userId) {
        final User foundUser = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException());

        return DetailUserDto.builder()
            .userId(foundUser.getId())
            .age(foundUser.getAge())
            .email(foundUser.getEmail())
            .loginId(foundUser.getLoginId())
            .name(foundUser.getName())
            .build();
    }

    @Transactional
    @Override
    public Long createUser(AddUserRequestDto addUserRequestDto) {
        // 아이디 중복 체크
        final DuplicationCheckResult loginIdDuplicationCheckResult = checkDuplicatedLoginId(addUserRequestDto.getLoginId());
        if(loginIdDuplicationCheckResult.isResult()){
            throw new DuplicatedLoginIdException();
        }

        // Email 중복체크
        final DuplicationCheckResult emailDuplicationCheckResult = checkDuplicatedEmail(addUserRequestDto.getEmail());
        if(emailDuplicationCheckResult.isResult()){
            throw new DuplicatedEmailException();
        }

        // 등록
        final User willSaveUser = addUserRequestDto.convertToEntity();
        final User savedUser = userRepository.save(willSaveUser);

        return savedUser.getId();
    }

    @Override
    public DuplicationCheckResult checkDuplicatedLoginId(String loginId) {
        if(userRepository.findUsersByLoginId(loginId).isPresent()){
            return DuplicationCheckResult.builder()
                .result(true)
                .build();
        }
        return DuplicationCheckResult.builder()
            .result(false)
            .build();
    }

    @Override
    public DuplicationCheckResult checkDuplicatedEmail(String email) {
        if(userRepository.findUsersByEmail(email).isPresent()){
            return DuplicationCheckResult.builder()
                .result(true)
                .build();
        }
        return DuplicationCheckResult.builder()
            .result(false)
            .build();
    }

}
