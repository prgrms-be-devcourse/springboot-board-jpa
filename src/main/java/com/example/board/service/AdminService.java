package com.example.board.service;

import com.example.board.converter.PostConverter;
import com.example.board.converter.UserConverter;
import com.example.board.domain.Post;
import com.example.board.domain.User;
import com.example.board.dto.request.admin.AdminUpdateUserRequest;
import com.example.board.dto.response.CustomResponseStatus;
import com.example.board.dto.response.PostResponse;
import com.example.board.dto.response.UserDetailResponse;
import com.example.board.exception.CustomException;
import com.example.board.repository.post.PostRepository;
import com.example.board.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public List<UserDetailResponse> getUsers(List<Long> userIds) {
        List<User> users;
        if (userIds == null || userIds.isEmpty()) users = userRepository.findAll();
        else users = userRepository.findAllById(userIds);

        return users.stream()
                .map(UserConverter::toUserDetailResponse)
                .toList();
    }

    public List<UserDetailResponse> updateUser(Long id, AdminUpdateUserRequest requestDto) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException(CustomResponseStatus.USER_NOT_FOUND));
        user.update(requestDto.email(), requestDto.name(), requestDto.age(), requestDto.role());
        return List.of(UserConverter.toUserDetailResponse(user));
    }

    public List<UserDetailResponse> deleteUsers(List<Long> userIds) {
        List<User> users = userRepository.findAllById(userIds);
        users.forEach(User::delete);

        return users.stream()
                .map(UserConverter::toUserDetailResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPosts(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(CustomResponseStatus.USER_NOT_FOUND));
        return user.getPosts().stream()
                .map(PostConverter::toPostResponse)
                .toList();
    }

    public List<UserDetailResponse> deletePosts(List<Long> postIds) {
        List<Post> posts = postRepository.findAllById(postIds);
        postRepository.deleteAll(posts);
        return posts.stream()
                .map(Post::getAuthor)
                .distinct()
                .map(UserConverter::toUserDetailResponse)
                .toList();
    }
}
