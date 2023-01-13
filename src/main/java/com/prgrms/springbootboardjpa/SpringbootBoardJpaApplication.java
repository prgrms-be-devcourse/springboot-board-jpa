package com.prgrms.springbootboardjpa;

import com.prgrms.springbootboardjpa.post.application.PostService;
import com.prgrms.springbootboardjpa.post.dto.PostInsertRequest;
import com.prgrms.springbootboardjpa.user.domain.Member;
import com.prgrms.springbootboardjpa.user.domain.MemberRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDateTime;

@SpringBootApplication
public class SpringbootBoardJpaApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(SpringbootBoardJpaApplication.class, args);
        MemberRepository memberRepository = context.getBean(MemberRepository.class);
        PostService postService = context.getBean(PostService.class);
        Member tmpMember = new Member("kiwoong", 20, "reading", LocalDateTime.now());
        memberRepository.save(tmpMember);
        postService.save(new PostInsertRequest(tmpMember, "title1", "content1", LocalDateTime.now()));

    }

}
