package com.board.springbootboard.domain.user.service;

import com.board.springbootboard.domain.posts.PostsEntity;
import com.board.springbootboard.domain.posts.dto.PostsListResponseDto;
import com.board.springbootboard.domain.posts.dto.PostsResponseDto;
import com.board.springbootboard.domain.posts.dto.PostsSaveRequestDto;
import com.board.springbootboard.domain.posts.dto.PostsUpdateRequestsDto;
import com.board.springbootboard.domain.user.UserEntity;
import com.board.springbootboard.domain.user.UserRepository;
import com.board.springbootboard.domain.user.dto.UserResponseDto;
import com.board.springbootboard.domain.user.dto.UserSaveRequestDto;
import com.board.springbootboard.domain.user.dto.UserUpdateRequestDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserService {

    UserRepository userRepository;

    // 저장
    @Transactional
    public Long save(UserSaveRequestDto requestDto) {
        return userRepository.save(requestDto.toEntity()).getId();
    }

    // 수정 - Posts Entity의 dirtyChecking으로 별다른 명령어 필요 없음. entity만 수정하면 됨.
    @Transactional
    public Long update(Long id, UserUpdateRequestDto requestsDto) {
        UserEntity user=userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 회원 정보가 없습니다 id="+id));
        user.update(requestsDto.getName(),requestsDto.getNickName());
        return id;
    }

    // id로 단건 조회
    @Transactional
    public UserResponseDto findById(Long id) {
        UserEntity entity=userRepository.findById(id).orElseThrow(()->new IllegalArgumentException("해당 게시물이 없습니다. id="+id));
        return new UserResponseDto(entity);
    }

    // 다건 조회 (id 별 내림차순)
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return userRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new) // Map을 통해
                .collect(Collectors.toList()); // List로 변환
    }
}
