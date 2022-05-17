package com.blessing333.boardapi;

import com.blessing333.boardapi.entity.Post;
import com.blessing333.boardapi.entity.User;
import com.blessing333.boardapi.repository.PostRepository;
import com.blessing333.boardapi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@SpringBootTest
@Transactional
public class TestDataProvider {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public Post insertPostToDb(String title, String content, User user) {
        Post post = Post.createNewPost(title, content, user);
        return postRepository.save(post);
    }

    public User insertUserToDb(String userName, int userAge) {
        User user = User.createUser(userName, userAge);
        return userRepository.save(user);
    }
}
