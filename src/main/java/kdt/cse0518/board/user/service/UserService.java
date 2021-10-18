package kdt.cse0518.board.user.service;

import kdt.cse0518.board.user.converter.UserConverter;
import kdt.cse0518.board.user.dto.UserDto;
import kdt.cse0518.board.user.entity.User;
import kdt.cse0518.board.user.factory.UserFactory;
import kdt.cse0518.board.user.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;
    private final UserConverter converter;
    private final UserFactory factory;

    public UserService(final UserRepository repository, final UserConverter converter, final UserFactory factory) {
        this.repository = repository;
        this.converter = converter;
        this.factory = factory;
    }

    public Page<UserDto> findAll(final Pageable pageable) {
        return repository.findAll(pageable)
                .map(converter::toUserDto);
    }

    public UserDto findById(final Long userid) {
        final User userEntity = repository.findById(userid)
                .orElseThrow(() -> new NullPointerException("Id에 해당하는 User가 없습니다."));
        return converter.toUserDto(userEntity);
    }

    public Long newRequestDtoSave(final UserDto userDto) {
        final User userEntity = factory.createUser(userDto.getName(), userDto.getAge(), userDto.getHobby());
        return userEntity.getUserId();
    }

    public Long update(final UserDto userDto) {
        final User userEntity = repository.findById(userDto.getUserId())
                .orElseThrow(() -> new NullPointerException("Id에 해당하는 User가 없습니다."));
        userEntity.update(userDto.getName(), userDto.getAge(), userDto.getHobby());
        return repository.save(userEntity).getUserId();
    }
}
