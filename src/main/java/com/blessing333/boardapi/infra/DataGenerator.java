package com.blessing333.boardapi.infra;

import com.blessing333.boardapi.entity.Post;
import com.blessing333.boardapi.entity.User;
import com.blessing333.boardapi.repository.PostRepository;
import com.blessing333.boardapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class DataGenerator {
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    //TODO: 질문 3. 어플리케이션이 실행될 때 생성해야할 데이터가 있을 경우, 데이터를 생성하는법
    @PostConstruct
    @Transactional
    public void initDefaultData() {
        User user = User.createUserWithHobby("이민재", 28, "휴식");
        userRepository.save(user);
        generate20Post(user);
    }

    private void generate20Post(User user) {
        List<Post> posts = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            String content = Integer.toString(i);
            Post post = Post.createNewPost("title", content, user);
            posts.add(post);
        }
        postRepository.saveAll(posts);
    }
}
