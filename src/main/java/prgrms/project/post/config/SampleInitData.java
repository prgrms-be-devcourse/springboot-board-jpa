package prgrms.project.post.config;

import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.BatchSize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import prgrms.project.post.domain.post.Post;
import prgrms.project.post.domain.user.Hobby;
import prgrms.project.post.domain.user.User;
import prgrms.project.post.repository.PostRepository;
import prgrms.project.post.repository.UserRepository;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SampleInitData {

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @PostConstruct
    @Transactional
    public void setData() {
        var users = new ArrayList<User>();
        var posts = new ArrayList<Post>();

        for (int i = 0; i < 50; i++) {
            var user = User.builder().name("kim" + i).age(i + 10).hobbies(Set.of(new Hobby("soccer"))).build();
            users.add(user);

            posts.add(Post.builder().title("title").content("content").user(user).build());
        }

        userRepository.saveAll(users);
        postRepository.saveAll(posts);
    }
}
