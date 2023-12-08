package com.programmers.springboard.service;

import com.programmers.springboard.entity.Member;
import com.programmers.springboard.entity.Post;
import com.programmers.springboard.exception.PostNotFoundException;
import com.programmers.springboard.repository.PostCustomRepository;
import com.programmers.springboard.repository.PostRepository;
import com.programmers.springboard.request.PostCreateRequest;
import com.programmers.springboard.request.PostUpdateRequest;
import com.programmers.springboard.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class PostService {

	private final PostRepository postRepository;
	private final PostCustomRepository postCustomRepository;
	private final MemberService memberService;

	@Transactional(readOnly = true)
	public List<PostResponse> getPosts(int page) {
		List<Post> posts = postCustomRepository.getPosts(page);
		return posts.stream().map(PostResponse::of).toList();
	}

	@Transactional(readOnly = true)
	public PostResponse getPostById(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(PostNotFoundException::new);
		return PostResponse.of(post);
	}

	public PostResponse createPost(PostCreateRequest request) {
		Member member = memberService.getMemberById(request.memberId());
		Post post = postRepository.save(Post.builder()
			.content(request.content())
			.title(request.title())
			.member(member)
			.build());
		return PostResponse.of(post);
	}

	public PostResponse updatePost(Long id, PostUpdateRequest request) {
		Post post = postRepository.findById(id)
			.orElseThrow(PostNotFoundException::new);
		post.changePostTitleContent(request.title(), request.content());
		return PostResponse.of(post);
	}

	public void deletePost(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(PostNotFoundException::new);
		postRepository.delete(post);
	}
}
