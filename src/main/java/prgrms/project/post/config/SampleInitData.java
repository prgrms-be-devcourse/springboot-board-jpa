package prgrms.project.post.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import prgrms.project.post.domain.post.Post;
import prgrms.project.post.domain.user.Hobby;
import prgrms.project.post.domain.user.User;
import prgrms.project.post.repository.PostRepository;
import prgrms.project.post.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SampleInitData {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @PostConstruct
    @Transactional
    public void setData() {
        for (int i = 0; i < 50; i++) {

            Set<Hobby> hobbies = new HashSet<>();
            hobbies.add(new Hobby("soccer"));

            var user = User.builder().name("kim" + i).age(i + 10).hobbies(hobbies).build();
            userRepository.save(user);

            var post = Post.builder().title("title").content("content").user(user).build();
            postRepository.save(post);
        }
    }
}
