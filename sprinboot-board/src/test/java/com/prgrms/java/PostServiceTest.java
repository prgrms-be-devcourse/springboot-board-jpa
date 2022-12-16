package com.prgrms.java;

import com.prgrms.java.domain.HobbyType;
import com.prgrms.java.domain.Post;
import com.prgrms.java.domain.User;
import com.prgrms.java.dto.GetPostDetailsResponse;
import com.prgrms.java.dto.GetPostsResponse;
import com.prgrms.java.repository.PostRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@DataJpaTest
class PostServiceTest {

    @Autowired
    private PostRepository postRepository;

    private PostService postService = new PostService(postRepository);


    @DisplayName("게시글을 페이징 조회할 수 있다.")
    @Test
    void getPosts() {
    }

    @DisplayName("게시글을 단건 조회할 수 있다.")
    @Test
    void getPostDetail() {
    }
}