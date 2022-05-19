package org.prgms.board.domain.post.service;

import org.prgms.board.domain.post.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostService {

	/** 게시글 페이징 조회 **/
	Page<Post> getPostPage(Pageable pageable);

	/** 게시글 단건 조회 **/
	Post findPost(Long postId);

	/** 게시글 작성 **/
	Post writePost(String title, String content, Long writerId);

	/** 게시글 수정 **/
	void updatePost(String title, String content, Long postId);
}
