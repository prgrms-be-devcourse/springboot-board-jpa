package yjh.jpa.springnoticeboard.domain.service;

import javassist.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yjh.jpa.springnoticeboard.domain.converter.UserMapper;
import yjh.jpa.springnoticeboard.domain.dto.UserDto;
import yjh.jpa.springnoticeboard.domain.entity.Post;
import yjh.jpa.springnoticeboard.domain.entity.User;
import yjh.jpa.springnoticeboard.domain.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Transactional(readOnly = true)
    @Override
    public Long createUser(UserDto userDto) {
        setUserPosts(userDto);
        User user = UserMapper.INSTANCE.userDtoToEntity(userDto);
        user.setCreatedBy("admin");
        user.setCratedAt(LocalDateTime.now());
        User entity = userRepository.save(user);
        return entity.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public Long updateUser(Long userId, UserDto userDto) throws NotFoundException {
        UserDto update = userRepository.findById(userId)
                .map(user -> UserMapper.INSTANCE.userEntityToDto(user))
                .orElseThrow(()->new NotFoundException("해당 유저를 찾을 수 없습니다."));
        update.setAge(userDto.getAge());
        update.setHobby(userDto.getHobby());
        update.setName(userDto.getName());
        return update.getId();
    }

    @Transactional(readOnly = true)
    @Override
    public Page<Object> findAll(Pageable pageable) {
        return userRepository.findAll(pageable)
                .map(user -> UserMapper.INSTANCE.userEntityToDto(user));
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto findUser(Long userId) throws NotFoundException {
        UserDto userDto = userRepository.findById(userId)
                .map(user -> UserMapper.INSTANCE.userEntityToDto(user))
                .orElseThrow(()->new NotFoundException("해당 유저를 찾을 수 없습니다."));
        return userDto;
    }

    @Transactional(readOnly = true)
    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }


    private void setUserPosts(UserDto userDto){
        if(userDto.getPosts() == null){
            List<Post> list = new ArrayList<>();
            userDto.setPosts(list);
        }
    }
}

