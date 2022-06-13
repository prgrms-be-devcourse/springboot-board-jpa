package com.su.gesipan.user;

import com.su.gesipan.post.PostDto;
import com.su.gesipan.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.su.gesipan.post.PostDtoMapper.toPostEntity;
import static com.su.gesipan.post.PostDtoMapper.toPostResult;
import static com.su.gesipan.user.UserDtoMapper.toUserEntity;
import static com.su.gesipan.user.UserDtoMapper.toUserResult;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;


    /**** Command ****/
    @Transactional
    public PostDto.Result createPost(PostDto.Create dto) {
        var user = userRepository.findById(dto.getUserId()).orElseThrow(() -> new UserNotFoundException("존재하지 않는 유저입니다."));
        var post = toPostEntity(dto, user);
        user.addPost(post);

        var findPost = postRepository.findByUserId(user.getId()).orElseThrow();
        return toPostResult(findPost);
    }

    @Transactional
    public UserDto.Result createUser(UserDto.Create dto) {
        return toUserResult(userRepository.save(toUserEntity(dto)));
    }
}
